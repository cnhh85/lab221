package Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utility {
  private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  public static boolean isValidSecondDate(Date firstDate, Date secondDate) {
    Calendar calendar = Calendar.getInstance();

    Date head = null;
    Date tail = null;

    String firstDateString = dateFormat.format(firstDate);

    try {
      calendar.setTime(dateFormat.parse(firstDateString));
    } catch (ParseException e) {
      System.out.println("Something went wrong parsing");
    }
    calendar.add(Calendar.DATE, 28);
    head = handleParseDate(dateFormat.format(calendar.getTime()));
    calendar.add(Calendar.DATE, 56);
    tail = handleParseDate(dateFormat.format(calendar.getTime()));

    if (secondDate.before(head) || secondDate.after(tail)) {
      System.out.println("The second dose of vaccine must be given 4 to 12 weeks after the first injection!");
      return false;
    } else {
      return true;
    }
  }

  public static Date handleParseDate(String inputDate) {
    try {
      Date date = dateFormat.parse(inputDate);
      return date;
    } catch (ParseException e) {
      return null;
    }
  }

  public static boolean isEmpty(String string) {
    if (string.equals("")) {
      System.out.println("String cannot be empty");
      return true;
    } else {
      return false;
    }
  }
}
