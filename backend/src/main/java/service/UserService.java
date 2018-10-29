package service;

import domain.*;
import filter.JWTTokenNeeded;
import filter.JWTTokenNeededFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.spi.HttpRequest;
import utils.KeyGenerator;
import bean.MailBean;
import utils.PasswordUtil;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static org.ietf.jgss.GSSException.UNAUTHORIZED;

@Stateless
@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UserService {
    @Context
    private UriInfo uriInfo;

    @EJB
    private MailBean mailBean;

    @Inject
    @Default
    private KeyGenerator keyGenerator;

    @PersistenceContext(unitName = "postgres")
    private EntityManager entityManager;

    private byte[] defaultAvatar;

    private Logger logger = Logger.getLogger("UserService");

    @PostConstruct
    private void readDefaultAvatar() {
        try {
            InputStream avatarStream = this.getClass().getResourceAsStream("/META-INF/avatar.jpg");
            defaultAvatar = IOUtils.toByteArray(avatarStream);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Can't find default avatar");
        }
    }

    @POST
    @Path("/login")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        try {
            String id = authenticate(username, password);
            String token = issueToken(username, id);
            TokenResponse tokenResponse = new TokenResponse(token);

            return Response.ok(tokenResponse).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private String authenticate(String login, String password) throws Exception {
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER, User.class);
        query.setParameter("username", login);
        User user = query.getSingleResult();

        if (user == null || !PasswordUtil.check(password, user.getPassword()))
            throw new SecurityException("Invalid user/password");

        return String.valueOf(user.getId());
    }

    private String issueToken(String login, String id) {
        Key key = keyGenerator.generateKey();

        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setId(id)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusDays(15L)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        try {
            System.out.println("Register user");
            String userPassword = user.getPassword();
            user.setPassword(PasswordUtil.getSaltedHash(userPassword));
            user.setCreationDate(new Date());
            user.setActivated(false);
            user.setBanned(false);

            entityManager.persist(user);

            mailBean.sendEmailActivation(user.getEmail(), user.getUsername());

            String id = String.valueOf(user.getId());
            return Response.created(
                    uriInfo.getAbsolutePathBuilder()
                            .path(id)
                            .build()
            ).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @POST
    @Path("/get")
    @JWTTokenNeeded
    public Response findUser(@Context HttpRequest request) {
        String username = (String) request.getAttribute(JWTTokenNeededFilter.USER);
        TypedQuery query = entityManager.createNamedQuery(User.FIND_USER, User.class);
        query.setParameter("username", username);

        User user = (User) query.getSingleResult();

        ProfileUser userResponse = new ProfileUser(user, Base64.encodeBase64String(defaultAvatar));

        return Response.ok(userResponse).build();
    }

    @POST
    @Path("/reset")
    public Response sendResetEmail(EmailReset email) {
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER_WITH_EMAIL, User.class);
        query.setParameter("email", email.getEmail());

        User user = query.getResultList().get(0);
        if (user != null) {
            String token = issueToken(user.getUsername(), String.valueOf(user.getId()));

            mailBean.sendResetPassword(user.getEmail(), user.getUsername(), token);

            return Response.ok().build();
        }

        return Response.noContent().build();
    }

    @POST
    @Path("/resetPassword")
    @JWTTokenNeeded
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response resetPassword(@Context HttpRequest request, @FormParam("newPassword") String newPassword) {
        try {
            String username = (String) request.getAttribute(JWTTokenNeededFilter.USER);

            TypedQuery query = entityManager.createNamedQuery(User.FIND_USER, User.class);
            query.setParameter("username", username);

            User user = (User) query.getSingleResult();
            user.setPassword(PasswordUtil.getSaltedHash(newPassword));
            entityManager.persist(user);

            return Response.ok().build();
        } catch (Exception ex) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/findUser")
    @JWTTokenNeeded
    public Response getUser(User user) {
        return Response.ok().build();
    }

    @POST
    @Path("/activateUser")
    @JWTTokenNeeded
    public Response activateAccount(@Context HttpRequest request) {
        //TODO: закончить метод
        Integer id = (Integer) request.getAttribute(JWTTokenNeededFilter.USER);
        User user = entityManager.find(User.class, id);
        entityManager.persist(user);

        return Response.ok().build();
    }

    @POST
    @Path("/uploadUserPhoto")
    @Consumes(MULTIPART_FORM_DATA)
    @JWTTokenNeeded
    public Response uploadPhoto(@Context HttpRequest request, MultipartFormDataInput input) {
        Integer id = (Integer) request.getAttribute(JWTTokenNeededFilter.USER);
        User user = entityManager.find(User.class, id);

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        StringBuilder binaryString = new StringBuilder();

        for (InputPart inputPart : inputParts) {
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                for (byte b : bytes) {
                    binaryString.append(b);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Avatar avatar = new Avatar();
        avatar.setBinary(binaryString.toString());
        user.setAvatar(avatar);

        entityManager.persist(user);

        return Response.ok().build();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
