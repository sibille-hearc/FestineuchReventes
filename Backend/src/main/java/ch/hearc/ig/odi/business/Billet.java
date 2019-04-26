package ch.hearc.ig.odi.business;

public class Billet {
  private int id;
  private String uid;
  private String prenom;
  private String nom;
  private String dateNaissance;
  private String email;
  private String gender;
  private int prixBillet;
  private String adresseIP;
  private String dateAchat;

  public Billet() {
  }

  public Billet(int id, String uid, String prenom, String nom, String dateNaissance,
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
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
