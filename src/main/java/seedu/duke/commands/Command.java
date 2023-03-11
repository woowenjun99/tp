package seedu.duke.commands;

public abstract class Command {
    private final boolean isExit;

    public Command(boolean isExit){
        this.isExit = isExit;
    }
    public abstract void execute();
}
