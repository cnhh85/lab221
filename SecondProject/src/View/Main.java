package View;

import java.util.Date;
import java.util.Scanner;

import Controller.InjectionController;
import Controller.StudentController;
import Controller.Utility;
import Controller.VaccineController;
import Model.Injection;

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
          updateInjection(controller, studentController, vaccineController);
          break;
        case 4:
          deleteInjection(controller, studentController, vaccineController);
          break;
        case 5:
          searchByStudentID(controller, studentController, vaccineController);
          break;
        case 6:
          controller.saveToFile();
          break;
        case 7:
          System.out.println("EXITING!");
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
      Injection injection = null;
      String injectionID = null, studentID = null, vaccineID = null, firstPlace = null, firstDateInput = null,
          secondPlace = null, secondDateInput = null, secondConfirmation = null;
      Date firstDate = null;
      Date secondDate = null;

      System.out.println("ADD AN INJECTION\n");

      do {
        System.out.println("Enter injection ID: ");
        injectionID = sc.nextLine();
        if (controller.get(injectionID) != null) {
          System.out.println("ID already exists, please try a different!");
        }
      } while (Utility.isEmpty(injectionID) || controller.get(injectionID) != null);

      studentController.printStudentList();

      do {
        System.out.println("Enter student ID: ");
        studentID = sc.nextLine();
        if (studentController.get(studentID) == null) {
          System.out.println("This student does not exist, please try a different!");
        }
      } while (Utility.isEmpty(studentID) || studentController.get(studentID) == null);

      vaccineController.printVaccineList();

      do {
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
        firstDateInput = sc.nextLine();
        if (!firstDateInput.matches("^\\d{1,2}/\\d{1,2}/\\d{4}$")) {
          System.out.println("Invalid date format");
          continue;
        }
        firstDate = Utility.handleParseDate(firstDateInput);
        if (firstDate == null) {
          System.out.println("Date not exist");
        }
      } while (firstDate == null);

      do {
        System.out.print("Do you want to add second information (Y/N): ");
        secondConfirmation = sc.nextLine();
        if (!secondConfirmation.matches("[YyNn]")) {
          secondConfirmation = null;
        }
      } while (secondConfirmation == null);

      if (!secondConfirmation.toUpperCase().equals("Y")) {
        injection = new Injection(injectionID, studentID, vaccineID, firstPlace, firstDate);
      } else {
        do {
          System.out.print("Enter second place: ");
          secondPlace = sc.nextLine();
        } while (Utility.isEmpty(secondPlace));

        do {
          System.out.print("Enter second date (dd/mm/yyyy): ");
          secondDateInput = sc.nextLine();
          if (!secondDateInput.matches("^\\d{1,2}/\\d{1,2}/\\d{4}$")) {
            System.out.println("Invalid date format");
            continue;
          }
          secondDate = Utility.handleParseDate(secondDateInput);
          if (secondDate == null) {
            System.out.println("Date not exist");
          }
        } while (secondDate == null || !Utility.isValidSecondDate(firstDate, secondDate));

        injection = new Injection(injectionID, studentID, vaccineID, firstPlace, firstDate, secondPlace, secondDate);

      }

      controller.add(injection);
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

  private static void updateInjection(InjectionController controller, StudentController studentController,
      VaccineController vaccineController) {

    Scanner sc = new Scanner(System.in);

    Injection injection = null;
    Date secondDate = null;

    String injectionID = null, keepUpdate = null, secondPlace, secondDateInput;
    int idx;

    do {
      System.out.println("Enter injection ID: ");
      injectionID = sc.nextLine();
      injection = controller.get(injectionID);
      if (injection == null) {
        System.out.println("This injection does not exist, please try a different!");
      }
    } while (Utility.isEmpty(injectionID) || injection == null);

    idx = controller.indexOf(injection);

    if (injection.getSecondPlace() != null) {
      do {
        System.out.print("This student has already completed 2 injections, do you want to continue update (Y/N): ");
        keepUpdate = sc.nextLine();
        if (!keepUpdate.matches("[YyNn]")) {
          keepUpdate = null;
        }
      } while (keepUpdate == null);

      if (keepUpdate.toUpperCase().equals("Y")) {
        controller.printInjection(injection);

        do {
          System.out.print("Enter second place: ");
          secondPlace = sc.nextLine();
        } while (Utility.isEmpty(secondPlace));

        injection.setSecondPlace(secondPlace);

        do {
          System.out.print("Enter second date (dd/mm/yyyy): ");
          secondDateInput = sc.nextLine();
          if (!secondDateInput.matches("^\\d{1,2}/\\d{1,2}/\\d{4}$")) {
            System.out.println("Invalid date format");
            continue;
          }
          secondDate = Utility.handleParseDate(secondDateInput);
          if (secondDate == null) {
            System.out.println("Date not exist");
          }
        } while (secondDate == null || !Utility.isValidSecondDate(injection.getFirstDate(), secondDate));

        injection.setSecondDate(secondDate);

        controller.set(idx, injection);

      }

    } else {
      controller.printInjection(injection);

      do {
        System.out.print("Enter second place: ");
        secondPlace = sc.nextLine();
      } while (Utility.isEmpty(secondPlace));

      injection.setSecondPlace(secondPlace);

      do {
        System.out.print("Enter second date (dd/mm/yyyy): ");
        secondDateInput = sc.nextLine();
        if (!secondDateInput.matches("^\\d{1,2}/\\d{1,2}/\\d{4}$")) {
          System.out.println("Invalid date format");
          continue;
        }
        secondDate = Utility.handleParseDate(secondDateInput);
        if (secondDate == null) {
          System.out.println("Date not exist");
        }
      } while (secondDate == null || !Utility.isValidSecondDate(injection.getFirstDate(), secondDate));

      injection.setSecondDate(secondDate);

      controller.set(idx, injection);

    }

  }

  public static void deleteInjection(InjectionController controller, StudentController studentController,
      VaccineController vaccineController) {
    Scanner sc = new Scanner(System.in);

    Injection injection = null;

    String injectionID = null, confirmation = null;

    do {
      System.out.println("Enter injection ID: ");
      injectionID = sc.nextLine();
      injection = controller.get(injectionID);
      if (injection == null) {
        System.out.println("This injection does not exist, please try a different!");
      }
    } while (Utility.isEmpty(injectionID) || injection == null);

    System.out.println();
    controller.printInjection(injection);
    System.out.println();

    do {
      System.out.print("Do you want to delete this injection (Y/N): ");
      confirmation = sc.nextLine();
      if (!confirmation.matches("[YyNn]")) {
        confirmation = null;
      }
    } while (confirmation == null);

    if (confirmation.toUpperCase().equals("Y")) {
      controller.remove(injection);
      System.out.println("Remove successfully");
    } else {
      System.out.println("Remove fail");
    }

  }

  private static void searchByStudentID(InjectionController controller, StudentController studentController,
      VaccineController vaccineController) {
    Scanner sc = new Scanner(System.in);

    String studentID;

    studentController.printStudentList();

    do {
      System.out.println("Enter student ID: ");
      studentID = sc.nextLine();
      if (studentController.get(studentID) == null) {
        System.out.println("This student does not exist, please try a different!");
      }
    } while (Utility.isEmpty(studentID) || studentController.get(studentID) == null);

  }

}