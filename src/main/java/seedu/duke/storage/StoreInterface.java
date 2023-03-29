package seedu.duke.storage;

import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Transaction;
import seedu.duke.TransactionManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The StoreInterface defines the methods that a Store class must implement.
 * The Store class handles the reading from and writing to file.
 */
public interface StoreInterface {
    public void loadAccountsFromStore (AccountList accounts) throws Exception;

    public void saveAccountsToStore (ArrayList<Account> accounts) throws IOException;

    public void loadTransactionsFromStore (TransactionManager transactions) throws Exception;


    public void saveTransactionsToStore (ArrayList<Transaction> transactions) throws IOException;
}
