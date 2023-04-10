package com.moneymoover.commands;

import com.moneymoover.Account;
import com.moneymoover.AccountList;
import com.moneymoover.Currency;
import com.moneymoover.TransactionManager;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.ui.Ui;
import com.moneymoover.validator.Validator;
import com.moneymoover.Forex;
import com.moneymoover.exceptions.InvalidExchangeArgumentException;
import com.moneymoover.exceptions.InvalidNumberException;
import com.moneymoover.exceptions.InvalidUpdateBalanceActionException;
import com.moneymoover.exceptions.NoAccountException;
import com.moneymoover.exceptions.NotEnoughInAccountException;
import com.moneymoover.exceptions.TooLargeAmountException;
import com.moneymoover.exceptions.InvalidBigDecimalException;
import com.moneymoover.exceptions.ExchangeSameCurrencyException;
import com.moneymoover.exceptions.ConvertedAmountTooSmallException;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class ExchangeCommand extends Command {
    private TransactionManager transaction = TransactionManager.getInstance();

    /**
     * Constructor for exchange command
     *
     * @param input input for exchange command
     */
    public ExchangeCommand (String input) {
        super(false, input);
    }

    /**
     * Converts the requested amount and changes the account balances
     */
    @Override
    public void execute (Ui ui, AccountList accounts) {
        try {
            // Parse input
            Forex exchangeRate = formatInput();
            BigDecimal amount = parseAmount();

            // Retrieve and edit accounts
            Account oldAcc = accounts.getAccount(exchangeRate.getInitial());
            Account newAcc = accounts.getAccount(exchangeRate.getTarget());
            BigDecimal convertedAmount = exchangeRate.convert(amount);
            checkAmt(convertedAmount, exchangeRate, ui);
            oldAcc.updateBalance(amount, "subtract");
            newAcc.updateBalance(exchangeRate.convert(amount), "add");
            ui.printMessage(exchangeRate);
            ui.printMessage("Balance of initial account --> " + oldAcc);
            ui.printMessage("Balance of target account --> " + newAcc);

            amount = amount.setScale(2, RoundingMode.DOWN);
            convertedAmount = convertedAmount.setScale(2, RoundingMode.DOWN);
            String description = String.format("exchange %.2f %s to %.2f %s", amount, exchangeRate.getInitial().name(),
                    convertedAmount, exchangeRate.getTarget().name());
            accounts.save();


            transaction.addTransaction(exchangeRate.getInitial(), description, false, amount,
                    BigDecimal.valueOf(oldAcc.getBalance()));

            transaction.addTransaction(exchangeRate.getTarget(), description, true,
                    convertedAmount, BigDecimal.valueOf(newAcc.getBalance()));
            // Exception handling
        } catch (InvalidBigDecimalException e) {
            ui.printMessage(e.getDescription());
        } catch (NoAccountException e) {
            ui.printMessage(ErrorMessage.NO_SUCH_ACCOUNT);
            printMissingAccounts(ui, accounts);
        } catch (IllegalArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        } catch (InvalidExchangeArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_EXCHANGE_ARGUMENT);
        } catch (NotEnoughInAccountException e) {
            ui.printMessage(ErrorMessage.NOT_ENOUGH_IN_ACCOUNT);
        } catch (InvalidUpdateBalanceActionException e) {
            ui.printMessage(ErrorMessage.INVALID_UPDATE_BALANCE_ACTION);
        } catch (TooLargeAmountException e) {
            ui.printMessage(ErrorMessage.EXCEED_AMOUNT_ALLOWED);
        } catch (ExchangeSameCurrencyException e) {
            ui.printMessage(ErrorMessage.EXCHANGE_SAME_CURRENCY);
        } catch (ConvertedAmountTooSmallException e) {
            ui.printMessage(ErrorMessage.LOSS_OF_EXCHANGE_VALUE);
        }
    }

    /**
     * Converts input into Forex object for use in execution
     *
     * @return Forex object with intial and target currencies
     * @throws IllegalArgumentException         if the currencies are not supported
     * @throws InvalidExchangeArgumentException if arguments are incorrect
     * @throws ExchangeSameCurrencyException    if the currencies are the same
     */
    public Forex formatInput () throws InvalidExchangeArgumentException, ExchangeSameCurrencyException {
        String[] splitInput = input.trim().split(" ");
        if (splitInput.length != 4) {
            throw new InvalidExchangeArgumentException();
        }
        Currency initial = Currency.valueOf(splitInput[1].toUpperCase());
        Currency target = Currency.valueOf(splitInput[2].toUpperCase());
        if (initial.equals(target)) {
            throw new ExchangeSameCurrencyException();
        }
        return new Forex(initial, target);
    }

    /**
     * Retrieves the amount to be converted from the input
     *
     * @return BigDecimal representing amount to be converted
     * @throws NullPointerException   if the amount is null
     * @throws InvalidNumberException if the amount is incorrectly formatted
     */
    public BigDecimal parseAmount () throws InvalidBigDecimalException {
        Validator validator = new Validator();
        BigDecimal amount = validator.validateAmount(input.trim().split(" ")[3]);
        assert amount != null;
        return amount;
    }

    /**
     * Prints a message indicating if any required accounts are missing.
     *
     * @param ui       the user interface to use for printing the message
     * @param accounts the list of accounts to check for missing accounts
     */
    private void printMissingAccounts (Ui ui, AccountList accounts) {
        try {
            Forex exchangeRate = formatInput();
            try {
                accounts.getAccount(exchangeRate.getInitial());
            } catch (NoAccountException e) {
                ui.printMessage("You need a " + exchangeRate.getInitial() + " account!");
            }
            try {
                accounts.getAccount(exchangeRate.getTarget());
            } catch (NoAccountException e) {
                ui.printMessage("You need a " + exchangeRate.getTarget() + " account!");
            }
        } catch (InvalidExchangeArgumentException e) {
            ui.printMessage(ErrorMessage.INVALID_EXCHANGE_ARGUMENT);
        } catch (ExchangeSameCurrencyException e) {
            ui.printMessage(ErrorMessage.EXCHANGE_SAME_CURRENCY);
        }
    }

    /**
     * A wrapper method to check if the target amount is too small
     *
     * @param amt  the amount to check
     * @param inst the currency relationship
     * @param ui   Ui instance
     * @throws ConvertedAmountTooSmallException if the number is too small
     */
    private static void checkAmt (BigDecimal amt, Forex inst, Ui ui) throws ConvertedAmountTooSmallException {
        Validator val = new Validator();
        if (!val.validateTargetValue(amt, inst, ui)) {
            throw new ConvertedAmountTooSmallException();
        }
    }
}
