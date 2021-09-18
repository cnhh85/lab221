package Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Vaccine;

public class VaccineController extends ArrayList<Vaccine> implements FileConnection<Vaccine> {
  private final String FILENAME = "vaccine.txt";
  private Scanner scanner = null;

  private FileWriter fileWriter = null;
  private BufferedWriter bufferedWriter = null;
  private PrintWriter printWriter = null;

  public VaccineController() {
    getAll();
  }

  public Vaccine get(String vaccineID) {

    getAll();

    for (Vaccine vaccine : this) {
      if (vaccine.getVaccineID().equals(vaccineID)) {
        return vaccine;
      }
    }

    return null;
  }

  private void getAll() {
    boolean readMode = true;
    open(readMode);

    while (scanner.hasNextLine()) {
      String data = scanner.nextLine();
      Vaccine vaccine = create(data);
      this.add(vaccine);
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
  public Vaccine create(String data) {
    String tokens[] = data.split("\\;");
    return new Vaccine(tokens[0], tokens[1]);
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

  private void printVaccine(Vaccine vaccine) {
    System.out.format("|%12s|%15s|\n", vaccine.getVaccineID(), vaccine.getName());
  }

  public void printVaccineList() {
    System.out.println("VACCINE LIST:");
    System.out.println("===================================================");
    System.out.format("|%12s|%30s|\n", "VaccineID", "VaccineName");
    System.out.println("---------------------------------------------------");
    this.forEach(vaccine -> printVaccine(vaccine));
    System.out.println("===================================================");
  }
}
