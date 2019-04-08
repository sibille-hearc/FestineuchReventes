package ch.hearc.ig.odi.restresources;

import ch.hearc.ig.odi.persistance.Conn;
import ch.hearc.ig.odi.util.KeyGenerator;
import ch.hearc.ig.odi.util.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

@Path("/authentification")
public class AuthenticationEndpoint {

  @Context
  private UriInfo uriInfo;

  @Inject
  private KeyGenerator keyGenerator;
  @Inject
  private Conn connection = new Conn();
  @Context
  SecurityContext securityContext;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response authenticateUser(@FormParam("email") String email,
      @FormParam("password") String password) {

    try {
      connection.openConnection();
      connection.authenticate(email, password);
      connection.closeConnection();
      String token = issueToken(email);
      return Response.ok(token).build();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  private String issueToken(String email) {
    Key key = keyGenerator.generateKey();
    return Token.issueToken(email, key, uriInfo );
  }

}
