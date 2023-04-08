package com.moneymoover.commands;

import com.moneymoover.constants.Message;
import com.moneymoover.AccountList;
import com.moneymoover.ui.Ui;

public class HelpCommand extends Command {
    public HelpCommand (String input) {
        super(false, input);
    }

    @Override
    public void execute (Ui ui, AccountList accounts) {
        ui.printMessage(Message.HELP.getMessage());
    }
}
