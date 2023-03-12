package seedu.duke;

import seedu.duke.commands.Command;
import seedu.duke.ui.Ui;
import seedu.duke.parser.Parser;

public class Duke {

    private static Ui ui;

    /**
     * Runs the main input loop until the exit command is called
     */
    public static void run(){
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.getUserInput();
                ui.printSpacer();
                Command c = Parser.parseInput(fullCommand);
                c.execute(ui);
                isExit = c.isExit();
            } catch (IllegalArgumentException e) {
                ui.printMessage(e.getMessage());
            } finally {
                ui.printSpacer();
            }

        }
    }
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        ui = new Ui();
        ui.printGreeting();
        ui.printSpacer();
        run();
    }
}
