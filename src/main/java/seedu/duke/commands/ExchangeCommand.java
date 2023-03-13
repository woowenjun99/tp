package seedu.duke.commands;

import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Forex;
import seedu.duke.Currency;
import seedu.duke.commands.Command;
import seedu.duke.ui.Ui;
import seedu.duke.exceptions.NoAccountException;
import seedu.duke.exceptions.InvalidExchangeArgumentException;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.exceptions.NotEnoughInAccountException;
import seedu.duke.constants.ErrorMessage;

public class ExchangeCommand extends Command {

    /**
     * Constructor for exchange command
     * @param input input for exchange command
     */
    public ExchangeCommand (String input) {
        super(false, input);
    }

    /**
     * Converts the requested amount and changes the account balances
     */
    @Override
    public void execute(Ui ui, AccountList acclist) {
        try {
            // Parse input
            Forex exchangeRate = formatInput();
            float amount = parseAmount();

            // Retrieve and edit accounts
            Account oldAcc = acclist.getAccount(exchangeRate.getInitial());
            oldAcc.updateBalance(amount, "subtract");
            Account newAcc = acclist.getAccount(exchangeRate.getTarget());
            newAcc.updateBalance(exchangeRate.convert(amount), "add");
            System.out.println(exchangeRate);
            System.out.println("Balance of initial account --> " + oldAcc);
            System.out.println("Balance of target account --> " + newAcc);

        // Exception handling
        } catch (NoAccountException e) {
            System.out.println(ErrorMessage.NO_SUCH_ACCOUNT);
        } catch (IllegalArgumentException e) {
            System.out.println(ErrorMessage.INVALID_CURRENCY);
        } catch (InvalidExchangeArgumentException e) {
            System.out.println(ErrorMessage.INVALID_EXCHANGE_ARGUMENT);
        } catch (InvalidNumberException e) {
            System.out.println(ErrorMessage.INVALID_NUMBER);
        } catch (NotEnoughInAccountException e) {
            System.out.println(ErrorMessage.NOT_ENOUGH_IN_ACCOUNT);
        }
    }

    /**
     * Converts input into Forex object for use in execution
     * @return Forex object with intial and target currencies
     * @throws IllegalArgumentException if the currencies are not supported
     * @throws InvalidExchangeArgumentException if arguments are incorrect
     */
    public Forex formatInput() throws InvalidExchangeArgumentException {
        String[] splitInput = input.trim().split(" ");
        if (splitInput.length != 4) {
            throw new InvalidExchangeArgumentException();
        }
        Currency initial  = Currency.valueOf(splitInput[1]);
        Currency target = Currency.valueOf(splitInput[2]);
        return new Forex(initial, target);
    }

    /**
     * Retrieves the amount to be converted from the input
     * @throws NullPointerException if the amount is null
     * @throws NumberFormatException if the amount is non-numeric
     * @return float representing amount to be converted
     */
    public float parseAmount() throws InvalidNumberException {
        try {
            String amount = input.trim().split(" ")[3];
            float amountAsFloat = Float.parseFloat(amount);
            if (amountAsFloat <= 0) {
                throw new InvalidNumberException();
            }
            return amountAsFloat;
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
    }
}
