import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.io.FileWriter;
import java.io.IOException;

public class Manager extends ArrayList<Food> {
  private final String FILENAME = "food.dat";

  public Manager() {

  }

  public void addFood(Food food) {
    this.add(food);
  }

  public Food searchByID(int id) {
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
    this.forEach(food -> {
      System.out.println(food.toString());
    });
  }

  // public void writeToFile() {
  // try {
  // FileWriter writer = new FileWriter(FILENAME);
  // this.forEach(food -> {writer.write("Food [ID=" + food.getID() + ", name=" +
  // food.getName() + ", Weight=" + food.getWeight()
  // + ", type=" + food.getType() + ", place=" + food.getPlace() + ",
  // expiredDate=" + food.getExpiredDate() + "]");}) ;
  // myWriter.close();
  // System.out.println("Successfully wrote to the file.");
  // } catch (IOException e) {
  // System.out.println("An error occurred.");
  // e.printStackTrace();
  // }
  // }
}
