package seedu.duke;

import java.util.ArrayList;

/**
 * Class that composites all the transactions
 * Contains commands that prints and searches through transactions
 * Singleton class to allow access by commands that trigger storing a new transaction
 */
public class TransactionManager {
    ArrayList<Transaction> transactions;
    private static TransactionManager instance = null;

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
    public void addTransaction (Currency currency, String description) {
        Transaction newTransaction = new Transaction(currency, description);
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

}
