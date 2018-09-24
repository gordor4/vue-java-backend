package service;

import bean.UserBean;
import domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import utils.KeyGenerator;

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
import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.ietf.jgss.GSSException.UNAUTHORIZED;
import static org.jboss.resteasy.util.HttpHeaderNames.AUTHORIZATION;

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
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        try {
            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private void authenticate(String login, String password) {
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER, User.class);
        query.setParameter("username", login);
        //TODO: use encoded password PasswordUtil.digestPassword(password)
        query.setParameter("password", password);
        User user = query.getSingleResult();

        if (user == null)
            throw new SecurityException("Invalid user/password");
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();

        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;

    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        entityManager.persist(user);

        String id = String.valueOf(user.getId());
        return Response.created(
                uriInfo.getAbsolutePathBuilder()
                        .path(id)
                        .build()
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") String id) {
        User user = entityManager.find(User.class, id);

        if (user == null)
            return Response.status(NOT_FOUND).build();

        return Response.ok(user).build();
    }

    @GET
    @Path("/get")
    public Response findAllUsers() {
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_ALL, User.class);
        List<User> allUsers = query.getResultList();

        if (allUsers == null)
            return Response.status(NOT_FOUND).build();

        return Response.ok(allUsers).build();
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
