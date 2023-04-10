package com.moneymoover.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.moneymoover.Account;
import com.moneymoover.Currency;
import com.moneymoover.AccountList;
import com.moneymoover.Transaction;
import com.moneymoover.TransactionManager;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.exceptions.AccountAlreadyExistsException;
import com.moneymoover.ui.Ui;

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
    private final Ui ui;

    public Store (String directory, Ui ui) {
        this.directory = directory;
        // creates new gson instance with the LocalDateTime adapter
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        this.gson = gsonBuilder.create();
        this.ui = ui;
    }

    /**
     * Creates the directory and/or files with an empty json
     *
     * @param fullPath The full path of the file, including the directory and file name
     * @throws IOException if the file cannot be created
     */
    private void createFile (String fullPath) throws IOException {
        logger.log(Level.INFO, "Created file " + fullPath);
        FileWriter writer = new FileWriter(fullPath);
        // Add in an empty array to prevent error from being thrown.
        writer.write("[]");
        writer.close();

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
            createFile(fullPath);
        }
    }

    /**
     * Populates the account list with the previously stored data in the
     * json file.
     *
     * @param accounts The AccountList instance
     */
    public void loadAccountsFromStore (AccountList accounts) throws IOException {
        String fullPath = directory + ACCOUNTS_FILE_NAME;
        createFileIfNotExist(fullPath);
        BufferedReader br = new BufferedReader(new FileReader(fullPath));
        Storage[] store = new Storage[]{};
        try {
            store = gson.fromJson(br, Storage[].class);
        } catch (Exception e) {
            ui.printMessage(ErrorMessage.ERR_LOADING_ACCOUNTS);
            createFile(fullPath);
        }

        for (Storage account : store) {
            Currency currency = Currency.valueOf(account.getCurrency().toUpperCase());
            long value = account.getValue();
            logger.log(Level.INFO, "Loaded account " + currency + " with value " + value);
            try {
                if (value < 0) {
                    ui.printMessage(ErrorMessage.ACCOUNT_BALANCE_NEGATIVE_WHEN_LOADING);
                    value = 0;
                }
                if (value > Account.UPPER_BOUND) {
                    ui.printMessage(ErrorMessage.ACCOUNT_BALANCE_TOO_LARGE_WHEN_LOADING);
                    value = Account.UPPER_BOUND;
                }
                accounts.addAccountWithLong(currency, value);
            } catch (AccountAlreadyExistsException e) {
                logger.log(Level.WARNING,
                        "Skipping duplicate account " + currency + " with value " + value + " found in store");
                ui.printf(ErrorMessage.DUPLICATE_ACCOUNT_WHEN_LOADING, currency);
            }
        }
        // If the 2 lengths do not match, there is a problem.
        assert accounts.getAllAccounts().size() == store.length;
        logger.log(Level.INFO, "Accounts loaded successfully");

    }

    /**
     * Populates the account list with the previously stored data in the
     * json file.
     *
     * @param transactions The TransactionManager instance
     */
    public void loadTransactionsFromStore (TransactionManager transactions) throws IOException {
        String fullPath = directory + TRANSACTIONS_FILE_NAME;
        createFileIfNotExist(fullPath);
        BufferedReader br = new BufferedReader(new FileReader(fullPath));
        ArrayList<Transaction> store = new ArrayList<>();
        try {
            store = gson.fromJson(br, new TypeToken<ArrayList<Transaction>>() {
            }.getType());
        } catch (Exception e) {
            ui.printMessage(ErrorMessage.ERR_LOADING_TRANSACTIONS);
            createFile(fullPath);
        }

        transactions.populateTransactions(store);
        // If the 2 lengths do not match, there is a problem.
        assert transactions.getSize() == store.size();
        logger.log(Level.INFO, "Transactions loaded successfully");
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
        try (FileWriter fw = new FileWriter(file)) {
            ArrayList<Storage> storages = new ArrayList<>();
            for (Account account : accounts) {
                storages.add(new Storage(account.getCurrencyType().toString(), account.getLongBalance()));
            }
            gson.toJson(storages, fw);
            fw.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("Error saving accounts to store: %s", e.getMessage()));
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
        try (FileWriter fw = new FileWriter(file)) {
            gson.toJson(transactions, fw);
            fw.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("Error saving transactions to store: %s", e.getMessage()));
        }
    }
}
