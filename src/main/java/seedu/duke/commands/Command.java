package seedu.duke.commands;

import seedu.duke.ui.Ui;
import seedu.duke.AccountList;

public abstract class Command {
    protected final boolean isExit;
    protected final String input;

    public Command(boolean isExit, String input){
        this.isExit = isExit;
        this.input = input;
    }

    /**
     * Executes the command implemented by the subclass
     */

    public abstract void execute(Ui ui, AccountList account);

    public boolean isExit () {
        return isExit;
    }
}
