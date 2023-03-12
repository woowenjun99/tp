package seedu.duke;

import java.util.HashMap;

/**
 * Forex object represents the relationship between two currencies. All
 * exchange rates are stored in a hash map that uses the ticker as a key
 * and maps to the value of S$1 in that currency. 
 */
public class Forex {

    private Currency initial;
    private Currency target;

    /**
     * Hash map to store all exchange rates. Rather than a 2D array,
     * just stores rate of S$1 in each currency. All exchanges
     * convert first to SGD, then from SGD to their target.
     */
    private static HashMap<Currency, Integer> exchangeRates = new HashMap<Currency, Integer>();

    // Sets dummy equal exchange rate to SGD
    public static void populateRates() {
        for (Currency currency : Currency.values()) {
            exchangeRates.put(currency, 1);
        }
    }

    // Constructor
    public Forex(Currency from, Currency to) {
        initial = from;
        target = to;
    }

    // Prints the exchange relationship
    public void printForex() {
        System.out.println("Exchanging from " + initial + " to " + target);
    }

    /**
     * Converts an amount from the initial currency to the target currency.
     * First converts initial into SGD, then from SGD into target.
     * @param amount amount to be converted from initial to target
     */
    public float convert(float amount) {
        float amountInSGD = amount / exchangeRates.get(initial);
        float amountInTarget = amountInSGD * exchangeRates.get(target);
        return amountInTarget;
    }

    // Accessor methods
    public Currency getInitial() {
        return initial;
    }
    public Currency getTarget() {
        return target;
    }
}