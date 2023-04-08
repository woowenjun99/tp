package com.moneymoover.commands;

import com.moneymoover.Currency;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.constants.Message;
import com.moneymoover.exceptions.AccountNotEmptyException;
import com.moneymoover.AccountList;

import com.moneymoover.TransactionManager;
import com.moneymoover.exceptions.InvalidDeleteAccountCommandException;
import com.moneymoover.exceptions.NoAccountException;
import com.moneymoover.ui.Ui;


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
        return Currency.valueOf(currencyString.toUpperCase());
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
            transactions.deleteCurrencyTransaction(this.currency);
            accounts.save();
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

