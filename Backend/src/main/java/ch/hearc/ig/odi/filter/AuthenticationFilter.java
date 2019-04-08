package ch.hearc.ig.odi.filter;

import ch.hearc.ig.odi.util.KeyGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  private static final String REALM = "example";
  private static final String AUTHENTICATION_SCHEME = "Bearer";
  private final Logger logger = LogManager.getLogger(AuthenticationFilter.class.getName());

  @Inject
  private KeyGenerator keyGenerator;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String authorizationHeader =
        requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

    if (!isTokenBasedAuthentication(authorizationHeader)) {
      abortWithUnauthorized(requestContext);
      return;
    }

    String token = authorizationHeader
        .substring(AUTHENTICATION_SCHEME.length()).trim();

    try {
      Key key = keyGenerator.generateKey();

      Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

      logger.info(claims.getSubject());

      final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
      requestContext.setSecurityContext(new SecurityContext() {

        @Override
        public Principal getUserPrincipal() {
          return claims::getSubject;
        }

        @Override
        public boolean isUserInRole(String role) {
          return true;
        }

        @Override
        public boolean isSecure() {
          return currentSecurityContext.isSecure();
        }

        @Override
        public String getAuthenticationScheme() {
          return AUTHENTICATION_SCHEME;
        }
      });
    } catch (Exception e) {
      abortWithUnauthorized(requestContext);
    }
  }

  private boolean isTokenBasedAuthentication(String authorizationHeader) {
    return authorizationHeader != null && authorizationHeader.toLowerCase()
        .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
  }

  private void abortWithUnauthorized(ContainerRequestContext requestContext) {
    requestContext.abortWith(
        Response.status(Response.Status.UNAUTHORIZED)
            .header(HttpHeaders.WWW_AUTHENTICATE,
                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
            .build());
  }
}
