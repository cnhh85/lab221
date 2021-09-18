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

public class InjectionController extends ArrayList<Injection> implements FileConnection<Injection> {
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

  public InjectionController() {
    getAll();
    getAllStudent();
    getAllVaccine();
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

  private void getAllVaccine() {
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

  public Injection create(String data) {
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
    System.out.println("INJECTION LIST:");
    System.out
        .println("===================================================================================================");
    System.out.format("|%13s|%12s|%12s|%15s|%12s|%15s|%12s|\n", "InjectionID", "StudentID", "VaccineID", "FirstPlace",
        "FirstDate", "SecondPlace", "SecondDate");
    System.out
        .println("---------------------------------------------------------------------------------------------------");
    this.forEach(injection -> printInjection(injection));
    System.out
        .println("===================================================================================================");

  }

  private void printStudent(Student student) {
    System.out.format("|%12s|%30s|\n", student.getStudentID(), student.getName());
  }

  public void printStudentList() {
    getAllStudent();

    System.out.println("STUDENT LIST:");
    System.out.println("===================================================");
    System.out.format("|%12s|%30s|\n", "StudentID", "StudentName");
    System.out.println("---------------------------------------------------");
    students.forEach(student -> printStudent(student));
    System.out.println("===================================================");

  }

  private void printVaccine(Vaccine vaccine) {
    System.out.format("|%12s|%15s|\n", vaccine.getVaccineID(), vaccine.getName());
  }

  public void printVaccineList() {
    getAllVaccine();

    System.out.println("VACCINE LIST:");
    System.out.println("===================================================");
    System.out.format("|%12s|%30s|\n", "VaccineID", "VaccineName");
    System.out.println("---------------------------------------------------");
    vaccines.forEach(vaccine -> printVaccine(vaccine));
    System.out.println("===================================================");
  }

  public void addInjection() {
    String id = null, studentID = null, vaccineID = null, firstPlace = null, firstDate = null;
    Scanner sc = new Scanner(System.in);

    System.out.println("ADD AN INJECTION\n");

    do {
      System.out.println("Enter injection ID: ");
      id = sc.nextLine();
      if (searchByID(id) != null) {
        System.out.println("ID already exists, please try a different!");
      }
    } while (Utility.isEmpty(id) || searchByID(id) != null);

    do {

    } while (true);

  }

  private Injection searchByID(String id) {
    for (Injection injection : this) {
      if (injection.getInjectionID().equals(id)) {
        return injection;
      }
    }
    return null;
  }
}
