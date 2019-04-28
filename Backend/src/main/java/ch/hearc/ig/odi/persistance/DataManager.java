package ch.hearc.ig.odi.persistance;

import ch.hearc.ig.odi.business.Billet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.*;

public class DataManager {

  private final char SEPARATOR = ';';
  private final String SOURCE = "D:\\Documents\\Cours_HE-ARC\\6semestre\\ArchitectureSI\\FestineuchReventes\\FestineuchReventes\\Backend\\BilletsVendus.csv";
  private final int COL_ID = 0;
  private final int COL_UID = 1;
  private final int COL_PRENOM = 2;
  private final int COL_NOM = 3;
  private final int COL_DATENAISSANCE = 4;
  private final int COL_EMAIL = 5;
  private final int COL_GENRE = 6;
  private final int COL_PRIX = 7;
  private final int COL_ADRESSEIP = 8;
  private final int COL_DATEACHAT = 9;
  private final int COL_PRIXRACHAT = 10;
  private final int COL_DATERACHAT = 11;
  private final int COL_ENVENTE = 12;

  private void updateCSV(String  replace, int row, int col) throws IOException {
      CSVReader reader = new CSVReader(new FileReader(SOURCE),SEPARATOR);
      List<String[]> csvBody = reader.readAll();
      csvBody.get(row)[col]=replace;
      reader.close();
      CSVWriter writer = new CSVWriter(new FileWriter(SOURCE),SEPARATOR,CSVWriter.NO_QUOTE_CHARACTER);
      writer.writeAll(csvBody);
      writer.flush();
      writer.close();
  }

  private Billet readLine(int LineNumber) throws IOException {
    CSVReader reader = new CSVReader(new FileReader(SOURCE),SEPARATOR);
    String[] tabLine = reader.readAll().get(LineNumber);
    reader.close();
    Billet billet = new Billet();
    billet.setId(tabLine[COL_ID]);
    billet.setUid(tabLine[COL_UID]);
    billet.setNom(tabLine[COL_NOM]);
    billet.setPrenom(tabLine[COL_PRENOM]);
    billet.setEmail(tabLine[COL_EMAIL]);
    billet.setDateNaissance(tabLine[COL_DATENAISSANCE]);
    billet.setGender(tabLine[COL_GENRE]);
    billet.setDateAchat(tabLine[COL_DATEACHAT]);
    billet.setAdresseIP(tabLine[COL_ADRESSEIP]);
    billet.setDateRachat(tabLine[COL_DATERACHAT]);
    billet.setPrixRachat(tabLine[COL_PRIXRACHAT]);
    billet.setEnVente(tabLine[COL_ENVENTE]);
    return billet;
  }

  private int searchLinebyTicketNumber(String ticketNumber) throws Exception {
    CSVReader reader = new CSVReader(new FileReader(SOURCE), SEPARATOR);
    List<String[]> csvBody = reader.readAll();
    for (int i = 0; i < csvBody.size(); i ++) {
      if(csvBody.get(i)[COL_UID].equals(ticketNumber) || csvBody.get(i)[COL_UID].equals(" "+ticketNumber)) {
        return i;
      }
    }
    return -1;
  }

  private List<String[]> searchTicketsInSale() throws Exception {
    CSVReader reader = new CSVReader(new FileReader(SOURCE), SEPARATOR);
    List<String[]> ticketsInSale = new ArrayList<>();
    List<String[]> csvBody = reader.readAll();
    for (int i = 0; i < csvBody.size(); i ++) {
      if(csvBody.get(i)[COL_ENVENTE].equals("oui") || csvBody.get(i)[COL_ENVENTE].equals(" oui")) {
        ticketsInSale.add(csvBody.get(i));
      }
    }
    return ticketsInSale;
  }

  public void changeTicketOwner(String oldUID, String newPrenom, String newNom, String newEmail, String newDateNaissance, String newGenre) throws Exception {
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
    updateCSV("non", line, COL_ENVENTE);
  }

  public ArrayList<Billet> getTicketsInSale() throws Exception {
    ArrayList<Billet> billets = new ArrayList<>();
    List<String[]> tickets = searchTicketsInSale();
    for (String[] ticket : tickets) {
      Billet billet = new Billet();
      billet.setUid(ticket[COL_UID]);
      billet.setNom(ticket[COL_NOM]);
      billet.setPrenom(ticket[COL_PRENOM]);
      billet.setEmail(ticket[COL_EMAIL]);
      billet.setDateNaissance(ticket[COL_DATENAISSANCE]);
      billet.setGender(ticket[COL_GENRE]);
      billets.add(billet);
    }
    return billets;
  }

  public void listTicketAsInSale(String oldUID, String prix) throws Exception {
    int line = searchLinebyTicketNumber(oldUID);
    updateCSV(prix, line, COL_PRIXRACHAT);
    updateCSV("oui", line, COL_ENVENTE);
  }

  public Billet getTicketInfo(String UID) throws Exception {
    int line = searchLinebyTicketNumber(UID);
    return readLine(line);
  }

}
