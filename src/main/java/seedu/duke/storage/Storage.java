package seedu.duke.storage;

import java.math.BigDecimal;

/**
 * @@author woowenjun99
 */
public class Storage {
    private final String currency;
    private final BigDecimal value;

    public Storage (String currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }
}