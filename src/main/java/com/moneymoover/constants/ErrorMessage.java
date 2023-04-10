package com.moneymoover.constants;

/**
 * A public interface that is used to contain all the error messages throughout the application.
 */
public interface ErrorMessage {
    String INVALID_ADD_COMMAND = "This is an invalid add command format. \n" +
            "Please check that you have correctly provided the currency and amount according this format:\n" +
            "\t add CURRENCY AMOUNT [DESCRIPTION]";
    String INVALID_TOO_SMALL_AMOUNT_TO_ADD_OR_WITHDRAW = "Please provide a value greater than or equal to 0.01";
    String INVALID_COMMAND_TOO_PRECISE_AMOUNT =
            "Please provide an amount that has a precision of not more than 2 decimal places";
    String INVALID_WITHDRAW_COMMAND = "This is an invalid withdraw command format. \n" +
            "Please check that you have correctly provided the currency and amount according this format:\n" +
            "\t withdraw CURRENCY AMOUNT [DESCRIPTION] ";
    String INVALID_CURRENCY = "An invalid currency has been provided.";
    String INVALID_NUMERICAL_AMOUNT = "Please provide a float amount of up to 2 decimal places to update.";
    String INVALID_CREATE_ACCOUNT_COMMAND = "create-account must be used with the following format: " +
            "create-account CURRENCY";
    String MORE_THAN_ONE_CURRENCY_PROVIDED = "This is an invalid balance command format. \n" + 
            "Please check that you have correctly provided the balance and currency in the following format:\n" +
            "\t balance [CURRENCY]";
    String NO_SUCH_ACCOUNT = "You do not have an account for these currencies.";

    String NO_AMOUNT_PROVIDED = "Please provide an amount to be added.";

    String INVALID_EXCHANGE_ARGUMENT = "Please structure your exchange as " +
            "'exchange CURRENCY1 CURRENCY2 AMOUNT'";
    String NOT_ENOUGH_IN_ACCOUNT = "Please ensure you have enough money in your" +
            " starting currency account to perform this transaction";
    String INVALID_NUMBER = "Please enter a valid number to exchange";
    String INVALID_DELETE_ACCOUNT_COMMAND = "Please check that you have correctly provided the " +
            "currency account to delete";
    String ACCOUNT_NOT_EMPTY = "Please empty your currency account before account deletion";
    String NEGATIVE_NUMBER = "Please enter a positive number to show the rate!";
    String SHOWRATE_SYNTAX = "Please structure show-rate as 'show-rate CURRENCY1 CURRENCY2 [AMOUNT]'";
    String ACCOUNT_ALREADY_EXISTS = "You already have an account of this currency";
    String INVALID_UPDATE_BALANCE_ACTION = "The provided action for updating balance was invalid";
    String EXCEED_AMOUNT_ALLOWED = "You are not allowed to store more than $10,000,000 of any currency in your " +
            "account.\nPlease check how much you have in your balance and how much you are depositing before " +
            "adding the value in.";
    String EXCHANGE_AMOUNT_TOO_SMALL = "The amount is too small to exchange " +
            "(e.g. does not become at least 0.01 in the target currency)";
    String NO_TRANSACTIONS_RECORDED = "You have no transactions with any of your existing accounts";
    String NO_TRANSACTIONS_FOUND = "You have no transactions of the specified search parameters";
    String INVALID_TRANSACTION_FLAG = "Sorry, I do not recognise that transaction flag, the available flags are:\n" +
            "\ti) trans - no flag specified which prints all transactions for existing accounts\n" +
            "\tii) trans desc DESCRIPTION - search transactions by description\n" +
            "\tiii) trans c CURRENCY - search transactions by valid currencies such as SGD\n" +
            "\tiv) trans d DD-MM-YYYY - search transactions by a specific date in the form DD-MM-YYYY\n" +
            "\tv) trans m MM-YYY - search transactions by month in the form MM-YYYY";
    String INVALID_TRANSACTIONS_OF_DESC = "Please search transactions by description in the following format:\n" +
            "trans desc DESCRIPTION";
    String INVALID_TRANSACTIONS_OF_MONTH = "Please search transactions by month in the following format:\n" +
            "trans m MM-YYYY";
    String INVALID_TRANSACTIONS_OF_CURRENCY = "Please search transactions by currency in the following format:\n" +
            "trans c CURRENCY";
    String INVALID_TRANSACTIONS_OF_DATE = "Please search transactions by date in the following format:\n" +
            "trans d DD-MM-YYYY";
    String RATE_LOADING_INCOMPLETE = "The rates did not have adequate time to load! Please restart MoneyMoover.";
    String RESPONSE_CODE_OUT_OF_BOUNDS = "The API server returned a code outside of 200-299. Please contact " +
            "the MoneyMoover team to report this error!";
    String NETWORK_OR_UNEXPECTED_ERROR = "The API could not be loaded! Please check your Internet connection " +
            "and try again. If problems persist, please report this error to the MoneyMoover team!";
    String OUTDATED_RATES = "In the meantime, we will use recently saved exchange rates!";

    String DESCRIPTION_TOO_LONG = "Please limit your description to 100 characters";
    String SHOW_RATE_RANGE = "Your show-rate value must be no smaller than 0.01 and no greater than 1 billion!";

    String ERR_LOADING_TRANSACTIONS = "There was an error loading your transactions from the file, creating a new one";
    String ERR_LOADING_ACCOUNTS = "There was an error loading your accounts from the file, creating a new one";

    String DUPLICATE_ACCOUNT_WHEN_LOADING = "Skipping duplicate account of currency %s in loaded file\n";
    String ACCOUNT_BALANCE_NEGATIVE_WHEN_LOADING = "Negative account balance found in file, defaulting to 0";
    String ACCOUNT_BALANCE_TOO_LARGE_WHEN_LOADING =
            "Account balance greater than 1 billion found in file, defaulting to 1 billion";

    String UNKNOWN_COMMAND = "Sorry, I don't understand that command. Type 'help' to see the list of commands.";
    String EXCEED_UPPER_BOUND = "Please limit your value to $10,000,000";
    String EXCHANGE_SAME_CURRENCY = "You cannot exchange between the same currency!";
    String LOSS_OF_EXCHANGE_VALUE = "Please try a larger amount so you don't lose money!";
}
