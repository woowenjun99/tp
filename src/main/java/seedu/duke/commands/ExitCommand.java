package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.ui.Ui;

/**
 * Command to exit the program
 */
public class ExitCommand extends Command{
    public ExitCommand(AccountList account) {
        super(true, "", account);
    }

    @Override
    public void execute(Ui ui) {
        ui.printFarewell();
    }
}
