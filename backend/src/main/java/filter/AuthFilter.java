package filter;

import io.jsonwebtoken.Jwts;
import utils.KeyGenerator;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.security.Key;
import java.util.logging.Logger;

@Provider
@NeedAuth
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter
{
	private Logger logger = Logger.getLogger("AuthFilter");

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
