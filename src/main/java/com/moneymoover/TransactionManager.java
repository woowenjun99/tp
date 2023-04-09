package com.moneymoover;

import com.moneymoover.exceptions.InvalidSearchTransactionByDateException;
import com.moneymoover.exceptions.InvalidSearchTransactionByMonthException;
import com.moneymoover.exceptions.NoTransactionsOfSearchParameterException;
import com.moneymoover.exceptions.NoTransactionsRecordedException;
import com.moneymoover.constants.DateConstants;
import com.moneymoover.storage.StoreInterface;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that composites all the transactions
 * Contains commands that prints and searches through transactions
 * Singleton class to allow access by commands that trigger storing a new transaction
 */
public class TransactionManager {
    private static TransactionManager instance = null;
    private ArrayList<Transaction> transactions;
    private StoreInterface store;
    private final Logger logger = Logger.getLogger("logger");

    private TransactionManager (StoreInterface store) {
        transactions = new ArrayList<>();
        this.store = store;
    }

    /**
     * The singleton design pattern getter for the instance of transaction manager
     *
     * @return The transaction manager instance
     */
    public static TransactionManager getInstance () {
        if (instance == null) {
            instance = new TransactionManager(null);
        }
        return instance;
    }

    public Transaction getTransaction (int index) {
        return transactions.get(index);
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
        save();
    }

    /**
     * delete all related currency transaction in the main transaction list
     *
     * @param currency currency to be delete
     */
    public void deleteCurrencyTransaction (Currency currency) {
        Iterator<Transaction> itr = transactions.iterator();
        while (itr.hasNext()) {
            Transaction transaction = itr.next();
            if (transaction.getCurrency() == currency) {
                itr.remove();
            }
        }
        save();
    }

    /**
     * Method called on starting the program to store the transactions retrieved from storage
     * into the transaction manager
     *
     * @param transactions the arraylist of transactions retrieved from storage
     */
    public void populateTransactions (ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        // we do not save here as the only time this is called is when we load from file
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
        Currency currency = Currency.valueOf(currencyString.toUpperCase());
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

        // This is necessary as LocalDate.parse does not throw an error for months that shouldn't have all 31 days
        if (!isDateValid(date, dateString)) {
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

    /**
     * Method that performs validation on the LocalDate variable
     * It ensures that the date doesn't have a day that is more than the number of days in its month
     * The original dateString is required since LocalDate parse
     * automatically reduces the day to the highest correct value if it exceeds the number of days in its month
     *
     * @param date       The date to be validated
     * @param dateString The original dateString before being parsed
     * @return The boolean representing whether the date is valid
     */
    private boolean isDateValid (LocalDate date, String dateString) {
        Month month = date.getMonth();
        try {
            int day = Integer.parseInt(dateString.substring(0, 2));
            int daysInMonth = DateConstants.DAYS_IN_MONTH.get(month);

            // Special case if isLeapYear and is in february, needed as DAYS_IN_MONTH assumes it is not a leap year
            if (month == Month.FEBRUARY && date.isLeapYear()) {
                daysInMonth = 29;
            }

            return day <= daysInMonth;
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Invalid dateString during further validation on date in TransactionManager");
            assert false;
            return false;
        }
    }

    /**
     * Method that returns the number of transactions
     *
     * @return The number of transactions in the list
     */
    public Integer getSize () {
        return transactions.size();
    }

    /**
     * Method that sets the store for the TransactionManager singleton
     *
     * @param store The class that implements the StoreInterface
     */
    public void setStore (StoreInterface store) {
        this.store = store;
    }

    /**
     * Saves all the transactions to the store, should be called after every command that modifies the account list
     * or accounts within it
     */
    public void save () {
        logger.log(Level.FINE, "Saving transactions to store");
        if (store == null) {
            logger.log(Level.INFO, "No store set, not saving transactions");
            return;
        }
        try {
            store.saveTransactionsToStore(transactions);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving transactions to store", e);
            return;
        }
        logger.log(Level.FINE, "done saving");
    }

}
