package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.Currency;

public class BalanceCommand extends Command {
    private AccountList accounts = AccountList.getInstance();
    private String command;


    private String processCommand() {
        String[] words = command.split(" ");
        if (words.length == 1) {
            return "ALL";
        }
        return words[1];
    }

    BalanceCommand(String command) {
        super(false);
        this.command = command.trim();
    }

    @Override
    public void execute() {
        // Process command
        // Get currency
        // Print output
    }
}
