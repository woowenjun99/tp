package seedu.duke.commands;

import seedu.duke.Currency;

public class ShowRateCommand extends Command {
    private Currency from;
    private Currency to;
    public ShowRateCommand(String from, String to) {
        super(false);
        this.from = Currency.valueOf(from);
        this.to = Currency.valueOf(to);
    }

    @Override
    public void execute() {
        // TODO: fully implement
        System.out.println("The current exchange rate is 1.0 SGD = 1.0 USD");
    }
}
