package service;

import domain.EmailReset;
import domain.TokenResponse;
import domain.User;
import filter.JWTTokenNeeded;
import filter.JWTTokenNeededFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jboss.resteasy.spi.HttpRequest;
import utils.KeyGenerator;
import utils.MailUtil;
import utils.PasswordUtil;

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
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.ietf.jgss.GSSException.UNAUTHORIZED;

@Stateless
@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UserService {
    @Context
    private UriInfo uriInfo;

    @EJB
    private MailUtil mailUtil;

    @Inject
    @Default
    private KeyGenerator keyGenerator;

    @PersistenceContext(unitName = "postgres")
    private EntityManager entityManager;

    private Logger logger = Logger.getLogger("UserService");

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

            //TODO: add email check or unique attr to email on db
            entityManager.persist(user);


            System.out.println("Before send email");
            mailUtil.sendEmailActivation(user.getEmail(), user.getUsername());

            System.out.println("After send email");

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
        String username = (String)request.getAttribute(JWTTokenNeededFilter.USER);
        TypedQuery query = entityManager.createNamedQuery(User.FIND_USER, User.class);
        query.setParameter("username", username);

        User user = (User)query.getSingleResult();

        return Response.ok(user).build();
    }

    @POST
    @Path("/reset")
    public Response sendResetEmail(EmailReset email) {
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER_WITH_EMAIL, User.class);
        query.setParameter("email", email.getEmail());

        User user = query.getResultList().get(0);
        if(user != null) {
            String token = issueToken(user.getUsername(), String.valueOf(user.getId()));

            mailUtil.sendResetPassword(user.getEmail(), user.getUsername(), token);

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

            User user = (User)query.getSingleResult();
            user.setPassword(PasswordUtil.getSaltedHash(newPassword));
            entityManager.persist(user);

            return Response.ok().build();
        } catch (Exception ex) {
            return Response.serverError().build();
        }
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

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
