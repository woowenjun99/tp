package seedu.duke;

import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Forex;
import seedu.duke.Currency;
import seedu.duke.commands.Command;
import seedu.duke.ui.Ui;
import seedu.duke.exceptions.*;
import seedu.duke.constants.ErrorMessage;

public class ExchangeCommand extends Command {

    public ExchangeCommand (String input) {
        super(false, input);
    }

    @Override
    public void execute(Ui ui) {
        // Throw an error if accounts for either currency doesn't exist, or initial doesn't have enough
        try {
            ui.printSpacer();

            // Parse input
            float amount = parseAmount();
            Forex exchangeRate = formatInput();
            System.out.println(exchangeRate);

            // Retrieve and edit accounts
            Account oldAcc = AccountList.getAccount(exchangeRate.getInitial());
            oldAcc.updateBalance(amount, "subtract");
            Account newAcc = AccountList.getAccount(exchangeRate.getTarget());
            newAcc.updateBalance(exchangeRate.convert(amount), "add");

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

    public Forex formatInput() throws InvalidExchangeArgumentException {
        String[] splitInput = input.trim().split(" ");
        if (splitInput.length != 4) {
            throw new InvalidExchangeArgumentException();
        }
        Currency initial  = Currency.valueOf(splitInput[2]);
        Currency target = Currency.valueOf(splitInput[3]);
        return new Forex(initial, target);
    }

    public float parseAmount() throws InvalidNumberException {
        try {
            String amount = input.trim().split(" ")[4];
            float amountAsFloat = Float.parseFloat(amount);
            return amountAsFloat;
        } catch (Exception e) {
            throw new InvalidNumberException();
        }
    }
}