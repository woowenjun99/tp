package seedu.duke.constants;

/**
 * Contains the messages that will be printed by the ui chatbot
 */
public enum Message {
    BALANCE("Here are the balances that you have requested:"),
    FAREWELL("Thank you for using MoneyMoover! We hope to see you again soon :)"),
    HELP("
            here are the commands available:
            \t help - show list of commands
            \t add $/CURRENCY AMOUNT - adds that amount of money into that currency account
            \t exchange $/CURRENCY1 AMOUNT $/CURRENCY2 - transfer funds from a currency account
            \t\t                                       into its equivalent value in another currency account
            \t withdraw $/CURRENCY AMOUNT - withdraws that amount of money from that currency account
            \t show $/CURRENCY1 $/CURRENCY2 - shows the value of each dollar in CURRENCY1 in terms of CURRENCY2
            \t delete-account $/CURRENCY - deletes the account of that currency
            \t create-account $/CURRENCY - creates an account of that currency
            \t exit - exits the program
            \t Available Currencies: MYR, SGD, USD, YUAN, THB, EU"),
    GREETING("Welcome to MoneyMoover!"),
    ERR_UNKNOWN_COMMAND("Sorry, I don't understand that command. Type 'help' to see the list of commands."),
    ERR_INVALID_SHOW_RATE("show-rate must be used with the following format: show-rate <CURRENCY1> <CURRENCY2>"),
    SUCCESSFUL_ADD_COMMAND("You have successfully added %s %f into your account");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
