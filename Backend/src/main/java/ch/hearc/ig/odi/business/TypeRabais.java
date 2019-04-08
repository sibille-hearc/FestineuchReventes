package ch.hearc.ig.odi.business;


public class TypeRabais {
  private int id;
  private String description;


  public TypeRabais(String description) {
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
