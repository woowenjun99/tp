package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.ui.Ui;

public abstract class Command {
    protected final boolean isExit;
    protected final String input;
    protected final AccountList account;

    public Command(boolean isExit, String input, AccountList account){
        this.isExit = isExit;
        this.input = input;
        this.account = account;
    }

    /**
     * Executes the command implemented by the subclass
     */

    public abstract void execute(Ui ui);

    public boolean isExit () {
        return isExit;
    }
}
