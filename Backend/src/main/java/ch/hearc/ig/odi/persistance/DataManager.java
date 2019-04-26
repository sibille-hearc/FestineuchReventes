package ch.hearc.ig.odi.persistance;

import ch.hearc.ig.odi.business.Billet;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.*;

public class DataManager {

  private final char SEPARATOR = ';';
  private final String SOURCE = "U:\\BilletsVendus.csv";
  private final int COL_UID = 1;
  private final int COL_PRENOM = 2;
  private final int COL_NOM = 3;
  private final int COL_DATERACHAT = 4;
  private final int COL_EMAIL = 5;
  private final int COL_DATENAISSANCE = 6;
  private final int COL_GENRE = 7;
  private final int COL_ENVENTE = 11;

  private void updateCSV(String  replace, int row, int col) throws IOException {
      CSVReader reader = new CSVReader(new FileReader(SOURCE),SEPARATOR);
      List<String[]> csvBody = reader.readAll();
      csvBody.get(row)[col]=replace;
      reader.close();
      CSVWriter writer = new CSVWriter(new FileWriter(SOURCE),SEPARATOR,' ');
      writer.writeAll(csvBody);
      writer.flush();
      writer.close();
  }

  private Billet readLine(int LineNumber) throws IOException {
    CSVReader reader = new CSVReader(new FileReader(SOURCE),SEPARATOR);
    String[] tabLine = reader.readAll().get(LineNumber);
    reader.close();
    Billet billet = new Billet();
    billet.setUid(tabLine[COL_UID]);
    billet.setNom(tabLine[COL_NOM]);
    billet.setPrenom(tabLine[COL_PRENOM]);
    billet.setEmail(tabLine[COL_EMAIL]);
    billet.setDateNaissance(tabLine[COL_DATENAISSANCE]);
    billet.setGender(tabLine[COL_GENRE]);
    return billet;
  }

  public void addTicketCSV(String numero, String prenom, String nom) throws Exception {
      CSVWriter writer = new CSVWriter(new FileWriter(SOURCE, true), SEPARATOR);
      String[] record = ("1001," + numero + "," + prenom + "," + nom
          + ",15.02.1994,nicolas.sibille@he-arc.ch,Male,90,111.38.53.10,08.04.2019").split(",");
      writer.writeNext(record);
      writer.flush();
      writer.close();
  }

  private int searchLinebyTicketNumber(String ticketNumber) throws Exception {
    CSVReader reader = new CSVReader(new FileReader(SOURCE), SEPARATOR);
    List<String[]> csvBody = reader.readAll();
    for (int i = 0; i < csvBody.size(); i ++) {
      if(csvBody.get(i)[COL_UID].equals(ticketNumber)) {
        return i;
      }
    }
    return -1;
  }

  public void changeTicketOwner(String oldUID, String newPrenom, String newNom, String newEmail, String newDateNaissance, String newGenre) throws Exception {
    if(newGenre != "Male" && newGenre != "Female") {
      throw new Exception("Le genre du nouvel acheteur est incorrect");
    }
    int line = searchLinebyTicketNumber(oldUID);
    String newUID = oldUID.replace("FES","FESR");
    updateCSV(newUID, line, COL_UID);
    updateCSV(newPrenom, line, COL_PRENOM);
    updateCSV(newNom, line, COL_NOM);
    updateCSV(newEmail, line, COL_EMAIL);
    updateCSV(newDateNaissance, line, COL_DATENAISSANCE);
    updateCSV(newGenre, line, COL_GENRE);
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    String strDateToday = formatter.format(new Date());
    updateCSV(strDateToday, line, COL_DATERACHAT);
    updateCSV("oui", line, COL_ENVENTE);
  }

  public Billet getTicketInfo(String UID) throws Exception {
    int line = searchLinebyTicketNumber(UID);
    return readLine(line);
  }

}
