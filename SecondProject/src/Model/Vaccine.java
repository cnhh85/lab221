package Model;

public class Vaccine {
  private String vaccineID;
  private String name;

  public Vaccine() {
  }

  public Vaccine(String vaccineID, String name) {
    this.vaccineID = vaccineID;
    this.name = name;
  }

  public String getVaccineID() {
    return vaccineID;
  }

  public void setVaccineID(String vaccineID) {
    this.vaccineID = vaccineID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Vaccine [name=" + name + ", vaccineID=" + vaccineID + "]";
  }
}
