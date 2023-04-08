package seedu.duke.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import seedu.duke.Currency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Store class handles the reading from and writing to the file.
 * It uses the Gson library to serialize and deserialize JSON data.
 */
public class Store implements StoreInterface {
    private static final String ACCOUNTS_FILE_NAME = "accounts.json";
    private static final String TRANSACTIONS_FILE_NAME = "transactions.json";
    private final String directory;
    private final Gson gson;
    private final Logger logger = Logger.getLogger("logger");

    public Store (String directory) {
        this.directory = directory;
        // creates new gson instance with the LocalDateTime adapter
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        this.gson = gsonBuilder.create();
    }


    /**
     * Checks and creates the directory and/or files if they are not found
     *
     * @param fullPath The full path of the file, including the directory and file name
     * @throws IOException if the file cannot be created
     */
    private void createFileIfNotExist (String fullPath) throws IOException {
        File file = new File(fullPath);
        if (file.getParentFile().mkdirs()) {
            logger.log(Level.INFO, "Created directory " + directory);
        }
        if (!file.exists()) {
            logger.log(Level.INFO, "Created file " + fullPath);
            FileWriter writer = new FileWriter(fullPath);
            // Add in an empty array to prevent error from being thrown.
            writer.write("[]");
            writer.close();
        }
    }

    /**
     * Populates the account list with the previously stored data in the
     * json file.
     *
     * @param accounts The AccountList instance
     */
    public void loadAccountsFromStore (AccountList accounts) throws Exception {
        String fullPath = directory + ACCOUNTS_FILE_NAME;
        createFileIfNotExist(fullPath);
        BufferedReader br = new BufferedReader(new FileReader(fullPath));
        Storage[] store = gson.fromJson(br, Storage[].class);
        for (Storage account : store) {
            Currency currency = Currency.valueOf(account.getCurrency().toUpperCase());
            float value = account.getValue() / 100.0f;
            logger.log(Level.INFO, "Loaded account " + currency + " with value " + value);
            accounts.addAccount(currency, value);
        }
        // If the 2 lengths do not match, there is a problem.
        assert accounts.getAllAccounts().size() == store.length;
        logger.log(Level.INFO, "accounts loaded successfully");
    }

    /**
     * Populates the account list with the previously stored data in the
     * json file.
     *
     * @param transactions The TransactionManager instance
     */
    public void loadTransactionsFromStore (TransactionManager transactions) throws Exception {
        String fullPath = directory + TRANSACTIONS_FILE_NAME;
        createFileIfNotExist(fullPath);
        BufferedReader br = new BufferedReader(new FileReader(fullPath));
        ArrayList<Transaction> store = gson.fromJson(br, new TypeToken<ArrayList<Transaction>>() {
        }.getType());
        transactions.populateTransactions(store);
        // If the 2 lengths do not match, there is a problem.
        assert transactions.getSize() == store.size();
        logger.log(Level.INFO, "transactions loaded successfully");
    }

    /**
     * Saves the accounts to the json file.
     *
     * @param accounts The ArrayList of accounts
     */
    public void saveAccountsToStore (ArrayList<Account> accounts) throws IOException {
        String fullPath = directory + ACCOUNTS_FILE_NAME;
        createFileIfNotExist(fullPath);
        File file = new File(fullPath);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            ArrayList<Storage> storages = new ArrayList<>();
            for (Account account : accounts) {
                storages.add(new Storage(account.getCurrencyType().toString(), account.getLongBalance()));
            }
            gson.toJson(storages, fw);
            fw.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("Error saving accounts to store: %s", e.getMessage()));
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
    }

    /**
     * Saves the transactions to the json file.
     *
     * @param transactions The ArrayList of transactions
     */
    public void saveTransactionsToStore (ArrayList<Transaction> transactions) throws IOException {
        String fullPath = directory + TRANSACTIONS_FILE_NAME;
        createFileIfNotExist(fullPath);
        File file = new File(fullPath);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            gson.toJson(transactions, fw);
            fw.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("Error saving transactions to store: %s", e.getMessage()));
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
    }
}
