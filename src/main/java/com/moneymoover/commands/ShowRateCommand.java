package com.moneymoover.commands;

import com.moneymoover.Currency;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.AccountList;
import com.moneymoover.ui.Ui;
import com.moneymoover.Forex;
import com.moneymoover.validator.Validator;
import com.moneymoover.exceptions.InvalidBigDecimalException;
import com.moneymoover.exceptions.InvalidShowrateArgumentException;
import com.moneymoover.exceptions.IllegalCurrencyException;

import java.math.BigDecimal;

/**
 * Command to print the exchange rate between two currencies
 */
public class ShowRateCommand extends Command {
    private static final float MIN_VALUE = (float) 0.01;
    private static final float MAX_VALUE = 1000000000; // 1 bn

    public ShowRateCommand (String input) {
        super(false, input);
    }

    /**
     * Prints the exchange rate between two currencies.
     * If no value is given, shows 1:X exchange rates, otherwise
     * prints the value exchanged to the target currency.
     */
    @Override
    public void execute (Ui ui, AccountList accounts) {
        try {
            Forex instance = parseInput();
            BigDecimal val = parseAmount();
            Forex reverse = new Forex(instance.getTarget(), instance.getInitial());
            ui.printMessage(getRateString(instance, val));
            ui.printMessage(getRateString(reverse, val));
        } catch (InvalidBigDecimalException e) {
            ui.printMessage(e.getDescription());
        } catch (IllegalCurrencyException e) {
            ui.printMessage(ErrorMessage.INVALID_CURRENCY);
        } catch (InvalidShowrateArgumentException e) {
            ui.printMessage(ErrorMessage.SHOWRATE_SYNTAX);
        }
    }

    /**
     * Prints the exchange rate between two currencies with a specified amount
     *
     * @param instance a Forex object containing the exchange rate
     * @param amt      a float of the amount to be converted on the exchange rate
     * @return a string containing the exchange rates to be printed
     */
    public String getRateString (Forex instance, BigDecimal amt) {
        String from = instance.getInitial().name();
        String to = instance.getTarget().name();
        return String.format("%.2f %s = %,10.6f %s", amt, from, instance.convert(amt), to);
    }

    /**
     * Parses an input string and returns a BigDecimal representing the numeric value.
     * Returns 1 if no value is provided.
     *
     * @return a BigDecimal representing the numeric value
     * @throws IllegalArgumentException if the input string is non-numeric
     */
    public BigDecimal parseAmount () throws InvalidBigDecimalException {
        String[] args = input.split(" ");
        if (args.length == 3) {
            return new BigDecimal(1);
        }
        // Checks for any non-numeric characters that are no '-' or '.'
        Validator validator = new Validator();
        BigDecimal val = validator.validateAmount(args[3]);
        assert val != null;
        return val;
    }

    /**
     * Parses the input string and returns a Forex object with the currency pair.
     * The input must be of the form show-rate Currency1 Currency2 [value].
     *
     * @return a Forex object with the currency pair
     * @throws InvalidShowrateArgumentException if the input string is not in the expected format
     * @throws IllegalCurrencyException         if an unsupported currency is provided.
     */
    public Forex parseInput () throws InvalidShowrateArgumentException, IllegalCurrencyException {
        String[] args = input.split(" ");
        if (args.length < 3 || args.length > 4) {
            throw new InvalidShowrateArgumentException();
        }
        Currency initial;
        Currency target;
        try {
            initial = Currency.valueOf(args[1].toUpperCase());
            target = Currency.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            // Differentiates between an invalid number being provided
            throw new IllegalCurrencyException();
        }
        return new Forex(initial, target);
    }
}
