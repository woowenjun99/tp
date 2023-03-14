package seedu.duke.commands;

import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.constants.Message;
import seedu.duke.exceptions.InvalidBalanceCommandException;
import seedu.duke.exceptions.NoAccountException;
import seedu.duke.ui.Ui;

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
    public BalanceCommand(String command) {
        super(false, command);
        this.command = command.trim();
    }

    private String processCommand() throws InvalidBalanceCommandException {
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

    private Currency convertStringToEnum(String currency) throws IllegalArgumentException {
        return Currency.valueOf(currency);
    }

    private ArrayList<Account> getAccounts(String currencyString, AccountList accounts)
            throws NoAccountException {
        ArrayList<Account> accountArrayList;
        if (currencyString.equals(ALL)) {
            // Return all accounts
            accountArrayList = accounts.getAllAccounts();
        }
        else{
            Currency currency = convertStringToEnum(currencyString);
            accountArrayList = new ArrayList<>();
            accountArrayList.add(accounts.getAccount(currency));
        }
        return accountArrayList;
    }

    private void printCurrencies(ArrayList<Account> accountArrayList, Ui ui) {
        ui.printMessage(Message.BALANCE.getMessage());
        for(Account account : accountArrayList){
            ui.printMessage(account.toString());
        }
    }

    /**
     * Gets the currencies from the AccountList and displays it onto the screen.
     */
    @Override
    public void execute(Ui ui, AccountList account) {
        try {
            String currencyString = processCommand();
            ArrayList<Account> accountArrayList = getAccounts(currencyString, account);
            printCurrencies(accountArrayList, ui);
        } catch (InvalidBalanceCommandException e) {
            System.out.println(ErrorMessage.MORE_THAN_ONE_CURRENCY_PROVIDED);
        } catch (IllegalArgumentException e) {
            System.out.println(ErrorMessage.INVALID_CURRENCY);
        } catch (NoAccountException e) {
            System.out.println(ErrorMessage.NO_SUCH_ACCOUNT);
        }
    }
}
