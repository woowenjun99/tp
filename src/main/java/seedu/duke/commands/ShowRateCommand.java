package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.ui.Ui;
import seedu.duke.Forex;
import seedu.duke.Account;
import seedu.duke.exceptions.InvalidNumberException;
import seedu.duke.exceptions.InvalidShowrateArgumentException;
import seedu.duke.constants.ErrorMessage;

/**
 * Command to print the exchange rate between two currencies
 */
public class ShowRateCommand extends Command {
    private Currency from;
    private Currency to;
    public ShowRateCommand(String input) {
        super(false, input);
    }

    /**
    * Prints the exchange rate between two currencies.
    * If no value is given, shows 1:X exchange rates, otherwise
    * prints the value exchanged to the target currency.
    */
    @Override
    public void execute(Ui ui, AccountList accounts) {
        try {
            float val;
            String[] args = input.split(" ");
            if (args.length < 3 || args.length > 4) {
                throw new InvalidShowrateArgumentException();
            }
            Currency from = Currency.valueOf(args[1]);
            Currency to = Currency.valueOf(args[2]);
            if (args.length == 4) {
                val = Float.parseFloat(args[3]);
            } else {
                val = 1;
            }
            Forex reverse = new Forex(to, from);
            Forex instance = new Forex(from, to);
            printRate(instance, val);
            if (val == 1) {
                printRate(reverse, val);
            }
        } catch (IllegalArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        } catch (InvalidNumberException e) {
            ui.printMessage(ErrorMessage.NEGATIVE_NUMBER);
        } catch (InvalidShowrateArgumentException e) {
            ui.printMessage(ErrorMessage.SHOWRATE_SYNTAX);
        }
    }

    /**
    * Prints the exchange rate between two currencies with a specified amount
    * @param temp a Forex object containing the exchange rate
    * @param instance a float of the amount to be converted on the exchange rate
    * @throws InvalidNumberException if the amount is negative
    */
    private void printRate(Forex instance, float amt) throws InvalidNumberException {
        if (amt < 0) {
            throw new InvalidNumberException();
        }
        String from = Account.currencyToString(instance.getInitial());
        String to = Account.currencyToString(instance.getTarget());
        System.out.println(amt  + " " + from + " = " + instance.convert(amt) + " " + to);
    }
}
