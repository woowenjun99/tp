package com.moneymoover.commands;

import com.moneymoover.Currency;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.constants.Message;
import com.moneymoover.exceptions.AccountAlreadyExistsException;
import com.moneymoover.AccountList;
import com.moneymoover.ui.Ui;

public class CreateAccountCommand extends Command {

    /**
     * @param input The full user input according to the command word {@code create-account SGD}
     */
    public CreateAccountCommand (String input) {
        super(false, input);
    }

    /**
     * Adds an account of the currency in user input to the account list
     */
    @Override
    public void execute (Ui ui, AccountList accounts) {
        String[] userInputs = input.split(" ");
        if (userInputs.length != 2) {
            ui.printMessage(ErrorMessage.INVALID_CREATE_ACCOUNT_COMMAND);
            return;
        }
        String currencyString = userInputs[1];
        try {
            Currency currency = Currency.valueOf(currencyString.toUpperCase());
            accounts.addAccount(currency, 0.0f);
            ui.printf(Message.SUCCESSFUL_CREATE_ACCOUNT_COMMAND.getMessage(), currency);
            accounts.save();
        } catch (IllegalArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        } catch (AccountAlreadyExistsException e) {
            ui.printMessage(ErrorMessage.ACCOUNT_ALREADY_EXISTS);
        }
    }
}
