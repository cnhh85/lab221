package Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Injection;

public class InjectionController extends ArrayList<Injection> implements FileConnection<Injection> {
  private final String FILENAME = "injection.dat";

  private Scanner scanner = null;

  private FileWriter fileWriter = null;
  private BufferedWriter bufferedWriter = null;
  private PrintWriter printWriter = null;

  private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  public InjectionController() {
    getAll();
  }

  public Injection get(String injectionID) {

    for (Injection injection : this) {
      if (injection.getInjectionID().equals(injectionID)) {
        return injection;
      }
    }

    return null;
  }

  public void getAll() {
    boolean readMode = true;
    open(readMode);

    while (scanner.hasNextLine()) {
      String data = scanner.nextLine();
      Injection injection = create(data);
      this.add(injection);
    }

    close();
  }

  @Override
  public void open(boolean readMode) {
    if (readMode) {
      try {
        scanner = new Scanner(Paths.get(FILENAME), "UTF-8");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        fileWriter = new FileWriter(FILENAME, true);
        bufferedWriter = new BufferedWriter(fileWriter);
        printWriter = new PrintWriter(bufferedWriter);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void close() {
    if (scanner != null)
      scanner.close();

    if (printWriter != null && bufferedWriter != null && fileWriter != null)
      try {
        printWriter.close();
        bufferedWriter.close();
        fileWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

  @Override
  public Injection create(String data) {
    String tokens[] = data.split("\\|");
    return new Injection(tokens[0], tokens[1], tokens[2], tokens[3], Utility.handleParseDate(tokens[4]),
        tokens[5] == "none" ? null : tokens[5], tokens[6] == "none" ? null : Utility.handleParseDate(tokens[6]));
  }

  public void saveToFile() {

    boolean readMode = false;
    open(readMode);

    for (Injection injection : this) {
      printWriter.println(injection.getInjectionID() + "|" + injection.getStudentID() + "|" + injection.getVaccineID()
          + "|" + injection.getFirstPlace() + "|" + dateFormat.format(injection.getFirstDate()) + "|"
          + injection.getSecondPlace() == null ? "none"
              : injection.getSecondPlace() + "|" + injection.getSecondDate() == null ? "none"
                  : dateFormat.format(injection.getSecondDate()));
    }

    close();
  }

  public void printInjection(Injection injection) {
    System.out.format("|%13s|%12s|%12s|%15s|%12s|%15s|%12s|\n", injection.getInjectionID(), injection.getStudentID(),
        injection.getVaccineID(), injection.getFirstPlace(), dateFormat.format(injection.getFirstDate()),
        injection.getSecondPlace() == null ? "none" : injection.getSecondPlace(),
        injection.getSecondDate() == null ? "none" : dateFormat.format(injection.getSecondDate()));
  }

  public void printInjectionList() {
    if (this.isEmpty()) {
      System.out.println("\n\nThere is no injection\n\n");
    } else {
      System.out.println("\n\nINJECTIONS LIST:");
      System.out.println(
          "===================================================================================================");
      System.out.format("|%13s|%12s|%12s|%15s|%12s|%15s|%12s|\n", "InjectionID", "StudentID", "VaccineID", "FirstPlace",
          "FirstDate", "SecondPlace", "SecondDate");
      System.out.println(
          "---------------------------------------------------------------------------------------------------");
      this.forEach(injection -> printInjection(injection));
      System.out.println(
          "===================================================================================================\n\n");
    }

  }

  public ArrayList<Injection> getInjectionByStudentID(String studentID) {
    if (this.isEmpty()) {
      return null;
    } else {
      ArrayList<Injection> result = new ArrayList<Injection>();
      for (Injection injection : this) {
        if (injection.getStudentID().equals(studentID)) {
          result.add(injection);
        }
      }
      return result;
    }
  }

}
