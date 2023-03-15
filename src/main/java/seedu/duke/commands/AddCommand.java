package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.constants.Message;
import seedu.duke.exceptions.InvalidAddCommandException;
import seedu.duke.exceptions.InvalidAmountToAddException;
import seedu.duke.exceptions.NoAccountException;
import seedu.duke.ui.Ui;

/**
 * This class is used to deal with the addCommand.
 */
public class AddCommand extends Command {
    private Currency currency;
    private float amount;

    /**
     * @param input   The user input including the command.
     */
    public AddCommand(String input) {
        super(false, input);
    }

    private Currency getCurrency(String currencyString) {
        return Currency.valueOf(currencyString);
    }

    private void processCommand() throws InvalidAddCommandException,
            InvalidAmountToAddException {
        String[] words = super.input.split(" ");
        // Format: [Command, CURRENCY, AMOUNT]
        boolean isValidCommand = words.length == 3;
        if (!isValidCommand) {
            throw new InvalidAddCommandException();
        }
        this.currency = getCurrency(words[1]);
        this.amount = Float.parseFloat(words[2]);
        if (this.amount <= 0) {
            throw new InvalidAmountToAddException();
        }
    }

    private void printSuccess(Ui ui) {
        ui.printf(Message.SUCCESSFUL_ADD_COMMAND.getMessage(), this.currency.name(), this.amount / 100.0);
        ui.printNewLine();
    }

    /**
     * Adds the currency into the existing account if found and print a success message.
     *
     * @param ui The instance of the UI class.
     */
    @Override
    public void execute(Ui ui, AccountList account) {
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
        } catch (InvalidAmountToAddException e) {
            ui.printMessage(ErrorMessage.INVALID_AMOUNT_TO_ADD);
        } catch (NullPointerException e) {
            ui.printMessage(ErrorMessage.NO_AMOUNT_PROVIDED);
        }
    }
}
