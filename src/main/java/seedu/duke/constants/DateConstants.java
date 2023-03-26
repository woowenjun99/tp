package seedu.duke.constants;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public interface DateConstants {
    DateTimeFormatter OUTPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    DateTimeFormatter INPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
}
