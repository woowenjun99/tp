package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.constants.Message;
import seedu.duke.exceptions.AccountNotEmptyException;
import seedu.duke.exceptions.InvalidDeleteAccountCommandException;
import seedu.duke.exceptions.NoAccountException;
import seedu.duke.ui.Ui;

import java.util.ArrayList;

/**
 * This class is used to deal with the deleteAccountCommand.
 */
public class DeleteAccountCommand extends Command {
    private Currency currency;
    private TransactionManager transactions = TransactionManager.getInstance();

    /**
     * @param input The user input including the command.
     */
    public DeleteAccountCommand (String input) {
        super(false, input);
    }

    private Currency getCurrency (String currencyString) {
        return Currency.valueOf(currencyString);
    }

    private void processCommand () throws InvalidDeleteAccountCommandException {
        String[] words = super.input.split(" ");
        // Format: [Command, CURRENCY]
        boolean isValidCommand = words.length == 2;
        if (!isValidCommand) {
            throw new InvalidDeleteAccountCommandException();
        }
        this.currency = getCurrency(words[1]);

    }

    private void printSuccess (Ui ui) {
        ui.printf(Message.SUCCESSFUL_DELETE_ACCOUNT_COMMAND.getMessage(), this.currency.name());
    }

    /**
     * delete all related currency transaction in the main transaction list
     *
     * @param currency currency to be delete
     */

    private void deleteCurrencyTransaction (Currency currency) {
        ArrayList<Transaction> transactionList = transactions.getTransactionList();
        ArrayList<Transaction> transactionListToBeDeleted = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (transaction.getCurrency() == currency) {
                transactionListToBeDeleted.add(transaction);
            }
        }
        for (Transaction transactionToBeDeleted : transactionListToBeDeleted) {
            transactions.deleteTransaction(transactionToBeDeleted);
        }
    }

    /**
     * Delete currency account if found and print a success message.
     *
     * @param ui The instance of the UI class.
     */
    @Override
    public void execute (Ui ui, AccountList accounts) {
        try {
            processCommand();
            accounts.deleteAccount(this.currency);
            printSuccess(ui);
            deleteCurrencyTransaction(this.currency);
        } catch (InvalidDeleteAccountCommandException e) {
            ui.printMessage(ErrorMessage.INVALID_DELETE_ACCOUNT_COMMAND);
        } catch (NumberFormatException e) {
            ui.printMessage(ErrorMessage.INVALID_NUMERICAL_AMOUNT);
        } catch (IllegalArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        } catch (NoAccountException e) {
            ui.printMessage(ErrorMessage.NO_SUCH_ACCOUNT);
        } catch (AccountNotEmptyException e) {
            ui.printMessage(ErrorMessage.ACCOUNT_NOT_EMPTY);
        }
    }
}

