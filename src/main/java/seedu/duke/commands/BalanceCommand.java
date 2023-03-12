package seedu.duke.commands;

import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.exceptions.InvalidBalanceCommand;
import seedu.duke.exceptions.NoAccountException;

import java.util.HashMap;

public class BalanceCommand extends Command {
    private AccountList accounts = AccountList.getInstance();
    private String command;

    BalanceCommand(String command) {
        super(false);
        this.command = command.trim();
    }

    private String processCommand() throws InvalidBalanceCommand {
        String[] words = command.split(" ");
        switch (words.length) {
        case 1:
            return "ALL";
        case 2:
            return words[1];
        default:
            throw new InvalidBalanceCommand();
        }
    }

    private Currency convertStringToEnum(String currency) throws IllegalArgumentException {
        return Currency.valueOf(currency);
    }

    private HashMap<Currency, Account> getBalance(String currencyString) throws NoAccountException {
        if (currencyString == "ALL") {
            return accounts.getAllBalance();
        }
        Currency currency = convertStringToEnum(currencyString);
        return accounts.getBalance(currency);
    }

    @Override
    public void execute() {
        try {
            String currencyString = processCommand();
            HashMap<Currency, Account> currencies = getBalance(currencyString);
        } catch (InvalidBalanceCommand e) {
            System.out.println("Please do not provide more than one currency.");
        } catch (IllegalArgumentException e) {
            System.out.println("An invalid currency has been provided.");
        } catch (NoAccountException e) {
            System.out.println("You do not have an account for the currency.");
        }
    }
}
