package com.moneymoover.commands;

import com.moneymoover.AccountList;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.constants.Message;
import com.moneymoover.constants.TransactionFlag;
import com.moneymoover.TransactionManager;
import com.moneymoover.exceptions.InvalidSearchTransactionByCurrencyException;
import com.moneymoover.exceptions.InvalidSearchTransactionByDateException;
import com.moneymoover.exceptions.InvalidSearchTransactionByDescException;
import com.moneymoover.exceptions.InvalidSearchTransactionByMonthException;
import com.moneymoover.exceptions.InvalidTransactionFlagException;
import com.moneymoover.exceptions.NoTransactionsOfSearchParameterException;
import com.moneymoover.exceptions.NoTransactionsRecordedException;
import com.moneymoover.ui.Ui;

/**
 * Command to print the transactions performed by the user
 */
public class TransactionCommand extends Command {
    public TransactionCommand (String input) {
        super(false, input);
    }

    /**
     * Prints the transactions made by the user
     * Optional flags allow the user to search by date, month, description or currency
     *
     * @param ui       The instance of the UI class
     * @param accounts The instance of the AccountList class
     */
    @Override
    public void execute (Ui ui, AccountList accounts) {
        String[] args = input.split(" ");
        try {
            if (args.length == 1) {
                // Print all transactions
                printAllTransactions(ui);
                return;
            }
            // At this point there is an additional flag in user input signalling a search parameter
            String flag = args[1];
            printTransactionsByFlag(ui, args, flag);
        } catch (NoTransactionsRecordedException e) {
            ui.printMessage(ErrorMessage.NO_TRANSACTIONS_RECORDED);
        } catch (InvalidSearchTransactionByDescException e) {
            ui.printMessage(ErrorMessage.INVALID_TRANSACTIONS_OF_DESC);
        } catch (InvalidSearchTransactionByCurrencyException e) {
            ui.printMessage(ErrorMessage.INVALID_TRANSACTIONS_OF_CURRENCY);
        } catch (InvalidSearchTransactionByDateException e) {
            ui.printMessage(ErrorMessage.INVALID_TRANSACTIONS_OF_DATE);
        } catch (InvalidSearchTransactionByMonthException e) {
            ui.printMessage(ErrorMessage.INVALID_TRANSACTIONS_OF_MONTH);
        } catch (NoTransactionsOfSearchParameterException e) {
            ui.printMessage(ErrorMessage.NO_TRANSACTIONS_FOUND);
        } catch (InvalidTransactionFlagException e) {
            ui.printMessage(ErrorMessage.INVALID_TRANSACTION_FLAG);
        } catch (IllegalArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        }
    }

    /**
     * Method that prints all transactions in the transaction list
     *
     * @param ui The instance of the UI class
     * @throws NoTransactionsRecordedException if there are no transactions in the list
     */
    private static void printAllTransactions (Ui ui) throws NoTransactionsRecordedException {
        TransactionManager transactionManager = TransactionManager.getInstance();
        String transactionsString;
        transactionsString = transactionManager.getAllTransactionsString();
        ui.printMessage(Message.SHOW_ALL_TRANSACTIONS_HEADER.getMessage());
        ui.printMessage(transactionsString);
    }

    /**
     * Method that searches a string for a substring
     * Upon finding that substring, returns the rest of the string after that substring,
     * excluding the searched substring
     *
     * @param stringToSearch The string to search within
     * @param substring      The substring to search for
     * @return The rest of the input string after and excluding the input substring, null if substring is not found
     */
    private String substringAfterKeyword (String stringToSearch, String substring) {
        int substringStartIndex = stringToSearch.indexOf(substring);
        if (substringStartIndex == -1) {
            return null;
        }
        return stringToSearch.substring(stringToSearch.indexOf(" ", substringStartIndex)).trim();
    }

    /**
     * Method that prints transactions within some search parameters
     * The search parameters are determined by the flag passed in
     *
     * @param ui   The instance of the UI class
     * @param args The String array representing the user input split by space
     * @param flag The String flag determining which search parameters are used
     * @throws InvalidSearchTransactionByDescException     if the description given to search by is invalid
     * @throws NoTransactionsRecordedException             if there are no transactions in the list
     * @throws InvalidSearchTransactionByMonthException    if the month given to search by is invalid
     * @throws InvalidSearchTransactionByDateException     if the date given to search by is invalid
     * @throws InvalidSearchTransactionByCurrencyException if the currency given to search by is invalid
     * @throws NoTransactionsOfSearchParameterException    if there are no transactions matching search parameters
     * @throws InvalidTransactionFlagException             if the flag given is invalid
     * @throws IllegalArgumentException                    if the currency given to search by is invalid
     */
    private void printTransactionsByFlag (Ui ui, String[] args, String flag) throws
            InvalidSearchTransactionByDescException, NoTransactionsRecordedException,
            InvalidSearchTransactionByMonthException, InvalidSearchTransactionByDateException,
            InvalidSearchTransactionByCurrencyException, NoTransactionsOfSearchParameterException,
            InvalidTransactionFlagException, IllegalArgumentException {
        TransactionManager transactionManager = TransactionManager.getInstance();
        String transactionsString;
        switch (flag) {
        case TransactionFlag.TRANSACTION_DESC_FLAG:
            if (args.length < 3) {
                throw new InvalidSearchTransactionByDescException();
            }
            String description = substringAfterKeyword(input, TransactionFlag.TRANSACTION_DESC_FLAG);
            transactionsString = transactionManager.getAllTransactionsOfDescription(description);
            ui.printMessage(Message.SHOW_TRANSACTIONS_OF_DESC_HEADER.getMessage() + description + ":");
            ui.printMessage(transactionsString);
            break;
        case TransactionFlag.TRANSACTION_MONTH_FLAG:
            if (args.length < 3) {
                throw new InvalidSearchTransactionByMonthException();
            }
            String monthString = substringAfterKeyword(input, TransactionFlag.TRANSACTION_MONTH_FLAG);
            transactionsString = transactionManager.getAllTransactionsOfMonth(monthString);
            ui.printMessage(Message.SHOW_TRANSACTIONS_OF_MONTH_HEADER.getMessage() + monthString + ":");
            ui.printMessage(transactionsString);
            break;
        case TransactionFlag.TRANSACTION_DATE_FLAG:
            if (args.length < 3) {
                throw new InvalidSearchTransactionByDateException();
            }
            String dateString = substringAfterKeyword(input, TransactionFlag.TRANSACTION_DATE_FLAG);
            transactionsString = transactionManager.getAllTransactionsOfDate(dateString);
            ui.printMessage(Message.SHOW_TRANSACTIONS_OF_DATE_HEADER.getMessage() + dateString + ":");
            ui.printMessage(transactionsString);
            break;
        case TransactionFlag.TRANSACTION_CURRENCY_FLAG:
            if (args.length < 3) {
                throw new InvalidSearchTransactionByCurrencyException();
            }
            String currencyString =
                    substringAfterKeyword(input, TransactionFlag.TRANSACTION_CURRENCY_FLAG);
            transactionsString = transactionManager.getAllTransactionsOfCurrency(currencyString);
            ui.printMessage(Message.SHOW_TRANSACTIONS_OF_CURRENCY_HEADER.getMessage() + currencyString + ":");
            ui.printMessage(transactionsString);
            break;
        default:
            throw new InvalidTransactionFlagException();
        }
    }

}
