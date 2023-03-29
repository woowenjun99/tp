package seedu.duke.constants;

/**
 * Contains the messages that will be printed by the ui chatbot
 */
public enum Message {
    BALANCE("Here are the balances that you have requested:"),
    FAREWELL("Thank you for using MoneyMoover! We hope to see you again soon :)"),
    HELP("Here are the commands available:\n"
            + "\t help - show list of commands\n"
            + "\t add CURRENCY AMOUNT [DESCRIPTION] - adds that amount of money into that currency account\n"
            + "\t balance [CURRENCY] - view balances of accounts\n"
            + "\t\t balance <CURRENCY> - View the balance of the specified currency in the account\n"
            + "\t\t balance - displays all currencies\n"
            + "\t exchange CURRENCY1 CURRENCY2 AMOUNT - transfer funds from a currency1 account\n"
            + "\t\t                                   into its equivalent value in currency2 account\n"
            + "\t withdraw CURRENCY AMOUNT [DESCRIPTION] - withdraws that amount of money from that currency account\n"
            + "\t show-rate CURRENCY1 CURRENCY2 [AMOUNT] - shows the value of each dollar in CURRENCY1 in " +
            "terms of CURRENCY2\n"
            + "\t trans [FLAG] [SEARCH PARAM]  - Appropriate flags are\n"
            + "\t                                   - desc : search by the description as search parameter\n"
            + "\t                                   - c : search by currency as search parameter\n"
            + "\t                                   - d : search by date as search parameter in the form dd-MM-yyyy\n"
            + "\t                                   - m : search by month as search parameter in the form MM-yyyy\n"
            + "\t delete-account CURRENCY - deletes the account of that currency\n"
            + "\t create-account CURRENCY - creates an account of that currency\n"
            + "\t trans [flag] [search parameter] - displays transactions for existing accounts\n"
            + "\t\t trans - displays all transactions\n"
            + "\t\t trans desc <description> - displays all transactions with the matching description\n"
            + "\t\t trans c <CURRENCY> - displays all transactions associated to the currency provided\n"
            + "\t\t trans d <dd-MM-yyyy> - displays all transactions that occurred on the specific day provided\n"
            + "\t\t trans m <MM-yyyy> - displays all transactions that occurred within the month provided\n"
            + "\t exit - exits the program\n"
            + "\t Available Currencies: SGD, USD, EUR, GBP, THB, MYR, IDR, VND, CNY, JPY, KRW"),
    GREETING("Welcome to MoneyMoover!"),
    ERR_UNKNOWN_COMMAND("Sorry, I don't understand that command. Type 'help' to see the list of commands."),
    ERR_INVALID_SHOW_RATE("show-rate must be used with the following format: show-rate <CURRENCY1> <CURRENCY2>"),
    SUCCESSFUL_WITHDRAW_COMMAND("You have successfully withdrawn %.2f %s from your account\n " +
            "Now you have remaining %.2f %s in your account "),
    SUCCESSFUL_DELETE_ACCOUNT_COMMAND("You have successfully deleted your %s account\n"),
    SUCCESSFUL_ADD_COMMAND("You have successfully added %s %.2f into your account"),
    LOADING_EXCHANGE_RATES("Initializing live exchange rates. Please wait 5 seconds..."),
    SUCCESSFUL_CREATE_ACCOUNT_COMMAND("You have successfully added the %s account\n"),
    SHOW_ALL_TRANSACTIONS_HEADER("Below are all your transactions in reverse chronological order:"),
    SHOW_TRANSACTIONS_OF_DESC_HEADER("Below are all your transactions with the description "),
    SHOW_TRANSACTIONS_OF_DATE_HEADER("Below are all your transactions with the date "),
    SHOW_TRANSACTIONS_OF_CURRENCY_HEADER("Below are all your transactions with currency "),
    SHOW_TRANSACTIONS_OF_MONTH_HEADER("Below are all your transactions during the month of ");

    private final String message;

    Message (String message) {
        this.message = message;
    }

    public String getMessage () {
        return message;
    }
}
