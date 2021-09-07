import java.util.ArrayList;
import java.util.Scanner;

public class Manager extends ArrayList<Food> {
  private final String FILENAME = "food.dat";

  public Manager() {

  }

  public void addFood(Food food) {
    this.add(food);
  }

  public Food idSearch(int id) {
    for (Food food : this) {
      if (food.getID() == id)
        return food;
    }
    return null;
  }
}
