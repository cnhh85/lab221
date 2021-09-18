package View;

import java.util.Scanner;

import Controller.InjectionController;
import Controller.StudentController;
import Controller.Utility;
import Controller.VaccineController;

public class Main {
  public static void main(String[] args) {
    view();
  }

  private static void view() {
    System.out.println("=================================================================");
    System.out.println("COVID-19 VACCINE MANAGEMENT BY @ SE160050 - CAO NGUYEN HOANG HIEP");
    System.out.println("=================================================================" + "\n\n");
    int menuChoice = 0;
    Scanner scanner = new Scanner(System.in);
    do {
      try {
        printMenu();
        menuChoice = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid choice!" + "\n\n");
        continue;
      }
      switch (menuChoice) {
        case 1:
          printInjection();
          break;
        case 2:
          addInjection();
          break;
        case 3:
          break;
        case 4:
          break;
        case 5:
          break;
        case 6:
          break;
        case 7:
          break;
        default:
          System.out.println("Invalid choice!" + "\n\n");
      }
    } while (menuChoice != 7);
  }

  private static void printMenu() {
    System.out.println("1. Show injection information");
    System.out.println("2. Add an injection");
    System.out.println("3. Update injection information");
    System.out.println("4. Delete injection");
    System.out.println("5. Search injection by studentID");
    System.out.println("6. Store data to file");
    System.out.println("7. Exit");
    System.out.print("Your choice: ");
  }

  private static void printInjection() {
    InjectionController controller = new InjectionController();
    controller.printInjectionList();
  }

  private static void addInjection() {
    InjectionController controller = new InjectionController();
    String id = null, studentID = null, vaccineID = null, firstPlace = null, firstDate = null;
    Scanner sc = new Scanner(System.in);

    StudentController studentController = new StudentController();
    VaccineController vaccineController = new VaccineController();

    System.out.println("ADD AN INJECTION\n");

    do {
      System.out.println("Enter injection ID: ");
      id = sc.nextLine();
      if (controller.get(id) != null) {
        System.out.println("ID already exists, please try a different!");
      }
    } while (Utility.isEmpty(id) || controller.get(id) != null);

    do {

    } while (true);

  }
}