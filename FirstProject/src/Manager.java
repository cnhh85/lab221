package src;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Manager extends ArrayList<Food> {
  private final String FILENAME = "food.dat";

  private FileWriter fileWriter = null;
  private BufferedWriter bufferedWriter = null;
  private PrintWriter printWriter = null;

  public Manager() {

  }

  public void addFood(Food food) {
    this.add(food);
  }

  public Food searchByID(String id) {
    for (Food food : this) {
      if (food.getID() == id)
        return food;
    }
    return null;
  }

  public void searchByName(String name) {
    ArrayList<Food> searchedList = new ArrayList<Food>();
    for (Food food : this) {
      if (food.getName().equals(name.toUpperCase()))
        searchedList.add(food);
    }
    if (searchedList.isEmpty()) {
      System.out.println("No food found!");
    } else {
      searchedList.forEach(food -> {
        System.out.println(food.toString());
      });
    }
  }

  private void sortList() {
    Comparator<Food> comparator = (c1, c2) -> {
      return c1.getExpiredDate().compareTo(c2.getExpiredDate());
    };
    Collections.sort(this, comparator.reversed());
  }

  public void printDescendingList() {
    sortList();
    System.out.println("DESCENDING LIST: ");
    this.forEach(food -> {
      System.out.println(food.toString());
    });
    System.out.println("=================================");
  }

  public void saveToFile() {
    try {
      fileWriter = new FileWriter(FILENAME, true);
      bufferedWriter = new BufferedWriter(fileWriter);
      printWriter = new PrintWriter(bufferedWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.forEach(food -> {
      printWriter.println(food.toString());
    });
  }
}
