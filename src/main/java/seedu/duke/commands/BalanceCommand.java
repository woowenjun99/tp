package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.exceptions.InvalidBalanceCommand;

public class BalanceCommand extends Command {
    private AccountList accounts = AccountList.getInstance();
    private String command;


    private String processCommand() throws InvalidBalanceCommand {
        String[] words = command.split(" ");
        switch (words.length) {
        case 1:
            return "ALL";
        case 2:
            return words[1];
        default:
            throw new InvalidBalanceCommand();
        }
    }

    private Currency convertStringToEnum(String currency) {
        return Currency.valueOf(currency);
    }

    BalanceCommand(String command) {
        super(false);
        this.command = command.trim();
    }

    @Override
    public void execute() {
        try {
            String currency = processCommand();
        } catch (InvalidBalanceCommand e) {
            System.out.println("An invalid balance command is provided.");
        }

    }
}
