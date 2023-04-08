package com.moneymoover.commands;

import com.moneymoover.Account;
import com.moneymoover.AccountList;
import com.moneymoover.Currency;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.constants.Message;
import com.moneymoover.validator.Validator;
import com.moneymoover.TransactionManager;
import com.moneymoover.exceptions.AmountTooPreciseException;
import com.moneymoover.exceptions.InvalidAddCommandException;
import com.moneymoover.exceptions.InvalidAmountToAddException;
import com.moneymoover.exceptions.InvalidBigDecimalException;
import com.moneymoover.exceptions.InvalidUpdateBalanceActionException;
import com.moneymoover.exceptions.NoAccountException;
import com.moneymoover.exceptions.NotEnoughInAccountException;
import com.moneymoover.exceptions.TooLargeAmountException;
import com.moneymoover.exceptions.DescriptionTooLongException;
import com.moneymoover.ui.Ui;

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
        return Currency.valueOf(currencyString.toUpperCase());
    }

    private void processCommand () throws InvalidAddCommandException,
            InvalidAmountToAddException, DescriptionTooLongException, AmountTooPreciseException,
            InvalidBigDecimalException {

        String[] words = super.input.split(" ", 4);
        // Format: [Command, CURRENCY, AMOUNT, DESCRIPTION]
        boolean isValidCommand = words.length >= 3;
        if (!isValidCommand) {
            throw new InvalidAddCommandException();
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

            transactions.addTransaction(this.currency, description, true, this.amount,
                    BigDecimal.valueOf(account.getBalance()));

        } catch (InvalidBigDecimalException e) {
            ui.printMessage(e.getDescription());
        } catch (InvalidAddCommandException e) {
            ui.printMessage(ErrorMessage.INVALID_ADD_COMMAND);
        } catch (AmountTooPreciseException e) {
            ui.printMessage(ErrorMessage.INVALID_COMMAND_TOO_PRECISE_AMOUNT);
        } catch (NumberFormatException e) {
            ui.printMessage(ErrorMessage.INVALID_NUMERICAL_AMOUNT);
        } catch (IllegalArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        } catch (NoAccountException e) {
            ui.printMessage(ErrorMessage.NO_SUCH_ACCOUNT);
        } catch (InvalidAmountToAddException e) {
            ui.printMessage(ErrorMessage.INVALID_TOO_SMALL_AMOUNT_TO_ADD_OR_WITHDRAW);
        } catch (NullPointerException e) {
            ui.printMessage(ErrorMessage.NO_AMOUNT_PROVIDED);
        } catch (NotEnoughInAccountException e) {
            // this should not happen since we are adding money
            ui.printMessage(ErrorMessage.NOT_ENOUGH_IN_ACCOUNT);
        } catch (InvalidUpdateBalanceActionException e) {
            ui.printMessage(ErrorMessage.INVALID_UPDATE_BALANCE_ACTION);
        } catch (TooLargeAmountException e) {
            ui.printMessage(ErrorMessage.EXCEED_AMOUNT_ALLOWED);
        } catch (DescriptionTooLongException e) {
            ui.printMessage(ErrorMessage.DESCRIPTION_TOO_LONG);
        }
    }
}
