package com.moneymoover;

import com.moneymoover.constants.DateConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A class that records essential information about transactions carried out by the user
 */
public class Transaction {
    // The date of the transaction
    private final LocalDateTime date;
    // The Currency of the account associated with the transaction
    private final Currency currency;
    // The description of the transaction such as the reason for a withdrawal
    private final String description;
    // isCredit is true if the amount in the account increases and false otherwise
    private final boolean isCredit;
    private final BigDecimal changeInBalance;
    private final BigDecimal balanceAfterTransaction;

    public Transaction (Currency currency, String description, boolean isCredit,
                        BigDecimal changeInBalance, BigDecimal balanceAfterTransaction) {
        if (description.isEmpty()) {
            description = "NIL";
        }
        date = LocalDateTime.now();
        this.currency = currency;
        this.description = description;
        this.isCredit = isCredit;
        this.changeInBalance = changeInBalance;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public LocalDateTime getDate () {
        return date;
    }

    public Currency getCurrency () {
        return currency;
    }

    public String getDescription () {
        return description;
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
        stringToReturn = stringToReturn.concat(String.format(" %.2f\n", changeInBalance));
        stringToReturn = stringToReturn.concat("Amount in account after transaction: " + currencyString);
        stringToReturn = stringToReturn.concat(String.format(" %.2f\n", balanceAfterTransaction));
        stringToReturn = stringToReturn.concat("Description: " + description + "\n");
        stringToReturn = stringToReturn.concat("At: " + date.format(DateConstants.OUTPUT_DATE_TIME_FORMATTER));
        return stringToReturn;
    }
}
