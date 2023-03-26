package seedu.duke;

import java.time.LocalDateTime;

public class Transaction {
    LocalDateTime date;
    Currency currency;
    String description;

    Transaction (Currency currency, String description) {
        this.currency = currency;
        this.description = description;
        date = LocalDateTime.now();
    }
}
