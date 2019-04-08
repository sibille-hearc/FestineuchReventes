package ch.hearc.ig.odi.business;


public class Ecole {
  private int id;
  private String nom;

  private Localite localite;

  public Ecole(String nom){
    this.nom = nom;
  }

  public Ecole(String nom, Localite localite) {
    this.nom = nom;
    this.localite = localite;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public Localite getLocalite() {
    return localite;
  }

  public void setLocalite(Localite localite) {
    this.localite = localite;
  }
}
