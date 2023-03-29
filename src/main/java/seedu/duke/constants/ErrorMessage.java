package seedu.duke.constants;

/**
 * A public interface that is used to contain all the error messages throughout the application.
 */
public interface ErrorMessage {
    String INVALID_ADD_COMMAND = "Please check that you have correctly provided the currency and amount";
    String INVALID_AMOUNT_TO_ADD_OR_WITHDRAW = "Please provide a value greater than or equal to 0.01";
    String INVALID_CURRENCY = "An invalid currency has been provided.";
    String INVALID_NUMERICAL_AMOUNT = "Please provide a float amount to update";
    String INVALID_CREATE_ACCOUNT_COMMAND = "create-account must be used with the following format: " +
            "create-account CURRENCY";
    String MORE_THAN_ONE_CURRENCY_PROVIDED = "Please do not provide more than one currency.";
    String NO_SUCH_ACCOUNT = "You do not have an account for the currency.";

    String INVALID_WITHDRAW_COMMAND = "Please check that you have correctly provided the currency and amount";
    String NO_AMOUNT_PROVIDED = "Please provide an amount to be added.";

    String INVALID_EXCHANGE_ARGUMENT = "Please structure your exchange as " +
            "'exchange STARTING_CURRENCY TARGET_CURRENCY AMOUNT_IN_STARTING'";
    String NOT_ENOUGH_IN_ACCOUNT = "Please ensure you have enough money in your" +
            " starting currency account to perform this transaction";
    String INVALID_NUMBER = "Please enter a valid number to exchange";
    String INVALID_DELETE_ACCOUNT_COMMAND = "Please check that you have correctly provided the " +
            "currency account to delete";
    String ACCOUNT_NOT_EMPTY = "Please empty your currency account before account deletion";
    String NEGATIVE_NUMBER = "Please enter a positive number to show the rate!";
    String SHOWRATE_SYNTAX = "Please structure show-rate as 'show-rate CURRENCY CURRENCY [AMOUNT]'";
    String ACCOUNT_ALREADY_EXISTS = "You already have an account of this currency";
    String INVALID_UPDATE_BALANCE_ACTION = "The provided action for updating balance was invalid";
    String EXCEED_AMOUNT_ALLOWED = "You are not allowed to store more than $10,000,000 in your account. Please"
            + " check how much you have in your balance and how much you are depositing before adding the value in.";
    String EXCHANGE_AMOUNT_TOO_SMALL = "The amount is too small to exchange " +
            "(e.g. does not become at least 0.01 in the target currency)";
    String NO_TRANSACTIONS_RECORDED = "You have no transactions with any of your existing accounts";
    String NO_TRANSACTIONS_FOUND = "You have no transactions of the specified search parameters";
    String INVALID_TRANSACTION_FLAG = "Sorry, I do not recognise that transaction flag, the available flags are:\n" +
            "trans - no flag specified which prints all transactions for existing accounts\n" +
            "trans desc <description> - search transactions by description\n" +
            "trans m <MM-yyyy> - search transactions by month in the form MM-yyyy\n" +
            "trans c <CURRENCY> - search transactions by valid currencies such as SGD\n" +
            "trans d <dd-MM-yyyy> - search transactions by a specific date in the form dd-MM-yyyy";
    String INVALID_TRANSACTIONS_OF_DESC = "Please search transactions by description in the following format:\n" +
            "trans desc <description>";
    String INVALID_TRANSACTIONS_OF_MONTH = "Please search transactions by month in the following format:\n" +
            "trans m <MM-yyyy>";
    String INVALID_TRANSACTIONS_OF_CURRENCY = "Please search transactions by currency in the following format:\n" +
            "trans c <CURRENCY>";
    String INVALID_TRANSACTIONS_OF_DATE = "Please search transactions by date in the following format:\n" +
            "trans d <dd-MM-yyyy>";
    String RATE_LOADING_INCOMPLETE = "The rates did not have adequate time to load! Please restart MoneyMoover.";
    String RESPONSE_CODE_OUT_OF_BOUNDS = "The API server returned a code outside of 200-299. Please contact " +
            "the MoneyMoover team to report this error!";
    String NETWORK_OR_UNEXPECTED_ERROR = "Please check your Internet connection and try again. If problems persist " +
            ", please report this error to the MoneyMoover team!";
}
