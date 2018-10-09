package service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.*;

import bean.UserBean;
import domain.Password;
import domain.TokenResponse;
import domain.User;
import filter.JWTTokenNeeded;
import filter.JWTTokenNeededFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jboss.resteasy.spi.HttpRequest;
import utils.KeyGenerator;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.ietf.jgss.GSSException.UNAUTHORIZED;

@Stateless
@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class UserService {
    @Inject
    UserBean userBean;

    @Context
    private UriInfo uriInfo;

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
            // Authenticate the user using the credentials provided
            String id = authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username, id);

            TokenResponse tokenResponse = new TokenResponse(token);

            // Return the token on the response
            return Response.ok(tokenResponse).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private String authenticate(String login, String password) throws Exception {
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER, User.class);
        query.setParameter("username", login);
        User user = query.getSingleResult();

        if (user == null || !Password.check(password, user.getPassword()))
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
            String userPassword = user.getPassword();
            user.setPassword(Password.getSaltedHash(userPassword));

            entityManager.persist(user);

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
        Integer id = (Integer) request.getAttribute(JWTTokenNeededFilter.USER_ID);
        User user = entityManager.find(User.class, id);

        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") String id) {
        entityManager.remove(entityManager.getReference(User.class, id));
        return Response.noContent().build();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
