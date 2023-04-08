package com.moneymoover.commands;

import com.moneymoover.AccountList;
import com.moneymoover.ui.Ui;

import java.math.BigDecimal;

public abstract class Command {
    protected final boolean isExit;
    protected final String input;

    public Command (boolean isExit, String input) {
        this.isExit = isExit;
        this.input = input;
    }

    /**
     * Executes the command implemented by the subclass
     */

    public abstract void execute (Ui ui, AccountList accounts);

    public boolean isExit () {
        return isExit;
    }

    /**
     * Method that gets the number of decimal places in a big decimal
     *
     * @param bigDecimal The big decimal to count the number of decimal places of
     * @return The number of decimal places in the big decimal
     */
    protected int getNumberOfDecimalPlaces (BigDecimal bigDecimal) {
        int scale = bigDecimal.stripTrailingZeros().scale();
        return Math.max(scale, 0);
    }
}
