package seedu.duke.commands;

import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.TransactionManager;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.constants.Message;
import seedu.duke.exceptions.InvalidWithdrawAmountException;
import seedu.duke.exceptions.AmountTooPreciseException;
import seedu.duke.exceptions.InvalidUpdateBalanceActionException;
import seedu.duke.exceptions.InvalidWithdrawCommandException;
import seedu.duke.exceptions.DescriptionTooLongException;
import seedu.duke.exceptions.InvalidBigDecimalException;
import seedu.duke.exceptions.NoAccountException;
import seedu.duke.exceptions.NotEnoughInAccountException;
import seedu.duke.exceptions.TooLargeAmountException;

import seedu.duke.ui.Ui;
import seedu.duke.validator.Validator;

import java.math.BigDecimal;

/**
 * This class is used to deal with the withdrawCommand.
 */
public class WithdrawCommand extends Command {
    private Currency currency;
    private BigDecimal amount;
    private String description;

    private TransactionManager transactions = TransactionManager.getInstance();

    /**
     * @param input The user input including the command.
     */
    public WithdrawCommand (String input) {
        super(false, input);
    }

    private Currency getCurrency (String currencyString) {
        return Currency.valueOf(currencyString);
    }

    private void processCommand () throws InvalidWithdrawCommandException, DescriptionTooLongException,
            InvalidWithdrawAmountException, AmountTooPreciseException, InvalidBigDecimalException {


        String[] words = super.input.split(" ", 4);
        // Format: [Command, CURRENCY, AMOUNT, DESCRIPTION]
        boolean isValidCommand = words.length >= 3;
        if (!isValidCommand) {
            throw new InvalidWithdrawCommandException();
        }

        currency = getCurrency(words[1]);
        Validator validator = new Validator();
        amount = validator.validateAmount(words[2]);

        boolean containDescription = words.length == 4;
        if (containDescription) {
            if (words[3].trim().length() > 100) {
                throw new DescriptionTooLongException();
            }
            description = words[3].trim();
        } else {
            description = "";
        }
    }

    private void printSuccess (Ui ui, float newBalance) {
        ui.printf(Message.SUCCESSFUL_WITHDRAW_COMMAND.getMessage(), this.amount, this.currency.name(),
                newBalance, this.currency.name());
        ui.printNewLine();
    }

    /**
     * Withdraw the currency into the existing account if found and print a success message.
     *
     * @param ui The instance of the UI class.
     */
    @Override
    public void execute (Ui ui, AccountList accounts) {
        try {
            processCommand();
            Account account = accounts.getAccount(this.currency);
            account.updateBalance(this.amount, "subtract");
            accounts.save();
            printSuccess(ui, account.getBalance());

            transactions.addTransaction(this.currency, description, false,
                    this.amount, BigDecimal.valueOf(account.getBalance()));

        } catch (InvalidBigDecimalException e) {
            ui.printMessage(e.getDescription());
        } catch (InvalidWithdrawAmountException e) {
            ui.printMessage(ErrorMessage.INVALID_TOO_SMALL_AMOUNT_TO_ADD_OR_WITHDRAW);
        } catch (AmountTooPreciseException e) {
            ui.printMessage(ErrorMessage.INVALID_COMMAND_TOO_PRECISE_AMOUNT);
        } catch (NumberFormatException e) {
            ui.printMessage(ErrorMessage.INVALID_NUMERICAL_AMOUNT);
        } catch (IllegalArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        } catch (NoAccountException e) {
            ui.printMessage(ErrorMessage.NO_SUCH_ACCOUNT);
        } catch (NotEnoughInAccountException e) {
            ui.printMessage(ErrorMessage.NOT_ENOUGH_IN_ACCOUNT);
        } catch (InvalidUpdateBalanceActionException e) {
            ui.printMessage(ErrorMessage.INVALID_UPDATE_BALANCE_ACTION);
        } catch (TooLargeAmountException e) {
            ui.printMessage(ErrorMessage.EXCEED_AMOUNT_ALLOWED);

        } catch (DescriptionTooLongException e) {
            ui.printMessage(ErrorMessage.DESCRIPTION_TOO_LONG);
        } catch (InvalidWithdrawCommandException e) {
            ui.printMessage(ErrorMessage.INVALID_WITHDRAW_COMMAND);
        }
    }
}
