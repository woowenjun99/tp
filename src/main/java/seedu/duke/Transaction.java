package seedu.duke;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * A class that records essential information about transactions carried out by the user
 */
public class Transaction {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    // The date of the transaction
    LocalDateTime date;
    // The Currency of the account associated with the transaction
    Currency currency;
    // The description of the transaction such as the reason for a withdrawal
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

    @Override
    public String toString () {
        String stringToReturn = "";
        if (isCredit) {
            stringToReturn = stringToReturn.concat("+");
        } else {
            stringToReturn = stringToReturn.concat("-");
        }
        String currencyString = currency.name();
        stringToReturn = stringToReturn.concat(currencyString);
        stringToReturn = stringToReturn.concat(String.format("%.2f\n", changeInBalance));
        stringToReturn = stringToReturn.concat("Amount in account after transaction: " + currencyString);
        stringToReturn = stringToReturn.concat(String.format("%.2f\n", balanceAfterTransaction));
        stringToReturn = stringToReturn.concat("Description: " + description + "\n");
        stringToReturn = stringToReturn.concat("At: " + date.format(DATE_TIME_FORMATTER));
        return stringToReturn;
    }
}
