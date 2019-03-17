package ru.rus.integrato.filter;

import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.GenericFilterBean;
import ru.rus.integrato.utils.KeyGenerator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.logging.Logger;


public class AuthFilter extends GenericFilterBean {
    private Logger log = Logger.getLogger("AuthFilter");
    private KeyGenerator keyGenerator;

    public static final String USER = "USER";

    public AuthFilter(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!request.getMethod().equals("OPTIONS")) {
            try {
                String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

                String token = authorizationHeader.substring("Bearer".length()).trim();
                Key key = keyGenerator.generateKey();
                String user = Jwts.parser()
                        .setSigningKey(key)
                        .parseClaimsJws(token)
                        .getBody().getSubject();

                servletRequest.setAttribute(USER, user);
            } catch (Exception e) {
                log.severe("#### invalid token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
