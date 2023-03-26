package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.TransactionManager;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.constants.Message;
import seedu.duke.constants.TransactionFlag;
import seedu.duke.exceptions.*;
import seedu.duke.ui.Ui;

/**
 * Command to print the transactions performed by the user
 */
public class TransactionCommand extends Command {
    public TransactionCommand (String input) {
        super(false, input);
    }

    /**
     * Prints the transactions made by the user
     * Optional parameters allow the user to search by date, month, description or currency
     *
     * @param ui       The instance of the UI class
     * @param accounts The instance of the AccountList class
     */
    @Override
    public void execute (Ui ui, AccountList accounts) {
        String[] args = input.split(" ");
        String transactionsString;
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

    private static void printAllTransactions (Ui ui) throws NoTransactionsRecordedException {
        TransactionManager transactionManager = TransactionManager.getInstance();
        String transactionsString;
        transactionsString = transactionManager.getAllTransactionsString();
        ui.printMessage(Message.SHOW_ALL_TRANSACTIONS_HEADER.getMessage());
        ui.printMessage(transactionsString);
        return;
    }

    /**
     * Method that searches a string for a substring
     * Upon finding that substring, returns the rest of the string after that substring,
     * excluding the searched substring
     *
     * @param stringToSearch The string to search within
     * @param substring      The substring to search for
     * @return The rest of the input string after and excluding the input substring
     */
    private String findSubstringToEndAfterASubstring (String stringToSearch, String substring) {
        int substringStartIndex = stringToSearch.indexOf(substring);
        return stringToSearch.substring(stringToSearch.indexOf(" ", substringStartIndex)).trim();
    }

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
            String description = findSubstringToEndAfterASubstring(input, TransactionFlag.TRANSACTION_DESC_FLAG);
            transactionsString = transactionManager.getAllTransactionsOfDescription(description);
            ui.printMessage(Message.SHOW_TRANSACTIONS_OF_DESC_HEADER.getMessage() + description + ":");
            ui.printMessage(transactionsString);
            break;
        case TransactionFlag.TRANSACTION_MONTH_FLAG:
            if (args.length < 3) {
                throw new InvalidSearchTransactionByMonthException();
            }
            break;
        case TransactionFlag.TRANSACTION_DATE_FLAG:
            if (args.length < 3) {
                throw new InvalidSearchTransactionByDateException();
            }
            String dateString = findSubstringToEndAfterASubstring(input, TransactionFlag.TRANSACTION_DATE_FLAG);
            transactionsString = transactionManager.getAllTransactionsOfDate(dateString);
            ui.printMessage(Message.SHOW_TRANSACTIONS_OF_DATE_HEADER.getMessage() + dateString + ":");
            ui.printMessage(transactionsString);
            break;
        case TransactionFlag.TRANSACTION_CURRENCY_FLAG:
            if (args.length < 3) {
                throw new InvalidSearchTransactionByCurrencyException();
            }
            String currencyString =
                    findSubstringToEndAfterASubstring(input, TransactionFlag.TRANSACTION_CURRENCY_FLAG);
            transactionsString = transactionManager.getAllTransactionsOfCurrency(currencyString);
            ui.printMessage(Message.SHOW_TRANSACTIONS_OF_CURRENCY_HEADER.getMessage() + currencyString + ":");
            ui.printMessage(transactionsString);
            break;
        default:
            throw new InvalidTransactionFlagException();
        }
    }

}
