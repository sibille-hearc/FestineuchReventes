package ch.hearc.ig.odi.business;

public class Localite {
  private int id;
  private String ville;
  private String npa;

  private Canton canton;

  public Localite(String ville, String npa, Canton canton) {
    this.ville = ville;
    this.npa = npa;
    this.canton = canton;
  }
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public String getNpa() {
    return npa;
  }

  public void setNpa(String npa) {
    this.npa = npa;
  }
}
