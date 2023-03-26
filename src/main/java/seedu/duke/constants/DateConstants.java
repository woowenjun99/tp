package seedu.duke.constants;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * A public interface that is used to contain constant date-time formats for date-time related transaction commands
 */
public interface DateConstants {
    DateTimeFormatter OUTPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
}
