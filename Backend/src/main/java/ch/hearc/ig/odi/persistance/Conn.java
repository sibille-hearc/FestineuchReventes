package ch.hearc.ig.odi.persistance;

import ch.hearc.ig.odi.business.Canton;
import ch.hearc.ig.odi.business.Categorie;
import ch.hearc.ig.odi.business.Ecole;
import ch.hearc.ig.odi.business.Offre;
import ch.hearc.ig.odi.business.Profil;
import ch.hearc.ig.odi.business.TypeRabais;
import ch.hearc.ig.odi.exception.AuthenticationException;
import ch.hearc.ig.odi.restresources.OffreResources;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Conn {
  private Connection cnn = null;

  public void openConnection() throws SQLException {
    cnn = DBDOracleConnectionPoolDataSource.getConnection();
  }

  public void closeConnection() throws SQLException {
    cnn.close();
  }

  public void commit() throws SQLException {
    cnn.commit();
  }

  public Profil createProfil(Profil profil){
    Statement stmt = null;
    ResultSet rs = null;
    int id = 0;
    try {
      stmt = cnn.createStatement();
      String sql = "INSERT INTO PROFILS (email, motdepasse, prenom, nom, datenaissance, "
          + "sexe, notification, admin) VALUES ('"
          + profil.getEmail() + "', '" + profil.getMotDePasse() + "','" + profil.getPrenom()+ "','" +
          profil.getNom()+ "','" + profil.getDateNaissance()+ "','" + profil.getSexe()+ "','" +
          profil.getNouvelleNotification()+ "'," + profil.getEstAdministrateur() + ")";
      stmt.executeUpdate(sql);

      sql = "Select numero From Profils where email = '"+profil.getEmail()+"'";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        id = rs.getInt("numero");
      }
      profil.setId(id);
      addCantons(profil);
      addCategories(profil);
      addEcoles(profil);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if(stmt != null){
        try{stmt.close();} catch (SQLException ignored){}
      }
      if(rs != null){
        try{rs.close();} catch (SQLException ignored){}
      }
    }
    return profil;
  }

  public Profil modifierNotification(Profil profil){
    Statement stmt = null;
    ResultSet rs = null;
    int id = 0;
    try {
      stmt = cnn.createStatement();
      String sql = "Select numero From Profils where email = '"+profil.getEmail()+"'";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        id = rs.getInt("numero");
      }
      profil.setId(id);

      sql = "UPDATE PROFILS SET NOTIFICATION = NVL('" + profil.getNouvelleNotification()
          + "', NOTIFICATION) WHERE NUMERO ='" + profil.getId() + "'";
      stmt.executeUpdate(sql);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if(stmt != null){
        try{stmt.close();} catch (SQLException ignored){}
      }
      if(rs != null){
        try{rs.close();} catch (SQLException ignored){}
      }
    }
    return profil;
  }

  public void modifierCantons(Profil profil) {
    try (Statement stmt = cnn.createStatement()) {
      String sql = "DELETE INTERETLIEUX WHERE PRO_NUMERO =" + profil.getId();
      stmt.executeUpdate(sql);
      addCantons(profil);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void modifierCategories(Profil profil) {
    try (Statement stmt = cnn.createStatement()) {
      String sql = "DELETE INTERETCATEGORIES WHERE PRO_NUMERO =" + profil.getId();
      stmt.executeUpdate(sql);
      addCategories(profil);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void modifierEcoles(Profil profil) {
    try (Statement stmt = cnn.createStatement()) {
      String sql = "DELETE ETUDIERS WHERE PRO_NUMERO =" + profil.getId();
      stmt.executeUpdate(sql);
      addEcoles(profil);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void authenticate(String email, String password) throws Exception {
    PreparedStatement prestmt = null;
    ResultSet rs = null;
    try {
      prestmt = cnn.prepareStatement("SELECT * FROM PROFILS where email=? and motdepasse=?");
      prestmt.setString(1, email);
      prestmt.setString(2, password);
      rs = prestmt.executeQuery();
      if(!rs.next()) {
        throw new AuthenticationException("invalid credentials");
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if(prestmt != null){
        try{prestmt.close();} catch (SQLException ex){}
      }
      if(rs != null){
        try{rs.close();} catch (SQLException ex){}
      }
    }
  }

  public Offre ajouterOffre(Offre offre){
    Statement stmt = null;
    ResultSet rs = null;
    int idTypeRabais = 0;
    int idCreateur = 0;
    int idLocalite = 0;
    int idOffre = 0;
    try {
      stmt = cnn.createStatement();
      //trouver l'id de la ville, du typeRabais et du créateur
      String sql = "Select numero From TYPERABAIS where libelle = '" +
          offre.getTypeRabais().getDescription() + "'";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        idTypeRabais = rs.getInt("numero");
      }
      sql = "SELECT NUMERO FROM PROFILS WHERE EMAIL = '" + offre.getCreateur().getEmail() + "'";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        idCreateur = rs.getInt("numero");
      }
      sql = "SELECT NUMERO FROM LOCALITES WHERE VILLE = '" + offre.getVille() + "'";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        idLocalite = rs.getInt("numero");
      }
      //créer l'offre
      if(!StringUtils.isEmpty(offre.getDebutValidite())){
        offre.setDebutValidite("'" + offre.getDebutValidite() + "'");
      }
      if(!StringUtils.isEmpty(offre.getFinValidite())){
        offre.setFinValidite("'" + offre.getFinValidite() + "'");
      }
      sql = "INSERT INTO OFFRES(libelle, description, organisation, rue, numerorue, lien, datedebut, "
          + "datefin, estvalidee, condition, montant, loc_numero, trab_numero, pro_numero) VALUES ('"
          + offre.getNom() + "', '" + offre.getDescription() + "','" + offre.getEntreprise()+ "','"
          + offre.getRue()+ "','" + offre.getNumeroRue()+ "','" + offre.getUrl()+ "'," + offre.getDebutValidite()
          + "," +  offre.getFinValidite() + ",'" + offre.getEstValidee()+"','" + offre.getCondition() + "',"
          + offre.getMontant() + "," + idLocalite + "," + idTypeRabais + "," + idCreateur + ")";
      stmt.executeUpdate(sql);
      //retrouver l'offre créée
      sql = "Select numero From Offres where libelle = '"+offre.getNom() + "'";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        idOffre = rs.getInt("numero");
      }
      offre.setId(idOffre);
      //ajouter ecoles, cantons et categories
      addCantons(offre);
      addCategories(offre);
      addEcoles(offre);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if(stmt != null){
        try{stmt.close();} catch (SQLException ignored){}
      }
      if(rs != null){
        try{rs.close();} catch (SQLException ignored){}
      }
    }
    return offre;
  }

  public void validerOffre(int idOffre, char estValidee, Profil verificateur) {
    Statement stmt = null;
    ResultSet rs = null;
    int idVerificateur = 0;
    try {
      stmt = cnn.createStatement();
      String sql = "SELECT NUMERO FROM PROFILS WHERE EMAIL = '" + verificateur.getEmail() + "'";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        idVerificateur = rs.getInt("numero");
      }
      sql = "UPDATE OFFRES SET ESTVALIDEE = '" + estValidee + "', PRO_NUMERO2 = " + idVerificateur + " WHERE NUMERO = " + idOffre;
      stmt.executeUpdate(sql);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if(stmt != null){
        try{stmt.close();} catch (SQLException ignored){}
      }
      if(rs != null){
        try{rs.close();} catch (SQLException ignored){}
      }
    }
  }

  public void aimerOffre(String offre, char aime, String email) {
    Statement stmt = null;
    ResultSet rs = null;
    int idOffre = 0;
    int idProfil = 0;
    try {
      stmt = cnn.createStatement();
      String sql = "SELECT NUMERO FROM OFFRES WHERE LIBELLE = '" + offre + "'";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        idOffre = rs.getInt("numero");
      }
      sql = "SELECT NUMERO FROM PROFILS WHERE EMAIL = '" + email + "'";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        idProfil = rs.getInt("numero");
      }
      sql = "INSERT INTO EVALUATIONS (aime, pro_numero, off_numero) VALUES ('" + aime + "'," + idProfil + "," + idOffre + ")";
      stmt.executeUpdate(sql);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if(stmt != null){
        try{stmt.close();} catch (SQLException ignored){}
      }
      if(rs != null){
        try{rs.close();} catch (SQLException ignored){}
      }
    }
  }

  public void supprimerOffre(int id) {
    try (Statement stmt = cnn.createStatement()) {
      String sql = "DELETE EVALUATIONS WHERE OFF_NUMERO = " + id;
      stmt.executeUpdate(sql);
      sql = "DELETE CONDITIONECOLES WHERE OFF_NUMERO = " + id;
      stmt.executeUpdate(sql);
      sql = "DELETE OFFRECATEGORIES WHERE OFF_NUMERO = " + id;
      stmt.executeUpdate(sql);
      sql = "DELETE OFFRECANTONS WHERE OFF_NUMERO = " + id;
      stmt.executeUpdate(sql);
      sql = "DELETE OFFRES WHERE NUMERO = " + id;
      stmt.executeUpdate(sql);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public List<Offre> rechercherOffres(){
    Statement stmt = null;
    ResultSet rs = null;
    List<Offre> offres = new ArrayList<>();
    try {
      stmt = cnn.createStatement();
      String sql = "SELECT numero, libelle, organisation, description, "
          + "trab_numero, lien, dateDebut, dateFin, condition, estvalidee, montant, rue, numerorue from OFFRES";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        offres.add(buildOffre(rs));
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if(stmt != null){
        try{stmt.close();} catch (SQLException ignored){}
      }
      if(rs != null){
        try{rs.close();} catch (SQLException ignored){}
      }
    }
    return offres;
  }

  public List<Offre> rechercherOffresPersonnalisees(Profil profil){
    Statement stmt = null;
    ResultSet rs = null;
    List<Offre> offres = new ArrayList<>();
    try {
      stmt = cnn.createStatement();
      String sql = "select numero from profils where email = '"+ profil.getEmail() + "'";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        profil.setId(rs.getInt("numero"));
      }
      profil.setCantons(rechercherCantonsParProfil(profil.getId()));
      profil.setCategories(rechercherCategoriesParProfil(profil.getId()));
      profil.setEcoles(rechercherEcolesParProfil(profil.getId()));

      sql = "SELECT DISTINCT o.numero, o.libelle, o.organisation, o.description, o.trab_numero, o.lien, "
          + "o.dateDebut, o.dateFin, o.condition, o.estvalidee, o.montant, o.rue, o.numerorue from OFFRES o "
          + "left join offrecategories ocg on ocg.off_numero = o.numero "
          + "left join offrecantons ocn on ocn.off_numero = o.numero left join conditionecoles oe on oe.off_numero = o.numero "
          + "where ocg.cat_numero in (" + profil.getIdsCategories() + ") or ocn.can_numero in "
          + "(" + profil.getIdsCantons() + ") or oe.eco_numero in (" + profil.getIdsEcoles() + ")";
      rs = stmt.executeQuery(sql);
      while(rs.next()){
        offres.add(buildOffre(rs));
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if(stmt != null){
        try{stmt.close();} catch (SQLException ignored){}
      }
      if(rs != null){
        try{rs.close();} catch (SQLException ignored){}
      }
    }
    return offres;
  }

  public void modifierOffre(Offre offre) {
    ResultSet rs = null;
    int idLocalite = 0;
    int idTRab = 0;
    try (Statement stmt = cnn.createStatement()) {
      String sql = "SELECT NUMERO FROM LOCALITES WHERE VILLE = '" + offre.getVille() + "'";
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        idLocalite = rs.getInt("numero");
      }
      sql =
          "SELECT NUMERO FROM TYPERABAIS WHERE LIBELLE = '" + offre.getTypeRabais().getDescription()
              + "'";
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        idTRab = rs.getInt("numero");
      }
      if(!StringUtils.isEmpty(offre.getDebutValidite())){
        offre.setDebutValidite("'" + offre.getDebutValidite() + "'");
      }
      if(!StringUtils.isEmpty(offre.getFinValidite())){
        offre.setFinValidite("'" + offre.getFinValidite() + "'");
      }
      sql = "UPDATE OFFRES SET LIBELLE = NVL('" + offre.getNom()
          + "', LIBELLE), DESCRIPTION = NVL('" + offre.getDescription()
          + "', DESCRIPTION), ORGANISATION = NVL('"
          + offre.getEntreprise() + "', ORGANISATION), RUE = NVL('" + offre.getRue()
          + "', RUE), NUMERORUE = NVL('"
          + offre.getNumeroRue() + "', NUMERORUE), LIEN = NVL('" + offre.getUrl()
          + "', LIEN), DATEDEBUT = NVL("
          + offre.getDebutValidite() + ", DATEDEBUT), DATEFIN = NVL(" + offre.getFinValidite()
          + ", DATEFIN), CONDITION = NVL('" + offre.getCondition() + "', CONDITION), MONTANT = NVL("
          + offre.getMontant()
          + ", MONTANT), LOC_NUMERO = NVL(" + idLocalite + ", LOC_NUMERO), TRAB_NUMERO = NVL("
          + idTRab
          + ", TRAB_NUMERO) WHERE NUMERO = " + offre.getId();
      stmt.executeUpdate(sql);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void modifierCantons(Offre offre) {
    try (Statement stmt = cnn.createStatement()) {
      String sql = "DELETE OFFRECANTONS WHERE OFF_NUMERO =" + offre.getId();
      stmt.executeUpdate(sql);
      addCantons(offre);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void modifierCategories(Offre offre) {
    try (Statement stmt = cnn.createStatement()) {
      String sql = "DELETE OFFRECATEGORIES WHERE OFF_NUMERO =" + offre.getId();
      stmt.executeUpdate(sql);
      addCategories(offre);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void modifierEcoles(Offre offre) {
    try (Statement stmt = cnn.createStatement()) {
      String sql = "DELETE CONDITIONECOLES WHERE OFF_NUMERO =" + offre.getId();
      stmt.executeUpdate(sql);
      addEcoles(offre);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }
  public Profil ajouterEcoles(Profil profil){
    ResultSet rs = null;
    try(Statement stmt = cnn.createStatement()) {
      String sql = "select numero from profils where email = '" + profil.getEmail()+"'";
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        profil.setId(rs.getInt("numero"));
      }
      profil.setEcoles(rechercherEcolesParProfil(profil.getId()));
    }catch(SQLException ex){
      throw new RuntimeException(ex);
    }
    return profil;
  }

  public Profil ajouterCategories(Profil profil){
    ResultSet rs = null;
    try(Statement stmt = cnn.createStatement()) {
      String sql = "select numero from profils where email = '" + profil.getEmail() + "'";
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        profil.setId(rs.getInt("numero"));
      }
      profil.setCategories(rechercherCategoriesParProfil(profil.getId()));
    }catch(SQLException ex){
      throw new RuntimeException(ex);
    }
    return profil;
  }

  public Profil setAdminNotif(Profil profil){
    ResultSet rs = null;
    try(Statement stmt = cnn.createStatement()) {
      String sql = "select admin, notification from profils where email = '" + profil.getEmail() + "'";
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        profil.setEstAdministrateur(rs.getInt("admin"));
        profil.setNouvelleNotification(rs.getString("notification").charAt(0));
      }
    }catch(SQLException ex){
      throw new RuntimeException(ex);
    }
    return profil;
  }

  public Profil ajouterCantons(Profil profil) {
    ResultSet rs = null;
    try(Statement stmt = cnn.createStatement()) {
      String sql = "select numero from profils where email = '" + profil.getEmail() + "'";
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        profil.setId(rs.getInt("numero"));
      }
      profil.setCantons(rechercherCantonsParProfil(profil.getId()));
    }catch(SQLException ex){
      throw new RuntimeException(ex);
    }
    return profil;
  }

  //////////////////////////////////////////////PRIVATE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private Offre buildOffre(ResultSet rs){
    Offre offre;
    try {
      offre = new Offre(rs.getString("libelle"), rs.getString("organisation"),
          rechercherCategoriesOffre(rs.getInt("numero")),
          new TypeRabais(rechercherTypeRabaisDepuisId(rs.getInt("trab_numero"))));
      offre.setId(rs.getInt("numero"));
      offre.setDescription(rs.getString("description"));
      offre.setUrl(rs.getString("lien"));
      offre.setDebutValidite(rs.getString("dateDebut"));
      offre.setFinValidite(rs.getString("dateFin"));
      offre.setCondition(rs.getString("condition"));
      offre.setMontant(rs.getDouble("montant"));
      offre.setEstValidee(rs.getString("estvalidee").charAt(0));
      offre.setRue(rs.getString("rue"));
      offre.setNumeroRue(rs.getString("numerorue"));
    } catch (SQLException ex){
      throw new RuntimeException(ex);
    }
    return offre;
  }

  private List<Categorie> rechercherCategoriesOffre(int idOffre){
    ResultSet rs;
    List<Categorie> categories = new ArrayList<>();
    try (Statement stmt = cnn.createStatement()) {
      String sql = "Select c.numero, c.libelle From Offres o JOIN OFFRECATEGORIES oc "
          + "on o.numero = oc.off_numero "
          + "JOIN categories c on oc.cat_numero = c.numero where o.numero = " + idOffre;
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        Categorie c = new Categorie("");
        c.setNom(rs.getString(2));
        c.setId(rs.getInt(1));
        categories.add(c);
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
    return categories;
  }
  
  private String rechercherTypeRabaisDepuisId(int id){
    ResultSet rs;
    String typeRabais = null;
    try (Statement stmt = cnn.createStatement()) {
      String sql = "Select libelle From TYPERABAIS where numero = " + id;
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        typeRabais = rs.getString("libelle");
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
    return typeRabais;
  }

  private List<Categorie> rechercherCategoriesParProfil(int id){
    ResultSet rs;
    List<Categorie> categories = new ArrayList<>();
    Categorie categorie;
    try (Statement stmt = cnn.createStatement()) {
      String sql = "Select c.numero, c.libelle From categories c "
          + "join interetCategories ic on c.numero = ic.cat_numero where ic.pro_numero = " + id;
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        categorie = new Categorie(rs.getString(2)); //libelle
        categorie.setId(rs.getInt(1)); //numero
        categories.add(categorie);
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
    return categories;
  }

  private List<Canton> rechercherCantonsParProfil(int id){
    ResultSet rs;
    List<Canton> cantons = new ArrayList<>();
    Canton canton;
    try (Statement stmt = cnn.createStatement()) {
      String sql = "Select c.numero, c.libelle From cantons c "
          + "join interetLieux il on c.numero = il.can_numero where il.pro_numero = " + id;
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        canton = new Canton(rs.getString(2)); //libelle
        canton.setId(rs.getInt(1)); //numero
        cantons.add(canton);
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
    return cantons;
  }

  private List<Ecole> rechercherEcolesParProfil(int id){
    ResultSet rs;
    List<Ecole> ecoles = new ArrayList<>();
    Ecole ecole;
    try (Statement stmt = cnn.createStatement()) {
      String sql = "Select e.numero, e.libelle From ecoles e "
          + "join etudiers et on e.numero = et.eco_numero where et.pro_numero = " + id;
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        ecole = new Ecole(rs.getString(2)); //libelle
        ecole.setId(rs.getInt(1)); //numero
        ecoles.add(ecole);
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
    return ecoles;
  }

  private void addCantons(Offre offre){
    ResultSet rs = null;
    int id = 0;
    try (Statement stmt = cnn.createStatement()) {
      for (Canton c : offre.getRegions()) {
        String sql = "Select numero From cantons where libelle = '" + c.getNom() + "'";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
          id = rs.getInt("numero");
        }
        c.setId(id);
        //Ecrire dans la table de liaison
        sql = "INSERT INTO OFFRECANTONS (off_numero,can_numero) VALUES ("
            + offre.getId() + ", " + c.getId() + ")";
        stmt.executeUpdate(sql);
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  private void addCategories(Offre offre){
    ResultSet rs = null;
    int id = 0;
    try (Statement stmt = cnn.createStatement()) {
      for (Categorie c : offre.getCategories()) {
        String sql = "Select numero From categories where libelle = '" + c.getNom() + "'";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
          id = rs.getInt("numero");
        }
        c.setId(id);
        //Ecrire dans la table de liaison
        sql = "INSERT INTO OFFRECATEGORIES (off_numero,cat_numero) VALUES ("
            + offre.getId() + ", " + c.getId() + ")";
        stmt.executeUpdate(sql);
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  private void addEcoles(Offre offre){
    ResultSet rs = null;
    int id = 0;
    try (Statement stmt = cnn.createStatement()) {
      for (Ecole e : offre.getPartenaires()) {
        String sql = "Select numero From ecoles where libelle = '" + e.getNom() + "'";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
          id = rs.getInt("numero");
        }
        e.setId(id);
        //Ecrire dans la table de liaison
        sql = "INSERT INTO CONDITIONECOLES (off_numero,eco_numero) VALUES ("
            + offre.getId() + ", " + e.getId() + ")";
        stmt.executeUpdate(sql);
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  private void addCantons(Profil profil){
    Statement stmt = null;
    ResultSet rs = null;
    int id = 0;
    try {
      stmt = cnn.createStatement();
      for(Canton c : profil.getCantons()){
        String sql = "Select numero From cantons where upper(libelle) = upper('"+c.getNom()+"')";
        rs = stmt.executeQuery(sql);
        while(rs.next()){
          id = rs.getInt("numero");
        }
        c.setId(id);
        //Ecrire dans la table de liaison
        stmt = cnn.createStatement();
        sql = "INSERT INTO INTERETLIEUX (pro_numero,can_numero) VALUES ("
            + profil.getId() + ", " + c.getId() + ")";
        stmt.executeUpdate(sql);
      }
    }catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if(stmt != null){
        try{stmt.close();} catch (SQLException ex){}
      }
    }
  }

  private void addCategories(Profil profil){
    Statement stmt = null;
    ResultSet rs = null;
    int id = 0;
    try {
      stmt = cnn.createStatement();
      for(Categorie c : profil.getCategories()){

        String sql = "Select numero From categories where upper(libelle) = upper('"+c.getNom()+"')";
        rs = stmt.executeQuery(sql);
        while(rs.next()){
          id = rs.getInt("numero");
        }
        c.setId(id);

        //Ecrire dans la table de liaison
        stmt = cnn.createStatement();
        sql = "INSERT INTO INTERETCATEGORIES (pro_numero,cat_numero) VALUES ("
            + profil.getId() + ", " + c.getId() + ")";
        stmt.executeUpdate(sql);
      }
    }catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if(stmt != null){
        try{stmt.close();} catch (SQLException ex){}
      }
    }
  }

  private void addEcoles(Profil profil) {
    Statement stmt = null;
    ResultSet rs = null;
    int id = 0;
    try {
      stmt = cnn.createStatement();
      for (Ecole e : profil.getEcoles()) {

        String sql = "Select numero From ecoles where upper(libelle) = upper('" + e.getNom() + "')";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
          id = rs.getInt("numero");
        }
        e.setId(id);

        //Ecrire dans la table de liaison
        stmt = cnn.createStatement();
        sql = "INSERT INTO ETUDIERS (pro_numero,eco_numero) VALUES ("
            + profil.getId() + ", " + e.getId() + ")";
        stmt.executeUpdate(sql);
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException ex) {
        }
      }
    }
  }
}