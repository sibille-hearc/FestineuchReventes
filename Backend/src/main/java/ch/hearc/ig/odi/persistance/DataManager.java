package ch.hearc.ig.odi.persistance;

import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.*;

public class DataManager {

  private static final char SEPARATOR = ';';
  private static final String SOURCE = "BilletsVendus.csv";

  public static void updateCSV(String  replace, int row, int col) throws IOException {
      CSVReader reader = new CSVReader(new FileReader(SOURCE),SEPARATOR);
      List<String[]> csvBody = reader.readAll();
      csvBody.get(row)[col]=replace;
      reader.close();
      CSVWriter writer = new CSVWriter(new FileWriter(SOURCE),SEPARATOR,' ');
      writer.writeAll(csvBody);
      writer.flush();
      writer.close();
  }

  public static void addTicketCSV(String numero, String prenom, String nom) throws Exception {
      CSVWriter writer = new CSVWriter(new FileWriter(SOURCE), SEPARATOR);
      String[] record = ("1001," + numero + "," + prenom + "," + nom
          + ",15.02.1994,nicolas.sibille@he-arc.ch,Male,90,111.38.53.10,08.04.2019").split(",");
      writer.writeNext(record);
      writer.flush();
      writer.close();
  }

  public static void getNumberTickets() throws Exception {
    CSVReader reader = new CSVReader(new FileReader(SOURCE),SEPARATOR);
    List<String[]> csvBody = reader.readAll();
  }

}
