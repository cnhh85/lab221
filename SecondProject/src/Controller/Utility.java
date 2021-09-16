package Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
  private static final String STUDENTFILE = "student.dat";
  private static final String VACCINEFILE = "vaccine.dat";

  private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  // protected Date validateExpiredDate(String inputDate) {
  //   Date today = new Date();

  //   dateFormat.setLenient(false);
  //   try {
  //     Date date = dateFormat.parse(inputDate);
  //     if (date.after(today)) {
  //       return date;
  //     } else {
  //       System.out.println("The date cannot before today");
  //       return null;
  //     }
  //   } catch (ParseException e) {
  //     System.out.println("Invalid date format or date not exist");
  //     return null;
  //   }

  // }

  protected static Date handleParseDate(String inputDate) {
    try {
      Date date = dateFormat.parse(inputDate);
      return date;
    } catch (ParseException e) {
      System.out.print("Something when wrong");
      return null;
    }
  }

  protected static boolean isEmpty(String string) {
    if (string.equals("")) {
      System.out.println("String cannot be empty");
      return true;
    } else {
      return false;
    }
  }
}
