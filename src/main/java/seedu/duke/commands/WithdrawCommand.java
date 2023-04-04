package seedu.duke.commands;

import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.TransactionManager;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.constants.Message;
import seedu.duke.exceptions.InvalidBigDecimalException;
import seedu.duke.exceptions.InvalidUpdateBalanceActionException;
import seedu.duke.exceptions.InvalidWithdrawCommandException;
import seedu.duke.exceptions.NoAccountException;
import seedu.duke.exceptions.NotEnoughInAccountException;
import seedu.duke.exceptions.TooLargeAmountException;
import seedu.duke.ui.Ui;

import java.math.BigDecimal;

/**
 * This class is used to deal with the withdrawCommand.
 */
public class WithdrawCommand extends Command {
    private Currency currency;
    private BigDecimal amount;
    private String description;

    private final TransactionManager transactions = TransactionManager.getInstance();

    /**
     * @param input The user input including the command.
     */
    public WithdrawCommand (String input) {
        super(false, input);
    }

    private Currency getCurrency (String currencyString) {
        return Currency.valueOf(currencyString);
    }

    /**
     * This method is used to validate the amount that is being passed in
     * by the user and determines if it is a valid amount. If the amount
     * provided is invalid, we will throw an error. Otherwise, we will
     * return the amount to be processed.
     *
     * @param amount The amount that is being provided in string
     * @return The value that has been validated and processed into a big decimal.
     * @throws NumberFormatException      If the string that is being processed
     *                                    cannot be represented as a big decimal.
     * @throws InvalidBigDecimalException If the amount provided is invalid.
     *                                    This may be due to more than 2 dp or if
     *                                    the value is more than 10_000_000.
     */
    private BigDecimal validateAndGetAmount (String amount) throws InvalidBigDecimalException,
            InvalidWithdrawCommandException {
        BigDecimal value = new BigDecimal(amount);

        // Checks whether more than 2 dp is provided.
        if (Math.max(0, value.stripTrailingZeros().scale()) > 2) {
            throw new InvalidBigDecimalException("Please do not provide more than 2 decimal places");
        }

        // Checks whether the amount is more than 10_000_000. We do not compare directly as it will
        // lead to an issue of overflow.
        if (value.compareTo(new BigDecimal("10000000")) > 0) {
            throw new InvalidBigDecimalException("Please do not provide a value of more than $10,000,000");
        }

        if (value.compareTo(new BigDecimal("0")) <= 0) {
            throw new InvalidWithdrawCommandException();
        }
        return value;
    }

    private void processCommand () throws InvalidWithdrawCommandException, InvalidBigDecimalException {
        String[] words = super.input.split(" ", 4);
        // Format: [Command, CURRENCY, AMOUNT, DESCRIPTION]
        boolean isValidCommand = words.length >= 3;
        if (!isValidCommand) {
            throw new InvalidWithdrawCommandException();
        }

        this.currency = getCurrency(words[1]);
        this.amount = validateAndGetAmount(words[2]);

        boolean containDescription = words.length == 4;
        if (containDescription) {
            this.description = words[3];
        } else {
            this.description = "";
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

            transactions.addTransaction(this.currency, this.description, false,
                    this.amount, BigDecimal.valueOf(account.getBalance()));

        } catch (InvalidWithdrawCommandException e) {
            ui.printMessage(ErrorMessage.INVALID_AMOUNT_TO_ADD_OR_WITHDRAW);
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
        } catch (InvalidBigDecimalException e) {
            ui.printMessage(e.getDescription());
        }
    }
}
