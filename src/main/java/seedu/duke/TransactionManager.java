package seedu.duke;

import seedu.duke.constants.DateConstants;
import seedu.duke.exceptions.InvalidSearchTransactionByDateException;
import seedu.duke.exceptions.InvalidSearchTransactionByMonthException;
import seedu.duke.exceptions.NoTransactionsOfSearchParameterException;
import seedu.duke.exceptions.NoTransactionsRecordedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Class that composites all the transactions
 * Contains commands that prints and searches through transactions
 * Singleton class to allow access by commands that trigger storing a new transaction
 */
public class TransactionManager {
    private static TransactionManager instance = null;
    private ArrayList<Transaction> transactions;

    private TransactionManager () {
        transactions = new ArrayList<>();
    }

    /**
     * The singleton design pattern getter for the instance of transaction manager
     *
     * @return The transaction manager instance
     */
    public static TransactionManager getInstance () {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    /**
     * Adds a new transaction to the list of transactions
     *
     * @param currency    The currency associated with the transaction
     * @param description The description associated with the transaction
     */
    public void addTransaction (Currency currency, String description, boolean isCredit,
                                BigDecimal changeInBalance, BigDecimal balanceAfterTransaction) {
        Transaction newTransaction = new Transaction(currency, description, isCredit,
                changeInBalance, balanceAfterTransaction);
        transactions.add(newTransaction);
    }

    /**
     * Method called on starting the program to store the transactions retrieved from storage
     * into the transaction manager
     *
     * @param transactions the arraylist of transactions retrieved from storage
     */
    public void populateTransactions (ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Method that appends a transaction appropriately with correct formatting to the input string
     *
     * @param currentString The string to append the transaction to
     * @param transaction   The transaction to be appended
     * @return The string after appending the transaction
     */
    private String appendTransactionToString (String currentString, Transaction transaction) {
        if (!currentString.isEmpty()) {
            // It is not the first transaction to be inserted, thus it should have a separator
            currentString = currentString.concat("\n" + "-".repeat(50) + "\n");
        }
        currentString = currentString.concat(transaction.toString());
        return currentString;
    }

    /**
     * Method that formats and returns a string representing all recorded transactions
     *
     * @return String representing all recorded transactions
     * @throws NoTransactionsRecordedException if there are no recorded transactions
     */
    public String getAllTransactionsString () throws NoTransactionsRecordedException {
        if (transactions.size() == 0) {
            throw new NoTransactionsRecordedException();
        }
        String stringToReturn = "";
        // Iterate backwards for reverse chronological order
        for (int i = transactions.size() - 1; i >= 0; --i) {
            stringToReturn = appendTransactionToString(stringToReturn, transactions.get(i));
        }
        return stringToReturn;
    }

    /**
     * Method that formats and returns a string representing all transactions containing a given description
     *
     * @param description The description to search for
     * @return The String representing all corresponding transactions
     * @throws NoTransactionsRecordedException          if there are no transactions in the list
     * @throws NoTransactionsOfSearchParameterException if there are no transactions matching search parameters
     */
    public String getAllTransactionsOfDescription (String description) throws NoTransactionsRecordedException,
            NoTransactionsOfSearchParameterException {
        if (transactions.size() == 0) {
            throw new NoTransactionsRecordedException();
        }
        String stringToReturn = "";
        // Iterate backwards for reverse chronological order
        for (int i = transactions.size() - 1; i >= 0; --i) {
            Transaction transaction = transactions.get(i);
            if (transaction.getDescription().toLowerCase().contains(description.toLowerCase())) {
                stringToReturn = appendTransactionToString(stringToReturn, transaction);
            }
        }
        if (stringToReturn.isEmpty()) {
            throw new NoTransactionsOfSearchParameterException();
        }
        return stringToReturn;
    }

    /**
     * Method that formats and returns a string representing all transactions associated to a given currency
     *
     * @param currencyString The String representing the currency to search for
     * @return The String representing all corresponding transactions
     * @throws NoTransactionsRecordedException          if there are no transactions in the list
     * @throws IllegalArgumentException                 if the currencyString is not a valid currency
     * @throws NoTransactionsOfSearchParameterException if there are no transactions matching search parameters
     */
    public String getAllTransactionsOfCurrency (String currencyString) throws NoTransactionsRecordedException,
            IllegalArgumentException, NoTransactionsOfSearchParameterException {
        if (transactions.size() == 0) {
            throw new NoTransactionsRecordedException();
        }
        Currency currency = Currency.valueOf(currencyString);
        String stringToReturn = "";
        for (int i = transactions.size() - 1; i >= 0; --i) {
            Transaction transaction = transactions.get(i);
            if (transaction.getCurrency().equals(currency)) {
                stringToReturn = appendTransactionToString(stringToReturn, transaction);
            }
        }
        if (stringToReturn.isEmpty()) {
            throw new NoTransactionsOfSearchParameterException();
        }
        return stringToReturn;
    }

    /**
     * Method that formats and returns a string representing all transactions associated to a given date
     *
     * @param dateString The String representing the date to search for
     * @return The String representing all corresponding transactions
     * @throws NoTransactionsRecordedException          if there are no transactions in the list
     * @throws NoTransactionsOfSearchParameterException if there are transactions matching search parameters
     * @throws InvalidSearchTransactionByDateException  if the date string provided is in the wrong format
     */
    public String getAllTransactionsOfDate (String dateString) throws NoTransactionsRecordedException,
            NoTransactionsOfSearchParameterException, InvalidSearchTransactionByDateException {
        if (transactions.size() == 0) {
            throw new NoTransactionsRecordedException();
        }
        LocalDate date;
        try {
            date = LocalDate.parse(dateString, DateConstants.INPUT_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidSearchTransactionByDateException();
        }

        String stringToReturn = "";
        for (int i = transactions.size() - 1; i >= 0; --i) {
            Transaction transaction = transactions.get(i);
            if (transaction.getDate().toLocalDate().isEqual(date)) {
                // This transaction occurred on the specified day
                stringToReturn = appendTransactionToString(stringToReturn, transaction);
            }
        }
        if (stringToReturn.isEmpty()) {
            throw new NoTransactionsOfSearchParameterException();
        }
        return stringToReturn;
    }

    /**
     * Method that formats and returns a string representing all transactions associated to a given month
     *
     * @param monthString The String representing the month to search for
     * @return The String representing all corresponding transactions
     * @throws NoTransactionsRecordedException          if there are no transactions in the list
     * @throws NoTransactionsOfSearchParameterException if there are no transactions matching search parameters
     * @throws InvalidSearchTransactionByMonthException if the month string provided is in invalid format
     */
    public String getAllTransactionsOfMonth (String monthString) throws NoTransactionsRecordedException,
            NoTransactionsOfSearchParameterException, InvalidSearchTransactionByMonthException {
        if (transactions.size() == 0) {
            throw new NoTransactionsRecordedException();
        }
        String dateString = "01-" + monthString;
        LocalDate date;
        try {
            date = LocalDate.parse(dateString, DateConstants.INPUT_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidSearchTransactionByMonthException();
        }
        YearMonth monthToSearch = YearMonth.from(date);
        String stringToReturn = "";
        for (int i = transactions.size() - 1; i >= 0; --i) {
            Transaction transaction = transactions.get(i);
            if (monthToSearch.equals(YearMonth.from(transaction.getDate().toLocalDate()))) {
                // This transaction occurred on the specified month
                stringToReturn = appendTransactionToString(stringToReturn, transaction);
            }
        }
        if (stringToReturn.isEmpty()) {
            throw new NoTransactionsOfSearchParameterException();
        }
        return stringToReturn;
    }
}
