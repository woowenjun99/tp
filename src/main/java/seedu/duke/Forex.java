package seedu.duke;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import seedu.duke.api.ExchangeRates;

/**
 * Forex object represents the relationship between two currencies. All
 * exchange rates are stored in a hash map that uses the ticker as a key
 * and maps to the value of S$1 in that currency.
 */
public class Forex {

    /**
     * Hash map to store all exchange rates. Rather than a 2D array,
     * just stores rate of S$1 in each currency. All exchanges
     * convert first to SGD, then from SGD to their target. To be
     * implemented using API.
     */
    private static HashMap<Currency, BigDecimal> exchangeRates;

    private Currency initial;
    private Currency target;

    // Constructor
    public Forex (Currency from, Currency to) {
        initial = from;
        target = to;
    }

    /**
     * Initializes the exchange rates by fetching them from the ExchangeRates class.
     * Buffers for 5 seconds to allow the API call to go through.
     * This method will only be called once upon starting the program.
     */ 
    public static void initializeRates() {
        System.out.println("Initializing exchange rates. Please wait 5 seconds...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Rates not initialized");
        }
        exchangeRates = ExchangeRates.getExchangeRates();
    }

    // Prints the exchange relationship
    @Override
    public String toString () {
        return "Exchanging from " + initial + " to " + target;
    }

    /**
     * Converts an amount from the initial currency to the target currency.
     * First converts initial into SGD, then from SGD into target.
     *
     * @param amount amount to be converted from initial to target
     * @return amount converted to target currency
     */
    public BigDecimal convert (BigDecimal amount) {
        BigDecimal amountInSGD = amount.divide(
                exchangeRates.get(initial), 10, RoundingMode.HALF_UP
        );
        BigDecimal amountInTarget = amountInSGD.multiply(exchangeRates.get(target));
        return amountInTarget;
    }

    // Accessor methods
    public Currency getInitial () {
        return initial;
    }

    public Currency getTarget () {
        return target;
    }
}
