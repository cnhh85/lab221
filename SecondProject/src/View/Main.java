package View;

import java.util.Scanner;

import Controller.Manager;

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
    Manager manager = new Manager();
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
          manager.printInjectionList();
          break;
        case 2:
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
}