package seedu.duke.commands;

import seedu.duke.Currency;
import seedu.duke.exceptions.InvalidAddCommandException;
import seedu.duke.ui.Ui;

public class AddCommand extends Command {
    private Currency currency;
    private int amount;

    public AddCommand(String input) {
        super(false, input);
    }

    private Currency getCurrency(String currencyString) {
        return Currency.valueOf(currencyString);
    }

    private void processCommand() throws InvalidAddCommandException {
        String[] words = input.split(" ");
        // Format: [Command, CURRENCY, AMOUNT]
        boolean isValidCommand = words.length == 3;
        if (!isValidCommand) {
            throw new InvalidAddCommandException();
        }
        this.currency = getCurrency(words[1]);
        this.amount = Integer.parseInt(words[2]);
    }

    @Override
    public void execute(Ui ui) {

    }
}
