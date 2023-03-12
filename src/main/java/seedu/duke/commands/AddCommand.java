package seedu.duke.commands;

import seedu.duke.Currency;
import seedu.duke.exceptions.InvalidAddCommandException;
import seedu.duke.ui.Ui;

public class AddCommand extends Command {
    public AddCommand(String input) {
        super(false, input);
    }

    private Currency getCurrency(String currencyString) throws IllegalArgumentException {
        return Currency.valueOf(currencyString);
    }

    private void processCommand() throws InvalidAddCommandException {
        String[] words = input.split(" ");
        // Format: [Command, CURRENCY, AMOUNT]
        boolean isValidCommand = words.length == 3;
        if (!isValidCommand) {
            throw new InvalidAddCommandException();
        }
    }

    @Override
    public void execute(Ui ui) {

    }
}
