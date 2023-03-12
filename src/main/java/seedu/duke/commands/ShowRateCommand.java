package seedu.duke.commands;

import seedu.duke.Currency;
import seedu.duke.ui.Ui;

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
    public void execute(Ui ui) {
        // TODO: fully implement
        try {
            String[] args = input.split(" ");
            Currency from = Currency.valueOf(args[0]);
            Currency to = Currency.valueOf(args[1]);
        } catch (IllegalArgumentException e) {
            ui.printInvalidShowRate();
        }
    }
}
