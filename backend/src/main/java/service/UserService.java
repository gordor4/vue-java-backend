package service;

import bean.MailBean;
import bean.UserBean;
import domain.TokenResponse;
import domain.user.Avatar;
import domain.user.EmailReset;
import domain.user.ProfileUser;
import domain.user.User;
import filter.AuthFilter;
import filter.NeedAuth;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.spi.HttpRequest;
import utils.KeyGenerator;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.*;
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

    @Inject
    private UserBean userBean;

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
        User user = userBean.findUser(login);

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
            //TODO: fix send email on error
            String userPassword = user.getPassword();
            user.setPassword(PasswordUtil.getSaltedHash(userPassword));
            user.setCreationDate(new Date());
            user.setActivated(false);
            user.setBanned(false);

            entityManager.persist(user);
            String id = String.valueOf(user.getId());

            mailBean.sendEmailActivation(user.getEmail(), user.getUsername());

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
    @NeedAuth
    public Response findUser(@Context HttpRequest request) {
        User user = userBean.findUser(request);

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
    @NeedAuth
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response resetPassword(@Context HttpRequest request, @FormParam("newPassword") String newPassword) {
        try {
            User user = userBean.findUser(request);
            user.setPassword(PasswordUtil.getSaltedHash(newPassword));
            entityManager.persist(user);

            return Response.ok().build();
        } catch (Exception ex) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/findUser")
    @NeedAuth
    public Response getUser(User userParam) {
        User user = userBean.findUser(userParam.getUsername());
        return Response.ok(user).build();
    }

    @POST
    @Path("/activateUser")
    @NeedAuth
    public Response activateAccount(@Context HttpRequest request) {
        User user = userBean.findUser(request);
        user.setActivated(true);

        entityManager.persist(user);
        return Response.ok().build();
    }

    @POST
    @Path("/updateUser")
    @NeedAuth
    public Response updateUserData(@Context HttpRequest request, ProfileUser profileUser) {
        User user = userBean.findUser(request);
        user.setFirstName(profileUser.getFirstName());
        user.setLastName(profileUser.getLastName());
        user.setSecondName(profileUser.getSecondName());

        entityManager.persist(user);
        return Response.ok().build();
    }

    @POST
    @Path("/uploadUserPhoto")
    @Consumes(MULTIPART_FORM_DATA)
    @NeedAuth
    public Response uploadPhoto(@Context HttpRequest request, MultipartFormDataInput input) {
        Integer id = (Integer) request.getAttribute(AuthFilter.USER);
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
        entityManager.persist(avatar);

        user.setAvatar(avatar);
        entityManager.persist(user);

        return Response.ok().build();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
