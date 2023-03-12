package seedu.duke.commands;

public abstract class Command {
    private final boolean isExit;

    public Command(boolean isExit) {
        this.isExit = isExit;
    }

    /**
     * Executes the command implemented by the subclass
     */
    public abstract void execute();

    public boolean isExit() {
        return isExit;
    }
}