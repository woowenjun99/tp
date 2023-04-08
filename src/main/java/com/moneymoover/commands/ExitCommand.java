package com.moneymoover.commands;

import com.moneymoover.AccountList;
import com.moneymoover.ui.Ui;

/**
 * Command to exit the program
 */
public class ExitCommand extends Command {
    public ExitCommand () {
        super(true, "");
    }

    @Override
    public void execute (Ui ui, AccountList accounts) {
        ui.printFarewell();
    }
}
