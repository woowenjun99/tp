package seedu.duke;

import seedu.duke.constants.DateConstants;
import seedu.duke.exceptions.NoTransactionsOfSearchParameterException;
import seedu.duke.exceptions.NoTransactionsRecordedException;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public String getAllTransactionsOfDate (String dateString) throws NoTransactionsRecordedException,
            NoTransactionsOfSearchParameterException {
        if (transactions.size() == 0) {
            throw new NoTransactionsRecordedException();
        }
        LocalDate date = LocalDate.parse(dateString, DateConstants.INPUT_DATE_TIME_FORMATTER);
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
}
