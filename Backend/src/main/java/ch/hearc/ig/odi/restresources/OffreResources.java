package ch.hearc.ig.odi.restresources;


import ch.hearc.ig.odi.business.Canton;
import ch.hearc.ig.odi.business.Categorie;
import ch.hearc.ig.odi.business.Ecole;
import ch.hearc.ig.odi.business.Offre;
import ch.hearc.ig.odi.business.Profil;
import ch.hearc.ig.odi.business.TypeRabais;
import ch.hearc.ig.odi.filter.Secured;
import ch.hearc.ig.odi.persistance.Conn;
import ch.hearc.ig.odi.util.ObjectList;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
@Path("offre")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class OffreResources {

  @Inject
  private Conn connection = new Conn();

  @GET
  public List<Offre> rechercherOffres(){
    List<Offre> offres = null;
    try{
      connection.openConnection();
      offres = connection.rechercherOffres();
      connection.commit();
      connection.closeConnection();
    }catch(JSONException | SQLException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    return offres;
  }

  @Secured
  @GET
  @Path("personnalise")
  public List<Offre> rechercherOffresPersonnalisees(@Context SecurityContext securityContext){
    List<Offre> offres = null;
    Principal principal = securityContext.getUserPrincipal();
    String email = principal.getName();
    Profil utilisateur = new Profil(email);
    try{
      connection.openConnection();
      offres = connection.rechercherOffresPersonnalisees(utilisateur);
      connection.commit();
      connection.closeConnection();
    }catch(JSONException | SQLException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    return offres;
  }

  @Secured
  @POST
  @Path("ajouter")
  public void ajouterOffre(@Context SecurityContext securityContext,
      @NotNull  @FormParam("nom") String nom,
      @FormParam("description") String description,
      @FormParam("categories") String categoriesNoms,
      @NotNull @FormParam("entreprise") String entreprise,
      @NotNull @FormParam("typeRabais") String typeRabais,
      @FormParam("dateDebut") String dateDebut,
      @FormParam("dateFin") String dateFin,
      @FormParam("lien") String lien,
      @FormParam("montant") String montant,
      @FormParam("rue") String rue,
      @FormParam("numeroRue") String numeroRue,
      @FormParam("condition") String condition,
      @FormParam("ecoles") String ecolesNoms,
      @FormParam("villes") String ville,
      @FormParam("cantons") String cantonsNoms) {
    try {
      ObjectList objectList = new ObjectList(categoriesNoms, ecolesNoms, cantonsNoms);
      List<Categorie> categories = objectList.getCategories();
      List<Canton> cantons = objectList.getCantons();
      List<Ecole> ecoles = objectList.getEcoles();
      Principal principal = securityContext.getUserPrincipal();
      String email = principal.getName();
      Profil createur = new Profil(email);
      Double montantDouble = 0.0;
      if(montant != null) {
        montantDouble = Double.parseDouble(montant);
      }
      connection.openConnection();
      connection.ajouterOffre(new Offre(nom, description, dateDebut, dateFin, entreprise, lien, numeroRue,
          rue, montantDouble, condition, categories, new TypeRabais(typeRabais),
          ecoles, cantons, createur, ville));
      connection.commit();
      connection.closeConnection();
    } catch (JSONException | SQLException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    throw new WebApplicationException(Status.OK);
  }

  @Secured
  @POST
  @Path("modifier")
  public void modifierOffre(@Context SecurityContext securityContext,
      @QueryParam("id") int id,
      @NotNull  @FormParam("nom") String nom,
      @FormParam("description") String description,
      @FormParam("categories") String categoriesNoms,
      @NotNull @FormParam("entreprise") String entreprise,
      @NotNull @FormParam("typeRabais") String typeRabais,
      @FormParam("dateDebut") String dateDebut,
      @FormParam("dateFin") String dateFin,
      @FormParam("lien") String lien,
      @FormParam("montant") String montant,
      @FormParam("rue") String rue,
      @FormParam("numeroRue") String numeroRue,
      @FormParam("condition") String condition,
      @FormParam("ecoles") String ecolesNoms,
      @FormParam("villes") String ville,
      @FormParam("cantons") String cantonsNoms) {
    try {
      ObjectList objectList = new ObjectList(categoriesNoms, ecolesNoms, cantonsNoms);
      Offre offre = new Offre(id, nom, description, dateDebut, dateFin, entreprise, lien, numeroRue,
          rue, Double.parseDouble(montant), condition, ville, new TypeRabais(typeRabais));
      connection.openConnection();
      connection.modifierOffre(offre);
      //modifier les listes
      if (!StringUtils.isEmpty(cantonsNoms)) {
        offre.setRegions(objectList.getCantons());
        connection.modifierCantons(offre);
      }
      if(!StringUtils.isEmpty(categoriesNoms)){
        offre.setCategories(objectList.getCategories());
        connection.modifierCategories(offre);
      }
      if (!StringUtils.isEmpty(ecolesNoms)) {
        offre.setPartenaires(objectList.getEcoles());
        connection.modifierEcoles(offre);
      }
      connection.commit();
      connection.closeConnection();
    } catch (JSONException | SQLException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    throw new WebApplicationException(Status.OK);
  }

  @Secured
  @POST
  @Path("verifier")
  public void verifierOffre(@Context SecurityContext securityContext, @QueryParam("id") int id,
      @FormParam("estvalidee") String estValidee){
    try {
      Principal principal = securityContext.getUserPrincipal();
      String email = principal.getName();
      Profil verificateur = new Profil(email);
      connection.openConnection();
      connection.validerOffre(id, estValidee.charAt(0), verificateur);
      connection.commit();
      connection.closeConnection();
    } catch (JSONException | SQLException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    throw new WebApplicationException(Status.OK);
  }

  @Secured
  @POST
  @Path("aime")
  public void aimerOffre(@Context SecurityContext securityContext, @FormParam("titreOffre") String offre,
      @FormParam("aime") String aime){
    try {
      Principal principal = securityContext.getUserPrincipal();
      String email = principal.getName();
      connection.openConnection();
      connection.aimerOffre(offre, aime.charAt(0), email);
      connection.commit();
      connection.closeConnection();
    } catch (JSONException | SQLException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    throw new WebApplicationException(Status.OK);
  }

  @DELETE
  @Secured
  public void supprimerOffre(@NotNull @QueryParam("id") int id) {
    try {
      connection.openConnection();
      connection.supprimerOffre(id);
      connection.commit();
      connection.closeConnection();
    } catch (JSONException | SQLException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    throw new WebApplicationException(Status.OK);
  }
}
