package ch.hearc.ig.odi.business;


public class Canton {
  private int id;
  private String nom;

  public Canton(String nom) {
    this.nom = nom;
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
}
