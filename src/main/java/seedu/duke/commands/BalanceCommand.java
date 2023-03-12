package seedu.duke.commands;

import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.exceptions.InvalidBalanceCommand;
import seedu.duke.exceptions.NoAccountException;

import java.util.HashMap;

public class BalanceCommand extends Command {
    private AccountList accounts = AccountList.getInstance();
    private String command;
    private final String ALL = "ALL";

    public BalanceCommand(String command) {
        super(false);
        this.command = command.trim();
    }

    private String processCommand() throws InvalidBalanceCommand {
        String[] words = command.split(" ");
        switch (words.length) {
        case 1:
            return ALL;
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
        if (currencyString == ALL) {
            return accounts.getAllBalance();
        }
        Currency currency = convertStringToEnum(currencyString);
        return accounts.getBalance(currency);
    }

    private void printCurrencies(HashMap<Currency, Account> currencies) {
        System.out.println("Here are the balances that you have requested:");
        currencies.forEach((currency, account) -> {
            System.out.printf("%s: %f\n", currency.name(), account.getBalance());
        });
    }

    @Override
    public void execute() {
        try {
            String currencyString = processCommand();
            HashMap<Currency, Account> currencies = getBalance(currencyString);
            printCurrencies(currencies);
        } catch (InvalidBalanceCommand e) {
            System.out.println(ErrorMessage.MORE_THAN_ONE_CURRENCY_PROVIDED);
        } catch (IllegalArgumentException e) {
            System.out.println(ErrorMessage.INVALID_CURRENCY);
        } catch (NoAccountException e) {
            System.out.println(ErrorMessage.NO_SUCH_ACCOUNT);
        }
    }
}
