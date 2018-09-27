package app.filter;

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

import anotation.JWTTokenNeeded;
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

	public static final String USER_ID = "USER_ID";

	@Override
	public void filter(ContainerRequestContext requestContext)
	{

		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();

		try {

			// Validate the token
			Key key = keyGenerator.generateKey();
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			logger.info("#### valid token : " + token);

			requestContext.setProperty(USER_ID, token);

		} catch (Exception e) {
			logger.severe("#### invalid token : " + token);
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}
}
