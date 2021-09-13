// Cao Nguyen Hoang Hiep . SE160050

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    View();
  }

  private static void View() {
    Scanner scanner = new Scanner(System.in);
    Manager manager = new Manager();
    int choice = 0;
    System.out.println("Welcome to Food Management - @ 2021 by SE160050 - Cao Nguyen Hoang Hiep");
    do {
      PrintInfo();
      try {
        choice = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid input, please try again!");
        System.out.println();
      }
      switch (choice) {
        case 1:
          manager.addFood();
          System.out.println();
          break;
        case 2:
          manager.searchFood();
          System.out.println();
          break;
        case 3:
          manager.removeFood();
          System.out.println();
          break;
        case 4:
          manager.printDescendingList();
          System.out.println();
          break;
        case 5:
          System.out.println("Saving!");
          manager.saveToFile();
          System.out.println("Saved to food.dat");
          break;
        case 0:
          break;
        default:
          System.out.println("Invalid input, please try again!");
          System.out.println();
      }
    } while (choice != 5);
  }

  private static void PrintInfo() {
    System.out.println("Select the options below:");
    System.out.println("1. Add a new food");
    System.out.println("2. Search a food by name");
    System.out.println("3. Remove the food by ID");
    System.out.println("4. Print the food list in the descending order of expired date");
    System.out.println("5. Save to file and exit");
    System.out.print("Your choice: ");
  }
}