package seedu.duke.commands;

import seedu.duke.ui.Ui;
import seedu.duke.AccountList;

/**
 * Command to exit the program
 */
public class ExitCommand extends Command{
    public ExitCommand() {
        super(true, "");
    }

    @Override
    public void execute(Ui ui, AccountList account) {
        ui.printFarewell();
    }
}
