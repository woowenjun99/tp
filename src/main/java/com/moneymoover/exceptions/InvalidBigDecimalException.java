package com.moneymoover.exceptions;

/**
 * An exception thrown if the value for a numeric command is negative, too big, or non-numeric
 */
public class InvalidBigDecimalException extends Exception {

    private final String description;

    public InvalidBigDecimalException (String description) {
        this.description = description;
    }

    public String getDescription () {
        return this.description;
    }
}
