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

    getAll();

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

  public void update(String injectionID, Injection injection) {

    getAll();

    boolean readMode = false;
    open(readMode);

    StringBuffer buffer = new StringBuffer();

    while (scanner.hasNextLine()) {
      buffer.append(scanner.nextLine() + System.lineSeparator());
    }
    Injection old = get(injectionID);

    String oldLine = old.getInjectionID() + "|" + old.getStudentID() + "|" + old.getVaccineID() + "|"
        + old.getFirstPlace() + "|" + old.getSecondPlace() + "|" + dateFormat.format(old.getFirstDate()) + "|"
        + dateFormat.format(old.getSecondDate());

    String newLine = injection.getInjectionID() + "|" + injection.getStudentID() + "|" + injection.getVaccineID() + "|"
        + injection.getFirstPlace() + "|" + injection.getSecondPlace() + "|"
        + dateFormat.format(injection.getFirstDate()) + "|" + dateFormat.format(injection.getSecondDate());

    String fileContents = buffer.toString();
    fileContents = fileContents.replaceAll(oldLine, newLine);

    try {
      fileWriter.append(fileContents);
      fileWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public Injection create(String data) {
    String tokens[] = data.split("\\|");
    return new Injection(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], Utility.handleParseDate(tokens[5]),
        Utility.handleParseDate(tokens[6]));
  }

  public void remove(String injectionID) {
    getAll();

    boolean readMode = false;
    open(readMode);

    StringBuffer buffer = new StringBuffer();

    while (scanner.hasNextLine()) {
      buffer.append(scanner.nextLine() + System.lineSeparator());
    }
    Injection old = get(injectionID);

    String oldLine = old.getInjectionID() + "|" + old.getStudentID() + "|" + old.getVaccineID() + "|"
        + old.getFirstPlace() + "|" + old.getSecondPlace() + "|" + dateFormat.format(old.getFirstDate()) + "|"
        + dateFormat.format(old.getSecondDate());

    String fileContents = buffer.toString();
    fileContents = fileContents.replaceAll(oldLine, "");

    try {
      fileWriter.append(fileContents);
      fileWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void save(Injection injection) {
    getAll();

    boolean readMode = false;
    open(readMode);

    printWriter.println(injection.getInjectionID() + "|" + injection.getStudentID() + "|" + injection.getVaccineID()
        + "|" + injection.getFirstPlace() + "|" + injection.getSecondPlace() + "|"
        + dateFormat.format(injection.getFirstDate()) + "|" + dateFormat.format(injection.getSecondDate()));

    close();
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

  private void printInjection(Injection injection) {
    System.out.format("|%13s|%12s|%12s|%15s|%12s|%15s|%12s|\n", injection.getInjectionID(), injection.getStudentID(),
        injection.getVaccineID(), injection.getFirstPlace(), dateFormat.format(injection.getFirstDate()),
        injection.getSecondPlace() == null ? "none" : injection.getSecondPlace(),
        injection.getSecondDate() == null ? "none" : dateFormat.format(injection.getSecondDate()));
  }

  public void printInjectionList() {
    getAll();
    System.out.println("\n\nINJECTION LIST:");
    System.out
        .println("===================================================================================================");
    System.out.format("|%13s|%12s|%12s|%15s|%12s|%15s|%12s|\n", "InjectionID", "StudentID", "VaccineID", "FirstPlace",
        "FirstDate", "SecondPlace", "SecondDate");
    System.out
        .println("---------------------------------------------------------------------------------------------------");
    this.forEach(injection -> printInjection(injection));
    System.out
        .println("===================================================================================================\n\n");

  }

}
