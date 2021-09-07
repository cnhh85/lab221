import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class Main {
  public static void main(String[] args) {
    View();
  }

  private static void View() {
    Scanner scanner = new Scanner(System.in);
    Manager manager = new Manager();
    String id, name, type, place, expiredDate;
    int weight;

    int choice;
    System.out.println("Welcome to Food Management - @ 2021 by SE160050 - Cao Nguyen Hoang Hiep");
    PrintInfo();
    do {
      choice = Integer.parseInt(scanner.nextLine());
      switch (choice) {
        case 1:
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
          do {
            System.out.print("Enter weight(gram): ");
            weight = Integer.parseInt(scanner.nextLine());
            if (weight <= 0) {
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
          break;
        case 2:
          do {
            System.out.print("Enter food name: ");
            name = scanner.nextLine();
            if (name == null) {
              System.out.println("Name cannot be empty");
            }
          } while (name == null);
          manager.searchByName(name);
          break;
        case 3:
          do {
            System.out.print("Enter ID: ");
            id = scanner.nextLine();
            if (id == null) {
              System.out.println("ID cannot be empty");
            } else if (manager.searchByID(id) == null) {
              System.out.println("ID already exists, please enter a different!");
            }
          } while (id == null || manager.searchByID(id) == null);
          manager.remove(manager.searchByID(id));
          break;
        case 4:
          manager.printDescendingList();
          break;
        case 5:
          System.out.println("Saving!");
          manager.saveToFile();
          System.out.println("Saved to food.dat");
          break;
        case 6:
          break;
        default:
          System.out.println("Invalid input, please try again!");
      }
    } while (choice != 6);
  }

  private static void PrintInfo() {
    System.out.println("Select the options below:");
    System.out.println("1. Add a new food");
    System.out.println("2. Search a food by name");
    System.out.println("3. Remove the food by ID");
    System.out.println("4. Print the food list in the descending order of expired date");
    System.out.println("5. Save all the food to file");
    System.out.println("6. Quit");
    System.out.print("Your choice: ");
  }

  private static Date validateExpiredDate(String expiredDate) {
    if (expiredDate.trim().equals("")) {
      System.out.println("Expired date cannot be empty");
      return null;
    } else {
      SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
      format.setLenient(false);
      try {
        Date date = format.parse(expiredDate);
        if (date.after(new Date())) {
          return date;
        } else {
          System.out.println("Expired date cannot before today");
          return null;
        }
      } catch (ParseException e) {
        System.out.println("Invalid date format or date not exist");
        return null;
      }
    }
  }
}