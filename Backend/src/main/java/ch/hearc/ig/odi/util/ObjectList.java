package ch.hearc.ig.odi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ObjectList {

  private List<Categorie> categories;
  private List<Canton> cantons;
  private List<Ecole> ecoles;

  public ObjectList(String categoriesNoms, String ecolesNoms, String cantonsNoms) {
    categories = new ArrayList<>();
    if(!StringUtils.isEmpty(categoriesNoms)){
      categories = getCategoriesByNames(Arrays.asList(categoriesNoms.split(",")));
    }
    cantons = new ArrayList<>();
    if(!StringUtils.isEmpty(cantonsNoms)){
      cantons = getCantonsByNames(Arrays.asList(cantonsNoms.split(",")));
    }
    ecoles = new ArrayList<>();
    if(!StringUtils.isEmpty(ecolesNoms)){
      ecoles = getEcolesByNames(Arrays.asList(ecolesNoms.split(",")));
    }
  }

  public List<Categorie> getCategories() {
    return categories;
  }

  public List<Canton> getCantons() {
    return cantons;
  }

  public List<Ecole> getEcoles() {
    return ecoles;
  }

  private List<Categorie> getCategoriesByNames(List<String> categoriesNoms){
    List<Categorie> categories = new ArrayList<>();
    for(String categorieNom : categoriesNoms){
      Categorie c = new Categorie(categorieNom);
      categories.add(c);
    }
    return categories;
  }

  private List<Canton> getCantonsByNames(List<String> cantonsNoms){
    List<Canton> cantons = new ArrayList<>();
    for(String cantonNom : cantonsNoms){
      Canton c = new Canton(cantonNom);
      cantons.add(c);
    }
    return cantons;
  }

  private List<Ecole> getEcolesByNames(List<String> ecolesNoms){
    List<Ecole> ecoles = new ArrayList<>();
    for(String ecoleNom : ecolesNoms){
      Ecole e = new Ecole(ecoleNom);
      ecoles.add(e);
    }
    return ecoles;
  }
}
