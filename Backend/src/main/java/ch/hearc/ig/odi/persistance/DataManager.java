package ch.hearc.ig.odi.persistance;

import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.*;

public class DataManager {

  private static final char SEPARATOR = ';';
  private static final String SOURCE = "BilletsVendus.csv";

  public static void updateCSV(String input, String output, String  replace, int row, int col) throws IOException {
    CSVReader reader = new CSVReader(new FileReader(input),SEPARATOR);
    List<String[]> csvBody = reader.readAll();
    csvBody.get(row)[col]=replace;
    reader.close();

    CSVWriter writer = new CSVWriter(new FileWriter(output),SEPARATOR,' ');
    writer.writeAll(csvBody);
    writer.flush();
    writer.close();
  }

  public static void main(String[] args) throws IOException {
    DataManager.updateCSV(SOURCE, SOURCE, "test", 1, 1);
  }

}
