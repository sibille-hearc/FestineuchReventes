package ch.hearc.ig.odi.persistance;

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
  private final int COL_DATERACHAT = 10;
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

  public void changeTicketOwner(String oldUID, String newPrenom, String newNom) throws Exception {
    int line = searchLinebyTicketNumber(oldUID);
    String newUID = oldUID.replace("FES","FESR");
    updateCSV(newUID, line, COL_UID);
    updateCSV(newPrenom, line, COL_PRENOM);
    updateCSV(newNom, line, COL_NOM);
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    String strDateToday = formatter.format(new Date());
    updateCSV(strDateToday, line, COL_DATERACHAT);
    updateCSV("oui", line, COL_ENVENTE);
  }

}
