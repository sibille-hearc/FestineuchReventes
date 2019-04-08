package ch.hearc.ig.odi.business;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Profil {
  private int id;
  private String email;
  private String motDePasse;
  private String prenom;
  private String nom;
  private String dateNaissance;
  private char sexe;
  private char nouvelleNotification;
  private int estAdministrateur;

  private List<Ecole> ecoles;
  private List<Canton> cantons;
  private List<Categorie> categories;

  public Profil(String email){
    this.email = email;
    this.ecoles = new ArrayList<>();
    this.cantons = new ArrayList<>();
    this.categories = new ArrayList<>();
  }

  public Profil(String email, String motDePasse, String prenom, String nom,
      String dateNaissance, char sexe, char nouvelleNotification, int estAdministrateur,
      List<Ecole> ecoles, List<Canton> cantons,
      List<Categorie> categories) {
    this.email = email;
    this.motDePasse = motDePasse;
    this.prenom = prenom;
    this.nom = nom;
    this.dateNaissance = dateNaissance;
    this.sexe = sexe;
    this.nouvelleNotification = nouvelleNotification;
    this.estAdministrateur = estAdministrateur;
    this.ecoles = ecoles;
    this.cantons = cantons;
    this.categories = categories;
  }

  public void ajouterEcole(Ecole ecole){
    this.ecoles.add(ecole);
  }

  public void retirerEcole(Ecole ecole){
    this.ecoles.remove(ecole);
  }

  public void ajouterCanton(Canton canton){
    this.cantons.add(canton);
  }

  public void retirerCanton(Canton canton){
    this.cantons.remove(canton);
  }

  public void ajouterCategorie(Categorie categorie){
    this.categories.add(categorie);
  }

  public void retirerCategorie(Categorie categorie){
    this.categories.remove(categorie);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getDateNaissance() {
    return dateNaissance;
  }

  public void setDateNaissance(String dateNaissance) {
    this.dateNaissance = dateNaissance;
  }

  public char getSexe() {
    return sexe;
  }

  public void setSexe(char sexe) {
    this.sexe = sexe;
  }

  public char getNouvelleNotification() {
    return nouvelleNotification;
  }

  public void setNouvelleNotification(char nouvelleNotification) {
    this.nouvelleNotification = nouvelleNotification;
  }

  public int getEstAdministrateur() {
    return estAdministrateur;
  }

  public void setEstAdministrateur(int estAdministrateur) {
    this.estAdministrateur = estAdministrateur;
  }

  public List<Ecole> getEcoles() {
    return ecoles;
  }

  public void setEcoles(List<Ecole> ecoles) {
    this.ecoles = ecoles;
  }

  public List<Canton> getCantons() {
    return cantons;
  }

  public void setCantons(List<Canton> cantons) {
    this.cantons = cantons;
  }

  public List<Categorie> getCategories() {
    return categories;
  }

  public void setCategories(List<Categorie> categories) {
    this.categories = categories;
  }

  public String getIdsCategories() {
    StringBuilder listIdCategories = new StringBuilder();
    for(Categorie c : categories){
      listIdCategories.append(c.getId()).append(",");
    }
    if(StringUtils.isEmpty(listIdCategories)) {
      return listIdCategories.toString();
    }
    return listIdCategories.toString().substring(0, listIdCategories.length() - 1);
  }

  public String getIdsCantons() {
    StringBuilder listIdCantons = new StringBuilder();
    for(Canton c : cantons){
      listIdCantons.append(c.getId()).append(",");
    }
    if(StringUtils.isEmpty(listIdCantons)) {
      return listIdCantons.toString();
    }
    return listIdCantons.toString().substring(0, listIdCantons.length() - 1);
  }

  public String getIdsEcoles() {
    StringBuilder listIdEcoles = new StringBuilder();
    for(Ecole e : ecoles){
      listIdEcoles.append(e.getId()).append(",");
    }
    if(StringUtils.isEmpty(listIdEcoles)) {
      return listIdEcoles.toString();
    }
    return listIdEcoles.toString().substring(0, listIdEcoles.length() - 1);
  }
}
