package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.constants.Message;
import seedu.duke.exceptions.InvalidAddCommandException;
import seedu.duke.exceptions.NoAccountException;
import seedu.duke.ui.Ui;

public class AddCommand extends Command {
    private Currency currency;
    private int amount;
    private AccountList account = AccountList.getInstance();

    public AddCommand(String input) {
        super(false, input);
    }

    private Currency getCurrency(String currencyString) {
        return Currency.valueOf(currencyString);
    }

    private void processCommand() throws InvalidAddCommandException {
        String[] words = super.input.split(" ");
        // Format: [Command, CURRENCY, AMOUNT]
        boolean isValidCommand = words.length == 3;
        if (!isValidCommand) {
            throw new InvalidAddCommandException();
        }
        this.currency = getCurrency(words[1]);
        this.amount = Integer.parseInt(words[2]);
    }

    private void printSuccess(Ui ui) {
        ui.printf(Message.SUCCESSFUL_ADD_COMMAND.getMessage(), this.currency.name(), this.amount);
    }

    @Override
    public void execute(Ui ui) {
        try {
            processCommand();
            account.addAmount(this.currency, this.amount);
            printSuccess(ui);
        } catch (InvalidAddCommandException e) {
            ui.printMessage(ErrorMessage.INVALID_ADD_COMMAND);
        } catch (NumberFormatException e) {
            ui.printMessage(ErrorMessage.INVALID_NUMERICAL_AMOUNT);
        } catch (IllegalArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        } catch (NoAccountException e) {
            ui.printMessage(ErrorMessage.NO_SUCH_ACCOUNT);
        }
    }
}
