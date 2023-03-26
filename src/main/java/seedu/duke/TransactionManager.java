package seedu.duke;

import seedu.duke.exceptions.NoTransactionsRecordedException;

import java.math.BigDecimal;
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
            stringToReturn = stringToReturn.concat(transactions.get(i).toString());
            if (i > 0) {
                // Not the first element in the list
                stringToReturn = stringToReturn.concat("\n" + "-".repeat(50) + "\n");
            }
        }
        return stringToReturn;
    }
}
