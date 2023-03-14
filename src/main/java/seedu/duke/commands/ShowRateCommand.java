package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.ui.Ui;
import seedu.duke.Forex;
import seedu.duke.Account;

/**
 * Command to print the exchange rate between two currencies
 */
public class ShowRateCommand extends Command {
    private Currency from;
    private Currency to;
    public ShowRateCommand(String input) {
        super(false, input);
    }

    @Override
    public void execute(Ui ui, AccountList account) {
        System.out.println(input);
        try {
            Forex.populateRates(); 
            float val;
            String[] args = input.split(" ");
            Currency from = Currency.valueOf(args[1]);
            Currency to = Currency.valueOf(args[2]);
            if (args.length == 4) {
                val = Float.parseFloat(args[3]);
            } else {
                val = 1;
            }
            Forex reverse = new Forex(to, from);
            Forex temp = new Forex(from, to);
            printRate(temp, val);
            if (val == 1) {
                printRate(reverse, val);
            }
        } catch (IllegalArgumentException e) {
            ui.printInvalidShowRate();
        }
    }

    private void printRate(Forex temp, float amt) {
        String from = Account.currencyToString(temp.getInitial());
        String to = Account.currencyToString(temp.getTarget());
        System.out.println(amt  + " " + from + " = " + temp.convert(amt) + " " + to);
    }
}
