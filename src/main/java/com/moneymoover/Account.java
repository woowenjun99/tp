package com.moneymoover;

import com.moneymoover.exceptions.TooLargeAmountException;
import com.moneymoover.exceptions.InvalidUpdateBalanceActionException;
import com.moneymoover.exceptions.NotEnoughInAccountException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {
    public static final long UPPER_BOUND = 1_000_000_000;
    private long balance;
    private final Currency currency;

    /**
     * Constructor for Account using a long.
     *
     * @param initialBalance the initial balance of the account in CENTS
     * @param currency       the currency of the account
     */
    Account (long initialBalance, Currency currency) {
        this.currency = currency;
        this.balance = initialBalance;
    }

    /**
     * Constructor for Account using a float.
     *
     * @param initialBalance the initial balance of the account in DOLLARS (multiplication is done here)
     * @param currency       the currency of the account
     */
    Account (float initialBalance, Currency currency) {
        this.currency = currency;
        // scaleByPowerOfTen(2) is used to convert dollars to cents, same as multiplying by 100
        this.balance = BigDecimal.valueOf(initialBalance).scaleByPowerOfTen(2).longValue();
    }

    public long getLongBalance () {
        return balance;
    }

    public float getBalance () {
        return balance / 100.0f;
    }

    public Currency getCurrencyType () {
        return currency;
    }

    /**
     * Updates the balance of the account
     *
     * @param changeInBalance the amount added or subtract from the account
     * @param action          specifies whether to add or subtract value
     * @throws NotEnoughInAccountException if the balance would become negative
     */
    public void updateBalance (BigDecimal changeInBalance, String action) throws NotEnoughInAccountException,
            InvalidUpdateBalanceActionException, TooLargeAmountException {
        long newBalance;
        if (action.equals("add")) {
            newBalance = balance + changeInBalance.multiply(BigDecimal.valueOf(100)).longValue();

            if (newBalance > UPPER_BOUND) {
                throw new TooLargeAmountException();
            }
        } else if (action.equals("subtract")) {
            newBalance = balance - changeInBalance.multiply(BigDecimal.valueOf(100)).longValue();
        } else {
            throw new InvalidUpdateBalanceActionException();
        }
        if (newBalance < 0) {
            throw new NotEnoughInAccountException();
        }
        balance = newBalance;
    }

    @Override
    public String toString () {
        String currencyType = currency.name();
        return currencyType + ": " + String.format("%.2f",
                BigDecimal.valueOf(balance).divide(BigDecimal.valueOf(100), 10, RoundingMode.DOWN)
        );
    }

}
