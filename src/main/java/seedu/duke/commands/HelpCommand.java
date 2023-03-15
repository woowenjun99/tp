package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.constants.Message;
import seedu.duke.ui.Ui;

public class HelpCommand extends Command {
    public HelpCommand (String input) {
        super(false, input);
    }

    @Override
    public void execute (Ui ui, AccountList accounts) {
        ui.printMessage(Message.HELP.getMessage());
    }
}
