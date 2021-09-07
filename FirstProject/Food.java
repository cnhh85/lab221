import java.util.Date;

public class Food {
  public String id;
  public String name;
  public int weight;
  public String type;
  public String place;
  public Date expiredDate;

  public Food() {
  }

  public Food(String id, String name, int weight, String type, String place, Date expiredDate) {
    this.id = id;
    this.name = name.toUpperCase();
    this.weight = weight;
    this.type = type;
    this.place = place;
    this.expiredDate = expiredDate;
  }

  public String getID() {
    return id;
  }

  public void setID(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name.toUpperCase();
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
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
    return "ID=" + getID() + " | name=" + getName() + " | weight=" + getWeight() + " | type=" + getType() + " | place="
        + getPlace() + " | expiredDate=" + getExpiredDate();
  }
}
