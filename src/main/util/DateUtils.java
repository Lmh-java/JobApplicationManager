package util;

import java.util.Calendar;
import java.util.Date;

/**
 * Utils for date processing
 */
public final class DateUtils {

    // EFFECTS: create a private constructor that disables initialization
    private DateUtils() {

    }

    // REQUIRES: numDays > 0 && base != null
    // EFFECTS: returns the date object of numDays after the base date.
    public static Date addDaysToDate(Date base, int numDays) {
        Calendar calendar = Calendar.getInstance();
        // set the start date to 'base'
        calendar.setTime(base);
        // add numDays to the start date
        calendar.add(Calendar.DATE, numDays);
        // convert to Date Object
        return calendar.getTime();
    }

    // REQUIRES: firstDate != null && secondDate != null
    // EFFECTS: returns two dates are equal (year, month, day are equal)
    public static boolean areDatesEqual(Date firstDate, Date secondDate) {
        // turns those date objects to calendar objects
        Calendar firstCalendar = Calendar.getInstance();
        Calendar secondCalendar = Calendar.getInstance();

        firstCalendar.setTime(firstDate);
        secondCalendar.setTime(secondDate);

        // returns true when both day of year and year are equal
        return firstCalendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR)
                && firstCalendar.get(Calendar.DAY_OF_YEAR) == secondCalendar.get(Calendar.DAY_OF_YEAR);
    }
}
