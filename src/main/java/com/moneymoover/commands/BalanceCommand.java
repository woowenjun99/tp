package com.moneymoover.commands;

import com.moneymoover.Account;
import com.moneymoover.AccountList;
import com.moneymoover.Currency;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.constants.Message;
import com.moneymoover.exceptions.InvalidBalanceCommandException;
import com.moneymoover.exceptions.NoAccountException;
import com.moneymoover.ui.Ui;

import java.util.ArrayList;

/**
 * BalanceCommand is a subclass of the Command class that is used to
 * handle the getBalance command by the user.
 */
public class BalanceCommand extends Command {

    private static final String ALL = "ALL";
    private final String command;

    /**
     * @param command The full user input including the command word {@code balance SGD}.
     */
    public BalanceCommand (String command) {
        super(false, command);
        this.command = command.trim();
    }

    private String processCommand () throws InvalidBalanceCommandException {
        String[] words = command.split(" ");
        switch (words.length) {
        case 1:
            return ALL;
        case 2:
            return words[1];
        default:
            throw new InvalidBalanceCommandException();
        }
    }

    private Currency convertStringToEnum (String currency) throws IllegalArgumentException {
        return Currency.valueOf(currency.toUpperCase());
    }

    private ArrayList<Account> getAccounts (String currencyString, AccountList accounts)
            throws NoAccountException {
        if (currencyString.equals(ALL)) {
            // Return all accounts
            return accounts.getAllAccounts();
        }

        ArrayList<Account> accountArrayList;
        Currency currency = convertStringToEnum(currencyString);
        accountArrayList = new ArrayList<>();
        accountArrayList.add(accounts.getAccount(currency));
        return accountArrayList;
    }

    private void printCurrencies (ArrayList<Account> accountArrayList, Ui ui) {
        if (accountArrayList.isEmpty()) {
            ui.printMessage(Message.NO_ACCOUNT_TO_SHOW.getMessage());
            return;
        }
        ui.printMessage(Message.BALANCE.getMessage());
        for (Account account : accountArrayList) {
            ui.printMessage(account.toString());
        }
    }

    /**
     * Gets the currencies from the AccountList and displays it onto the screen.
     *
     * @param ui       The ui instance
     * @param accounts The global accounts database
     */
    @Override
    public void execute (Ui ui, AccountList accounts) {
        try {
            String currencyString = processCommand();
            ArrayList<Account> accountArrayList = getAccounts(currencyString, accounts);
            printCurrencies(accountArrayList, ui);
        } catch (InvalidBalanceCommandException e) {
            ui.printMessage(ErrorMessage.MORE_THAN_ONE_CURRENCY_PROVIDED);
        } catch (IllegalArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        } catch (NoAccountException e) {
            ui.printMessage(ErrorMessage.NO_SUCH_ACCOUNT);
        }
    }
}
