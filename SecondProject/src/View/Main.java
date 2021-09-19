package View;

import java.util.Date;
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
    InjectionController controller = new InjectionController();
    StudentController studentController = new StudentController();
    VaccineController vaccineController = new VaccineController();

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
          printInjection(controller);
          break;
        case 2:
          addInjection(controller, studentController, vaccineController);
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

  private static void printInjection(InjectionController controller) {
    controller.printInjectionList();
  }

  private static void addInjection(InjectionController controller, StudentController studentController,
      VaccineController vaccineController) {

    Scanner sc = new Scanner(System.in);
    String continueConfirmation = null;
    do {
      String injectionID = null, studentID = null, vaccineID = null, firstPlace = null, firstDate = null;
      Date date = null;

      System.out.println("ADD AN INJECTION\n");

      do {
        System.out.println("Enter injection ID: ");
        injectionID = sc.nextLine();
        if (controller.get(injectionID) != null) {
          System.out.println("ID already exists, please try a different!");
        }
      } while (Utility.isEmpty(injectionID) || controller.get(injectionID) != null);

      do {
        studentController.printStudentList();
        System.out.println("Enter student ID: ");
        studentID = sc.nextLine();
        if (studentController.get(studentID) == null) {
          System.out.println("This student does not exist, please try a different!");
        }
      } while (Utility.isEmpty(studentID) || studentController.get(studentID) == null);

      do {
        vaccineController.printVaccineList();
        System.out.println("Enter vaccine ID: ");
        vaccineID = sc.nextLine();
        if (vaccineController.get(vaccineID) == null) {
          System.out.println("This vaccine does not exist, please try a different!");
        }
      } while (Utility.isEmpty(vaccineID) || vaccineController.get(vaccineID) == null);

      do {
        System.out.print("Enter first place: ");
        firstPlace = sc.nextLine();
      } while (Utility.isEmpty(firstPlace));

      do {
        System.out.print("Enter first date (dd/mm/yyyy): ");
        firstDate = sc.nextLine();
        date = Utility.handleParseDate(firstDate);
        if (date == null) {
          System.out.println("Invalid date format or date not exist");
        }
      } while (date == null);


      
      System.out.println("Successfully added injection " + injectionID);
      do {
        System.out.print("Do you want to add another injection (Y/N): ");
        continueConfirmation = sc.nextLine();
        if (!continueConfirmation.matches("[YyNn]")) {
          continueConfirmation = null;
        }
      } while (continueConfirmation == null);
    } while (continueConfirmation.toUpperCase().equals("Y"));
  }

}