package seedu.duke;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    LocalDateTime date;
    Currency currency;
    String description;
    // isCredit is true if the amount in the account increases and false otherwise
    boolean isCredit;
    BigDecimal changeInBalance;
    BigDecimal balanceAfterTransaction;


    Transaction (Currency currency, String description, boolean isCredit,
                 BigDecimal changeInBalance, BigDecimal balanceAfterTransaction) {
        this.currency = currency;
        this.description = description;
        date = LocalDateTime.now();
        this.isCredit = isCredit;
        this.changeInBalance = changeInBalance;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public Currency getCurrency () {
        return currency;
    }
}
