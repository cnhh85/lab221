// Cao Nguyen Hoang Hiep . SE160050

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.text.ParseException;
import java.util.Date;

public class Manager extends ArrayList<Food> {
  private final String FILENAME = "food.dat";
  private Scanner scanner = new Scanner(System.in);

  private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  public Manager() {

  }

  public void addFood() {
    String id, name, type, continueConfirmation, expiredDate, place = null;
    int weight, placeChoice = -1;
    do {
      do {
        System.out.print("Enter ID: ");
        id = scanner.nextLine();
        if (searchByID(id) != null) {
          System.out.println("ID already exists, please enter a different!");
        }
      } while (isEmpty(id) || searchByID(id) != null);
      do {
        System.out.print("Enter name: ");
        name = scanner.nextLine();
      } while (isEmpty(name));

      weight = 0;
      do {
        try {
          System.out.print("Enter weight(gram): ");
          weight = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
          System.out.println("Invalid weight!");
          continue;
        }
        if (!(weight > 0 && weight < 10000)) {
          System.out.println("Invalid weight! The weight must be greater than 0 and under 10000");
        }
      } while (!(weight > 0 && weight < 10000));

      do {
        System.out.print("Enter type: ");
        type = scanner.nextLine();
      } while (isEmpty(type));
      do {
        try {
          System.out.print("Choose place (1 for Cooler, 2 for Freezer): ");
          placeChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
          System.out.println("Invalid choice!");
        }
        if (placeChoice != 1 && placeChoice != 2) {
          System.out.println("Invalid choice!");
        }
      } while (placeChoice != 1 && placeChoice != 2);
      switch (placeChoice) {
        case 1:
          place = "Cooler";
          break;
        case 2:
          place = "Freezer";
          break;
      }

      Date date = null;
      do {
        System.out.print("Enter expired date (dd/mm/yyyy): ");
        expiredDate = scanner.nextLine();
        if (!expiredDate.matches("^\\d{1,2}/\\d{1,2}/\\d{4}$")) {
          System.out.println("Invalid date format or date not exist");
          continue;
        }
        date = validateExpiredDate(expiredDate);
      } while (date == null);

      Food food = new Food(id, name, weight, type, place, date);
      this.add(food);
      System.out.println("Successfully added " + id + "|" + name);
      do {
        System.out.print("Do you want to add another food (Y/N): ");
        continueConfirmation = scanner.nextLine();
        if (!continueConfirmation.matches("[YyNn]")) {
          continueConfirmation = null;
        }
      } while (continueConfirmation == null);
    } while (continueConfirmation.toUpperCase().equals("Y"));
  }

  public void searchFood() {
    String name, continueConfirmation;
    do {
      do {
        System.out.print("Enter food name: ");
        name = scanner.nextLine();
        if (name.equals("")) {
          System.out.println("Name cannot be empty");
        }
      } while (name.equals(""));
      searchByName(name);
      do {
        System.out.print("Do you want to search another food (Y/N): ");
        continueConfirmation = scanner.nextLine();
        if (!continueConfirmation.matches("[YyNn]")) {
          continueConfirmation = null;
        }
      } while (continueConfirmation == null);
    } while (continueConfirmation.toUpperCase().equals("Y"));
  }

  public void removeFood() {
    String id, continueConfirmation, confirmation = null;
    do {
      do {
        System.out.print("Enter ID: ");
        id = scanner.nextLine();
        if (id.equals("")) {
          System.out.println("ID cannot be empty");
        } else if (searchByID(id) == null) {
          System.out.println("Food not found!");
        }
      } while (id.equals("") || searchByID(id) == null);
      do {
        System.out.print("Do you want to delete this food (Y/N): ");
        confirmation = scanner.nextLine();
        if (!confirmation.matches("[YyNn]")) {
          confirmation = null;
        }
      } while (confirmation == null);
      if (confirmation.toUpperCase().equals("Y")) {
        remove(searchByID(id));
        System.out.println("Remove successfully!");
      } else {
        System.out.println("Remove failed!");
      }
      do {
        System.out.println("Do you want to remove another food (Y/N): ");
        continueConfirmation = scanner.nextLine();
        if (!continueConfirmation.matches("[YyNn]")) {
          continueConfirmation = null;
        }
      } while (continueConfirmation == null);
    } while (continueConfirmation.toUpperCase().equals("Y"));
  }

  public void printDescendingList() {
    sortList();
    System.out.println("LIST OF FOODS: ");
    System.out.println("=====================================================================================");
    System.out.format("|%8s|%20s|%13s|%15s|%10s|%12s|\n", "ID", "Name", "Weight", "Type", "Place", "Expired By");
    this.forEach(food -> {
      printItem(food);
    });
    System.out.println("=====================================================================================");
  }

  public void saveToFile() {
    try {
      FileWriter writer = new FileWriter(FILENAME);
      for (Food food : this) {
        writer.write(food.getID() + "|" + food.getName() + "|" + food.getWeight() + "(g)|" + food.getType() + "|"
            + food.getPlace() + "|" + dateFormat.format((food.getExpiredDate())) + "\n");
      }
      writer.close();
    } catch (IOException e) {
      System.out.println("Something went wrong");
    }
  }

  private Food searchByID(String id) {
    for (Food food : this) {
      if (food.getID().equals(id)) {
        return food;
      }
    }
    return null;
  }

  private void printItem(Food food) {
    System.out.format("|%8s|%20s|%10d(g)|%15s|%10s|%12s|\n", food.getID(), food.getName(), food.getWeight(),
        food.getType(), food.getPlace(), dateFormat.format(food.getExpiredDate()));
  }

  private void searchByName(String name) {
    ArrayList<Food> searchedList = new ArrayList<Food>();
    for (Food food : this) {
      if (food.getName().toUpperCase().contains(name.toUpperCase()))
        searchedList.add(food);
    }
    if (searchedList.isEmpty()) {
      System.out.println("This food does not exist!");
    } else {
      searchedList.forEach(food -> printItem(food));
    }
  }

  private void sortList() {
    Comparator<Food> comparator = (c1, c2) -> {
      return c1.getExpiredDate().compareTo(c2.getExpiredDate());
    };
    Collections.sort(this, comparator.reversed());
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

  private static boolean isEmpty(String string) {
    if (string.equals("")) {
      System.out.println("String cannot be empty");
      return true;
    } else {
      return false;
    }
  }
}