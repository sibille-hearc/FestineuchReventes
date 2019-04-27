package ch.hearc.ig.odi.rest;

import ch.hearc.ig.odi.business.Billet;
import ch.hearc.ig.odi.persistance.DataManager;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

  @GET
  @Path("ticket/{uid}")
  public Billet ticketInfos(
      @PathParam("uid") String uid
      ) {
    try {
      DataManager DM = new DataManager();
      return DM.getTicketInfo(uid);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
  }

  @POST
  @Path("achat")
  public Response achatBillet(
      @NotNull @FormParam("uid") String uid,
      @NotNull @FormParam("prenom") String prenom,
      @NotNull @FormParam("nom") String nom,
      @NotNull @FormParam("email") String email,
      @NotNull @FormParam("dateNaissance") String dateNaissance,
      @NotNull @FormParam("gender") String gender) {
    try {
      DataManager DM = new DataManager();
      DM.changeTicketOwner(uid, prenom, nom, dateNaissance, dateNaissance, gender);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    return Response.ok().build();
  }

  @POST
  @Path("miseEnVente")
  public Response miseEnVente(
      @NotNull @FormParam("uid") String uid,
      @NotNull @FormParam("prix") String prix) {
    try {
      DataManager DM = new DataManager();
      DM.listTicketAsInSale(uid, prix);
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    return Response.ok().build();
  }

  @GET
  @Path("ticketsEnVente")
  public ArrayList<Billet> ticketsEnVente() {
    try {
      DataManager DM = new DataManager();
      return DM.getTicketsInSale();
    } catch (Exception e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
  }

}