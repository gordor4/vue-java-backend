package filter;

import java.security.Key;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Jwts;
import utils.KeyGenerator;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter
{
	private Logger logger = Logger.getLogger("JWTTokenNeededFilter");

	@Inject
	private KeyGenerator keyGenerator;

	public static final String USER= "USER";
	private static final String ID_KEY = "jti";

	@Override
	public void filter(ContainerRequestContext requestContext)
	{
		try {
			String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

			String token = authorizationHeader.substring("Bearer".length()).trim();

			Key key = keyGenerator.generateKey();
			String user = Jwts.parser()
					.setSigningKey(key)
					.parseClaimsJws(token)
					.getBody().getSubject();

			logger.info("#### valid token : " + token);

			requestContext.setProperty(USER, user);
		} catch (Exception e) {
			logger.severe("#### invalid token");
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}
}
