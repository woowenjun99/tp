package seedu.duke.commands;

public class ExitCommand extends Command{
    public ExitCommand() {
        super(true);
    }

    @Override
    public void execute() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
