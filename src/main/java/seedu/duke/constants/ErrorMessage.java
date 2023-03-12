package seedu.duke.constants;

/**
 * A public interface that is used to contain all the error messages throughout the application.
 */
public interface ErrorMessage {
    String INVALID_ADD_COMMAND = "Please check that you have provided the input in the " +
            "following format: add <Currency> <Amount>";
    String INVALID_CURRENCY = "An invalid currency has been provided.";
    String INVALID_NUMERICAL_AMOUNT = "Please provide a numerical amount";
    String MORE_THAN_ONE_CURRENCY_PROVIDED = "Please do not provide more than one currency.";
    String NO_SUCH_ACCOUNT = "You do not have an account for the currency.";
}
