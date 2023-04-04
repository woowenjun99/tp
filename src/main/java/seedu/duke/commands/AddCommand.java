package seedu.duke.commands;

import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.TransactionManager;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.constants.Message;
import seedu.duke.exceptions.InvalidAddCommandException;
import seedu.duke.exceptions.InvalidAmountToAddException;
import seedu.duke.exceptions.InvalidBigDecimalException;
import seedu.duke.exceptions.InvalidUpdateBalanceActionException;
import seedu.duke.exceptions.NoAccountException;
import seedu.duke.exceptions.NotEnoughInAccountException;
import seedu.duke.exceptions.TooLargeAmountException;
import seedu.duke.ui.Ui;

import java.math.BigDecimal;

/**
 * This class is used to deal with the addCommand.
 */
public class AddCommand extends Command {
    private Currency currency;
    private BigDecimal amount;

    private String description;
    private final TransactionManager transactions = TransactionManager.getInstance();

    /**
     * @param input The user input including the command.
     */
    public AddCommand (String input) {
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
     * @return The big decimal amount that was being processed
     * @throws NumberFormatException      If the string that is being processed
     *                                    cannot be represented as a big decimal.
     * @throws InvalidBigDecimalException If the amount provided is invalid.
     *                                    This may be due to more than 2 dp or if
     *                                    the value is more than 10_000_000.
     */
    private BigDecimal validateAndGetAmount (String amount) throws InvalidBigDecimalException, 
            InvalidAmountToAddException {
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
            throw new InvalidAmountToAddException();
        }
        return value;
    }


    private void processCommand () throws InvalidAddCommandException,
            InvalidAmountToAddException, InvalidBigDecimalException {
        String[] words = super.input.split(" ", 4);
        // Format: [Command, CURRENCY, AMOUNT, DESCRIPTION]
        boolean isValidCommand = words.length >= 3;
        if (!isValidCommand) {
            throw new InvalidAddCommandException();
        }
        this.currency = getCurrency(words[1]);

        this.amount = validateAndGetAmount(words[2]);

        if (words.length == 4) {
            this.description = words[3];
        } else {
            this.description = "";
        }

    }

    private void printSuccess (Ui ui) {
        ui.printf(Message.SUCCESSFUL_ADD_COMMAND.getMessage(), this.currency.name(), this.amount);
        ui.printNewLine();
    }

    /**
     * Adds the currency into the existing account if found and print a success message.
     *
     * @param ui The instance of the UI class.
     */
    @Override
    public void execute (Ui ui, AccountList accounts) {
        try {
            processCommand();
            Account account = accounts.getAccount(this.currency);
            account.updateBalance(this.amount, "add");
            accounts.save();
            printSuccess(ui);

            transactions.addTransaction(this.currency, this.description, true, this.amount,
                    BigDecimal.valueOf(account.getBalance()));

        } catch (InvalidAddCommandException e) {
            ui.printMessage(ErrorMessage.INVALID_ADD_COMMAND);
        } catch (NumberFormatException e) {
            ui.printMessage(ErrorMessage.INVALID_NUMERICAL_AMOUNT);
        } catch (IllegalArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        } catch (NoAccountException e) {
            ui.printMessage(ErrorMessage.NO_SUCH_ACCOUNT);
        } catch (InvalidAmountToAddException e) {
            ui.printMessage(ErrorMessage.INVALID_AMOUNT_TO_ADD_OR_WITHDRAW);
        } catch (NullPointerException e) {
            ui.printMessage(ErrorMessage.NO_AMOUNT_PROVIDED);
        } catch (NotEnoughInAccountException e) {
            // this should not happen since we are adding money
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
