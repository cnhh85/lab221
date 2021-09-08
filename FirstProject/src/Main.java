import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    View();
  }

  private static void View() {
    Scanner scanner = new Scanner(System.in);
    Manager manager = new Manager();

    int choice;
    String continueConfirmation = null;
    System.out.println("Welcome to Food Management - @ 2021 by SE160050 - Cao Nguyen Hoang Hiep");
    PrintInfo();
    do {
      choice = Integer.parseInt(scanner.nextLine());
      switch (choice) {
        case 1:
          do {
            addFunction();
            do {
              System.out.println("Do you want to add another food (Y/N): ");
              continueConfirmation = scanner.nextLine();
              if (!continueConfirmation.matches("YyNn")) {
                continueConfirmation = null;
              }
            } while (continueConfirmation == null);
          } while (continueConfirmation.toUpperCase().equals("Y"));
          break;
        case 2:
          do {
            searchFunction();
            do {
              System.out.println("Do you want to remove another food (Y/N): ");
              continueConfirmation = scanner.nextLine();
              if (!continueConfirmation.matches("YyNn")) {
                continueConfirmation = null;
              }
            } while (continueConfirmation == null);
          } while (continueConfirmation.toUpperCase().equals("Y"));
          break;
        case 3:
          do {
            removeFunction();
            do {
              System.out.println("Do you want to remove another food (Y/N): ");
              continueConfirmation = scanner.nextLine();
              if (!continueConfirmation.matches("YyNn")) {
                continueConfirmation = null;
              }
            } while (continueConfirmation == null);
          } while (continueConfirmation.toUpperCase().equals("Y"));
          break;
        case 4:
          manager.printDescendingList();
          break;
        case 5:
          System.out.println("Saving!");
          manager.saveToFile();
          System.out.println("Saved to food.dat");
          break;
        default:
          System.out.println("Invalid input, please try again!");
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

  private static void addFunction() {
    Scanner scanner = new Scanner(System.in);
    Manager manager = new Manager();
    String id, name, type, place, expiredDate;
    int weight;
    do {
      System.out.print("Enter ID: ");
      id = scanner.nextLine();
      if (id == null) {
        System.out.println("ID cannot be empty");
      } else if (manager.searchByID(id) != null) {
        System.out.println("ID already exists, please enter a different!");
      }
    } while (id == null || manager.searchByID(id) != null);
    do {
      System.out.print("Enter name: ");
      name = scanner.nextLine();
      if (name == null) {
        System.out.println("Name cannot be empty");
      }
    } while (name == null);
    weight = 0;
    do {
      try {
        System.out.print("Enter weight(gram): ");
        weight = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid weight!");
      }
    } while (weight <= 0);
    do {
      System.out.print("Enter type: ");
      type = scanner.nextLine();
      if (type == null) {
        System.out.println("Type cannot be empty");
      }
    } while (type == null);
    do {
      System.out.print("Enter place: ");
      place = scanner.nextLine();
      if (place == null) {
        System.out.println("Place cannot be empty");
      }
    } while (place == null);
    do {
      System.out.print("Enter expired date (dd/mm//yyyy): ");
      expiredDate = scanner.nextLine();
    } while (validateExpiredDate(expiredDate) == null);
    manager.addFood(new Food(id, name, weight, type, place, validateExpiredDate(expiredDate)));
    System.out.println("Successfully added " + id + " | " + name);
  }

  private static void searchFunction() {
    String name;
    Scanner scanner = new Scanner(System.in);
    Manager manager = new Manager();
    do {
      System.out.print("Enter food name: ");
      name = scanner.nextLine();
      if (name == null) {
        System.out.println("Name cannot be empty");
      }
    } while (name == null);
    manager.searchByName(name);
  }

  private static void removeFunction() {
    String id, confirmation = null;
    Scanner scanner = new Scanner(System.in);
    Manager manager = new Manager();
    do {
      System.out.print("Enter ID: ");
      id = scanner.nextLine();
      if (id == null) {
        System.out.println("ID cannot be empty");
      } else if (manager.searchByID(id) == null) {
        System.out.println("ID already exists, please enter a different!");
      }
    } while (id == null || manager.searchByID(id) == null);
    do {
      System.out.print("Do you want to delete this food (Y/N): ");
      confirmation = scanner.nextLine();
      if (!confirmation.matches("YyNn")) {
        confirmation = null;
      }
    } while (confirmation == null);
    if (confirmation.toUpperCase().equals("Y")) {
      manager.remove(manager.searchByID(id));
      System.out.println("Remove successfully!");
    } else {
      System.out.println("Remove failed!");
    }
  }

  private static Date validateExpiredDate(String expiredDate) {
    Date today = new Date();
    if (expiredDate.trim().equals("")) {
      System.out.println("Expired date cannot be empty");
      return null;
    } else {
      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
      format.setLenient(false);
      try {
        Date date = format.parse(expiredDate);
        if (date.after(today)) {
          return date;
        } else {
          System.out.println("Expired food!");
          return null;
        }
      } catch (ParseException e) {
        System.out.println("Invalid date format or date not exist");
        return null;
      }
    }
  }
}