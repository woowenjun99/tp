package com.moneymoover.constants;

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
            + "\t\t balance CURRENCY - View the balance of the specified currency in the account\n"
            + "\t\t balance - displays all currencies\n"
            + "\t exchange CURRENCY1 CURRENCY2 AMOUNT - transfer funds from a currency1 account\n"
            + "\t\t                                   into its equivalent value in currency2 account\n"
            + "\t withdraw CURRENCY AMOUNT [DESCRIPTION] - withdraws that amount of money from that currency account\n"
            + "\t show-rate CURRENCY1 CURRENCY2 [AMOUNT] - shows the value of each dollar in CURRENCY1 in " +
            "terms of CURRENCY2\n"
            + "\t delete-account CURRENCY - deletes the account of that currency\n"
            + "\t create-account CURRENCY - creates an account of that currency\n"
            + "\t trans [FLAG] [SEARCH_PARAM] - Appropriate flags are\n "
            + "\t\t i) desc : search by the description as search parameter\n "
            + "\t\t ii) c : search by currency as search parameter\n "
            + "\t\t iii) d : search by date as search parameter in the form DD-MM-YYYY\n "
            + "\t\t iv) m : search by month as search parameter in the form MM-YYYY\n "
            + "\t exit - exits the program\n"
            + "\t Available Currencies: SGD, USD, EUR, GBP, THB, MYR, IDR, VND, CNY, JPY, KRW"),
    GREETING("Welcome to MoneyMoover!"),
    SUCCESSFUL_WITHDRAW_COMMAND("You have successfully withdrawn %.2f %s from your account\n " +
            "Now you have remaining %.2f %s in your account "),
    SUCCESSFUL_DELETE_ACCOUNT_COMMAND("You have successfully deleted your %s account\n"),
    SUCCESSFUL_ADD_COMMAND("You have successfully added %s %.2f into your account"),
    LOADING_EXCHANGE_RATES("Initializing live exchange rates. Please wait for a confirmation message..."),
    SUCCESSFUL_CREATE_ACCOUNT_COMMAND("You have successfully added the %s account\n"),
    SHOW_ALL_TRANSACTIONS_HEADER("Below are all your transactions in reverse chronological order:"),
    SHOW_TRANSACTIONS_OF_DESC_HEADER("Below are all your transactions with the description "),
    SHOW_TRANSACTIONS_OF_DATE_HEADER("Below are all your transactions with the date "),
    SHOW_TRANSACTIONS_OF_CURRENCY_HEADER("Below are all your transactions with currency "),
    SHOW_TRANSACTIONS_OF_MONTH_HEADER("Below are all your transactions during the month of "),
    API_INITIALIZED("Real-time exchange rates have been set! You are free to use MoneyMoover!"),
    NO_ACCOUNT_TO_SHOW("There is no account to be displayed");

    private final String message;

    Message (String message) {
        this.message = message;
    }

    public String getMessage () {
        return message;
    }
}
