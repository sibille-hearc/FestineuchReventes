package ch.hearc.ig.odi.rest;

import ch.hearc.ig.odi.persistance.DataManager;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.lang3.StringUtils;

@Path("client")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class Revente {

  @POST
  @Path("ajouter")
  public Response ajouterOffre(
      @NotNull @FormParam("numero") String numero,
      @NotNull @FormParam("prenom") String prenom,
      @NotNull @FormParam("nom") String nom) {
    try {
      DataManager DM = new DataManager();
      //DM.addTicketCSV(numero, prenom, nom);
      DM.changeTicketOwner("FES46240", "Nicolas", "Sibille", "nicolas.sibille@he-arc.ch","15.02.1994", "Male");
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    return Response.ok().build();
  }

}