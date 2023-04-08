package com.moneymoover;

import com.moneymoover.parser.Parser;
import com.moneymoover.storage.Store;
import com.moneymoover.ui.Ui;
import com.moneymoover.commands.Command;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoneyMoover {

    private static Ui ui;
    private static final Store store = new Store("data/");
    private static final AccountList accounts = new AccountList(store);
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
     * Main entry-point for the MoneyMoover application.
     */
    public static void main (String[] args) {
        try {
            logger.setUseParentHandlers(false);
            logger.addHandler(new FileHandler("log.txt"));
            ui = new Ui();
            ui.printGreeting();
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
