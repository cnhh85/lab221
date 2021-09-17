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
import Model.Student;
import Model.Vaccine;

public class Manager extends ArrayList<Injection> {
  private final String FILENAME = "injection.dat";

  private Scanner scanner = null;

  private FileWriter fileWriter = null;
  private BufferedWriter bufferedWriter = null;
  private PrintWriter printWriter = null;

  private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  private ArrayList<Student> students = new ArrayList<Student>();
  private ArrayList<Vaccine> vaccines = new ArrayList<Vaccine>();

  private final String studentFILE = "student.txt";
  private final String vaccineFILE = "vaccine.txt";

  public Manager() {
    getAll();
    getAllStudent();
    getVaccine();
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

  private void getAll() {
    boolean readMode = true;
    open(readMode);

    while (scanner.hasNextLine()) {
      String data = scanner.nextLine();
      Injection injection = create(data);
      this.add(injection);
    }

    close();
  }

  private void getAllStudent() {
    try {
      scanner = new Scanner(Paths.get(studentFILE), "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    while (scanner.hasNextLine()) {
      String data = scanner.nextLine();
      Student student = createStudent(data);
      students.add(student);
    }
  }

  private void getVaccine() {
    try {
      scanner = new Scanner(Paths.get(vaccineFILE), "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    while (scanner.hasNextLine()) {
      String data = scanner.nextLine();
      Vaccine vaccine = createVaccine(data);
      vaccines.add(vaccine);
    }
  }

  private void open(boolean readMode) {
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

  private Injection create(String data) {
    String tokens[] = data.split("\\|");
    return new Injection(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], Utility.handleParseDate(tokens[5]),
        Utility.handleParseDate(tokens[6]));
  }

  private Student createStudent(String data) {
    String tokens[] = data.split("\\;");
    return new Student(tokens[0], tokens[1]);
  }

  private Vaccine createVaccine(String data) {
    String tokens[] = data.split("\\;");
    return new Vaccine(tokens[0], tokens[1]);
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

  private void close() {
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

}
