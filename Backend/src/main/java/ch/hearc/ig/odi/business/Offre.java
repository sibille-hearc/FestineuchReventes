package ch.hearc.ig.odi.business;

import java.util.List;

public class Offre {
  private int id;
  private String nom;
  private String description;
  private String debutValidite;
  private String finValidite;
  private String pathImage; //abandonné
  private String entreprise;
  private char estValidee;
  private String url;
  private String numeroRue;
  private String rue;
  private double montant;
  private String condition;
  private String ville;

  private List<Categorie> categories;
  private TypeRabais typeRabais;
  private List<Ecole> partenaires;
  private List<Canton> regions;
  private Profil verificateur;
  private Profil createur;
  private Localite localite;


  public Offre(String nom, String entreprise,
      List<Categorie> categories, TypeRabais typeRabais) {
    this.nom = nom;
    this.entreprise = entreprise;
    this.categories = categories;
    this.typeRabais = typeRabais;
    this.estValidee = '0';
  }

  public Offre(String nom, String description, String debutValidite, String finValidite,
      String entreprise, String url, String numeroRue, String rue, double montant, String condition,
       List<Categorie> categories, TypeRabais typeRabais,
      List<Ecole> partenaires, List<Canton> regions, Profil createur, String ville) {
    this.nom = nom;
    this.description = description;
    this.debutValidite = debutValidite;
    this.finValidite = finValidite;
    this.entreprise = entreprise;
    this.estValidee = '0';
    this.url = url;
    this.numeroRue = numeroRue;
    this.rue = rue;
    this.montant = montant;
    this.condition = condition;
    this.ville = ville;
    this.typeRabais = typeRabais;
    this.categories = categories;
    this.partenaires = partenaires;
    this.createur = createur;
    this.regions = regions;
  }

  public Offre(int id, String nom, String description, String debutValidite, String finValidite,
      String entreprise, String url, String numeroRue, String rue, double montant,
      String condition, String ville, TypeRabais typeRabais) {
    this.id = id;
    this.nom = nom;
    this.description = description;
    this.debutValidite = debutValidite;
    this.finValidite = finValidite;
    this.entreprise = entreprise;
    this.url = url;
    this.numeroRue = numeroRue;
    this.rue = rue;
    this.montant = montant;
    this.condition = condition;
    this.ville = ville;
    this.typeRabais = typeRabais;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNom() {    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDebutValidite() {
    return debutValidite;
  }

  public void setDebutValidite(String debutValidite) {
    this.debutValidite = debutValidite;
  }

  public String getFinValidite() {
    return finValidite;
  }

  public void setFinValidite(String finValidite) {
    this.finValidite = finValidite;
  }

  public String getPathImage() {
    return pathImage;
  }

  public void setPathImage(String pathImage) {
    this.pathImage = pathImage;
  }

  public String getEntreprise() {
    return entreprise;
  }

  public void setEntreprise(String entreprise) {
    this.entreprise = entreprise;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getNumeroRue() {
    return numeroRue;
  }

  public void setNumeroRue(String numeroRue) {
    this.numeroRue = numeroRue;
  }

  public String getRue() {
    return rue;
  }

  public void setRue(String rue) {
    this.rue = rue;
  }

  public List<Categorie> getCategories() {
    return categories;
  }

  public void setCategories(List<Categorie> categories) {
    this.categories = categories;
  }

  public TypeRabais getTypeRabais() {
    return typeRabais;
  }

  public void setTypeRabais(TypeRabais typeRabais) {
    this.typeRabais = typeRabais;
  }

  public List<Ecole> getPartenaires() {
    return partenaires;
  }

  public void setPartenaires(List<Ecole> partenaires) {
    this.partenaires = partenaires;
  }

  public List<Canton> getRegions() {
    return regions;
  }

  public void setRegions(List<Canton> regions) {
    this.regions = regions;
  }

  public Profil getVerificateur() {
    return verificateur;
  }

  public void setVerificateur(Profil verificateur) {
    this.verificateur = verificateur;
  }

  public Profil getCreateur() {
    return createur;
  }

  public void setCreateur(Profil createur) {
    this.createur = createur;
  }

  public char getEstValidee() {
    return estValidee;
  }

  public void setEstValidee(char estValidee) {
    this.estValidee = estValidee;
  }

  public double getMontant() {
    return montant;
  }

  public void setMontant(double montant) {
    this.montant = montant;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public Localite getLocalite() {
    return localite;
  }

  public void setLocalite(Localite localite) {
    this.localite = localite;
  }

  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public void setGuillemets() {
    this.nom = "'" + this.nom + "'";
    this.description = "'" + this.description + "'";
    this.rue = "'" + this.rue + "'";
    this.numeroRue = "'" + this.numeroRue + "'";
    this.finValidite = "'" + this.finValidite + "'";
    this.debutValidite = "'" + this.debutValidite + "'";
    this.condition = "'" + this.condition + "'";
    this.entreprise = "'" + this.entreprise + "'";
    this.url = "'" + this.url + "'";
  }
}
