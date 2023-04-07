package seedu.duke;

import seedu.duke.commands.Command;
import seedu.duke.parser.Parser;
import seedu.duke.storage.Store;
import seedu.duke.ui.Ui;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Duke {

    private static Ui ui;
    private static Store store;
    private static AccountList accounts;
    private static final Logger logger = Logger.getLogger("logger");


    /**
     * Runs the main input loop until the exit command is called
     */
    public static void run () {
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.getUserInput();
                ui.printSpacer();
                Command c = Parser.parseInput(fullCommand);
                c.execute(ui, accounts);
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
    public static void main (String[] args) {
        ui = new Ui();
        try {

            // sets up the logger to write to file instead of to console
            logger.setUseParentHandlers(false);
            logger.addHandler(new FileHandler("log.txt"));

            ui.printGreeting();

            store = new Store("data/", ui);
            accounts = new AccountList(store);
            store.loadAccountsFromStore(accounts);
            TransactionManager.getInstance().setStore(store);
            store.loadTransactionsFromStore(TransactionManager.getInstance());

            Forex.initializeRates();

            ui.printSpacer();
            run();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something went wrong with starting the app", e);
        }
        // This is a workaround to solve the issue where
        // the program takes a long time to end for an indeterminate reason
        System.exit(0);
    }
}
