package ch.hearc.ig.odi.restresources;

import ch.hearc.ig.odi.filter.Secured;
import ch.hearc.ig.odi.persistance.Conn;
import ch.hearc.ig.odi.util.KeyGenerator;
import ch.hearc.ig.odi.util.ObjectList;
import ch.hearc.ig.odi.util.Token;
import java.security.Key;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
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
import org.apache.commons.lang3.StringUtils;

@Path("profil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ProfilResources {

  @Context
  private UriInfo uriInfo;
  @Context
  SecurityContext securityContext;
  @Inject
  private KeyGenerator keyGenerator;
  @Inject
  private Conn connection = new Conn();

  @GET
  @Secured
  public Profil getProfilByToken(@Context SecurityContext securityContext){
    Principal principal = securityContext.getUserPrincipal();
    Profil profil = new Profil(principal.getName());
    try {
      connection.openConnection();
      profil = connection.ajouterEcoles(profil);
      profil = connection.ajouterCategories(profil);
      profil = connection.ajouterCantons(profil);
      profil = connection.setAdminNotif(profil);
    }catch(SQLException ex){
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    return profil;
  }

  @POST
  @Path("creer")
  public Response createProfil(@NotNull @FormParam("email") String email,
      @NotNull @FormParam("password") String password, @NotNull @FormParam("nom") String nom,
      @NotNull @FormParam("prenom") String prenom, @NotNull @FormParam("sexe") String sexe,
      @NotNull @FormParam("dateNaissance") String dateNaissance,
      @FormParam("notification") String notification, @FormParam("cantons") String cantonsNoms,
      @NotNull @FormParam("ecoles") String ecolesNoms, @FormParam("categories") String categoriesNoms) {
    try {
      ObjectList objectList = new ObjectList(categoriesNoms, ecolesNoms, cantonsNoms);
      List<Categorie> categories = objectList.getCategories();
      List<Canton> cantons = objectList.getCantons();
      List<Ecole> ecoles = objectList.getEcoles();
      connection.openConnection();
      connection.createProfil(new Profil(email, password, prenom, nom, dateNaissance, sexe.charAt(0),
          notification.charAt(0), 0, ecoles, cantons, categories));
      connection.commit();
      connection.authenticate(email, password);
      connection.closeConnection();
      // Issue a token for the user
      String token = issueToken(email);
      // Return the token on the response
      return Response.ok(token).build();
    } catch (Exception e ) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
  }

  @POST
  @Path("modifier")
  @Secured
  public void modifierProfil(@Context SecurityContext securityContext, @NotNull @FormParam("notification") String notification,
      @FormParam("cantons") String cantonsNoms, @FormParam("ecoles") String ecolesNoms,
      @FormParam("categories") String categoriesNoms){
    try{
      ObjectList objectList = new ObjectList(categoriesNoms, ecolesNoms, cantonsNoms);
      connection.openConnection();
      //Obtenir mail
      Principal principal = securityContext.getUserPrincipal();
      String email = principal.getName();
      Profil profil = new Profil(email);
      //Ajouter modification au profil
      profil.setNouvelleNotification(notification.charAt(0));
      profil = connection.modifierNotification(profil);
      if (!StringUtils.isEmpty(cantonsNoms)) {
        profil.setCantons(objectList.getCantons());
        connection.modifierCantons(profil);
      }
      if(!StringUtils.isEmpty(categoriesNoms)){
        profil.setCategories(objectList.getCategories());
        connection.modifierCategories(profil);
      }
      if (!StringUtils.isEmpty(ecolesNoms)) {
        profil.setEcoles(objectList.getEcoles());
        connection.modifierEcoles(profil);
      }
      connection.commit();
      connection.closeConnection();
    }catch(SQLException e) {
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    throw new WebApplicationException(Status.OK);
  }

  //////////////////////////////////////////////PRIVATE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private String issueToken(String email) {
    Key key = keyGenerator.generateKey();
    return Token.issueToken(email, key, uriInfo );
  }
}
