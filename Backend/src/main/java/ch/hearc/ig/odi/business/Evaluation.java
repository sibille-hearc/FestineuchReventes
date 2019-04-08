package ch.hearc.ig.odi.business;

public class Evaluation {
  private int id;
  private char aime;

  private Profil profil;
  private Offre offre;

  public Evaluation(char aime) {
    this.aime = aime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public char getAime() {
    return aime;
  }

  public void setAime(char aime) {
    this.aime = aime;
  }

  public Profil getProfil() {
    return profil;
  }

  public void setProfil(Profil profil) {
    this.profil = profil;
  }

  public Offre getOffre() {
    return offre;
  }

  public void setOffre(Offre offre) {
    this.offre = offre;
  }
}
