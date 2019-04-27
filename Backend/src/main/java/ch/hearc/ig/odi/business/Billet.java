package ch.hearc.ig.odi.business;

public class Billet {
  private String id;
  private String uid;
  private String prenom;
  private String nom;
  private String dateNaissance;
  private String email;
  private String gender;
  private int prixBillet;
  private String adresseIP;
  private String dateAchat;
  private String dateRachat;
  private String enVente;
  private String prixRachat;

  public Billet() {
  }

  public Billet(String id, String uid, String prenom, String nom, String dateNaissance,
      String email, String gender, int prixBillet, String adresseIP, String dateAchat) {
    this.id = id;
    this.uid = uid;
    this.prenom = prenom;
    this.nom = nom;
    this.dateNaissance = dateNaissance;
    this.email = email;
    this.gender = gender;
    this.prixBillet = prixBillet;
    this.adresseIP = adresseIP;
    this.dateAchat = dateAchat;
  }

  public String getDateRachat() {
    return dateRachat;
  }

  public void setDateRachat(String dateRachat) {
    this.dateRachat = dateRachat;
  }

  public String getEnVente() {
    return enVente;
  }

  public void setEnVente(String enVente) {
    this.enVente = enVente;
  }

  public String getPrixRachat() {
    return prixRachat;
  }

  public void setPrixRachat(String prixRachat) {
    this.prixRachat = prixRachat;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public int getPrixBillet() {
    return prixBillet;
  }

  public void setPrixBillet(int prixBillet) {
    this.prixBillet = prixBillet;
  }

  public String getAdresseIP() {
    return adresseIP;
  }

  public void setAdresseIP(String adresseIP) {
    this.adresseIP = adresseIP;
  }

  public String getDateAchat() {
    return dateAchat;
  }

  public void setDateAchat(String dateAchat) {
    this.dateAchat = dateAchat;
  }
}
