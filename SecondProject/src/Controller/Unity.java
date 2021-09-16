package Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Unity {
  private final String STUDENTFILE = "student.dat";
  private final String VACCINEFILE = "vaccine.dat";

  private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  protected Date validateExpiredDate(String expiredDate) {
    Date today = new Date();
    if (expiredDate.trim().equals("")) {
      System.out.println("Expired date cannot be empty");
      return null;
    } else {
      dateFormat.setLenient(false);
      try {
        Date date = dateFormat.parse(expiredDate);
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

  protected boolean isEmpty(String string) {
    if (string.equals("")) {
      System.out.println("String cannot be empty");
      return true;
    } else {
      return false;
    }
  }
}
