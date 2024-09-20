package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    private Date testDate1;
    private Date testDate2;

    @BeforeEach
    public void setUp() {
        testDate1 = new Date(2023, Calendar.NOVEMBER, 1);
        testDate2 = new Date(2023, Calendar.DECEMBER, 31);
    }

    @Test
    void testAddDaysToDate() {
        assertEquals(new Date(2023, Calendar.NOVEMBER, 16), DateUtils.addDaysToDate(testDate1, 15));
        assertEquals(new Date(2024, Calendar.JANUARY, 15), DateUtils.addDaysToDate(testDate2, 15));
    }

    @Test
    void testAreDatesEqual() {
        assertFalse(DateUtils.areDatesEqual(testDate1, testDate2));
        assertTrue(DateUtils.areDatesEqual(testDate1, new Date(2023, Calendar.NOVEMBER, 1)));
        assertFalse(DateUtils.areDatesEqual(testDate1, new Date(2024, Calendar.NOVEMBER, 1)));
        // although there is a time difference, two dates are in the same day of the same year, so they should always
        // return false.
        assertTrue(DateUtils.areDatesEqual(new Date(1707552038746L), new Date(1707552038750L)));
        assertFalse(DateUtils.areDatesEqual(testDate2, new Date(2023, Calendar.NOVEMBER, 10)));
    }
}