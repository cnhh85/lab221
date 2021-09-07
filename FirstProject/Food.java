import java.util.Date;

public class Food {
  public int ID;
  public String name;
  public int Weight;
  public String type;
  public String place;
  public Date expiredDate;

  public Food() {
  }

  public Food(int iD, String name, int weight, String type, String place, Date expiredDate) {
    ID = iD;
    this.name = name;
    Weight = weight;
    this.type = type;
    this.place = place;
    this.expiredDate = expiredDate;
  }

  public int getID() {
    return ID;
  }

  public void setID(int iD) {
    ID = iD;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWeight() {
    return Weight;
  }

  public void setWeight(int weight) {
    Weight = weight;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public Date getExpiredDate() {
    return expiredDate;
  }

  public void setExpiredDate(Date expiredDate) {
    this.expiredDate = expiredDate;
  }

  @Override
  public String toString() {
    return "Food [ID=" + ID + ", name=" + name + ", Weight=" + Weight + ", type=" + type + ", place=" + place
        + ", expiredDate=" + expiredDate + "]";
  }
}
