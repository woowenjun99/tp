package com.moneymoover.constants;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;

/**
 * A public interface that is used to contain constant date-time formats for date-time related transaction commands
 */
public interface DateConstants {
    DateTimeFormatter OUTPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // This assumes the year is not a leap year
    HashMap<Month, Integer> DAYS_IN_MONTH = new HashMap<>() {
        {
            put(Month.JANUARY, 31);
            put(Month.FEBRUARY, 28);
            put(Month.MARCH, 31);
            put(Month.APRIL, 30);
            put(Month.MAY, 31);
            put(Month.JUNE, 30);
            put(Month.JULY, 31);
            put(Month.AUGUST, 31);
            put(Month.SEPTEMBER, 30);
            put(Month.OCTOBER, 31);
            put(Month.NOVEMBER, 30);
            put(Month.DECEMBER, 31);
        }
    };
}
