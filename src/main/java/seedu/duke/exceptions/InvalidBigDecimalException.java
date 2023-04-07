package seedu.duke.exceptions;

public class InvalidBigDecimalException extends Exception {

    private final String description;

    public InvalidBigDecimalException (String description) {
        this.description = description;
    }

    public String getDescription () {
        return this.description;
    }
}
