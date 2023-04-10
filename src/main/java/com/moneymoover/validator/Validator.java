package com.moneymoover.validator;

import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.exceptions.InvalidBigDecimalException;
import com.moneymoover.ui.Ui;
import com.moneymoover.Currency;
import com.moneymoover.Forex;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Validator {


    private static final double UPPER_BOUND = 10_000_000;
    private static final double LOWER_BOUND = 0;

    /**
     * This validator class is used to validate the amount of money that the users intend
     * to put in. The limit is between $0 and $10,000,000 and an exception will be
     * thrown in the amount falls outside of that range.
     *
     * @param amount The amount that the user provides
     * @return The amount in big decimal.
     * @throws IllegalArgumentException   If the amount that the user provides contains a
     *                                    non-numeric characters apart from . and -. This is to
     *                                    prevent user from using values such as 1.00f.
     * @throws NumberFormatException      If the amount that the user provides is an invalid big
     *                                    decimal.
     * @throws InvalidBigDecimalException If the amount the user provides fall outside the upper
     *                                    and lower bound.
     */
    public BigDecimal validateAmount (String amount) throws InvalidBigDecimalException {
        // Validates if there are non-numeric characters apart from . and -
        if (!amount.matches("[0-9.-]+")) {
            throw new InvalidBigDecimalException(ErrorMessage.INVALID_NUMERICAL_AMOUNT);
        }

        BigDecimal value = new BigDecimal(amount);

        // Checks whether more than 2 dp is provided.
        if (Math.max(0, value.stripTrailingZeros().scale()) > 2) {
            throw new InvalidBigDecimalException(ErrorMessage.INVALID_COMMAND_TOO_PRECISE_AMOUNT);
        }

        // Checks whether the amount is more than Upper Bound

        if (value.compareTo(new BigDecimal(UPPER_BOUND)) > 0) {
            throw new InvalidBigDecimalException(ErrorMessage.EXCEED_UPPER_BOUND);
        }

        // Checks whether the amount is smaller than Lower Bound

        if (value.compareTo(new BigDecimal(LOWER_BOUND)) <= 0) {
            throw new InvalidBigDecimalException(ErrorMessage.INVALID_TOO_SMALL_AMOUNT_TO_ADD_OR_WITHDRAW);
        }

        return value;
    }

    /**
     * This method is used to check if the converted value in a show-rate or exchange command
     * is less than 0.01. If it is, it will truncate to 0 in the transaction history, resulting
     * in a loss of money for the user. The method will print what the minimum amount that must
     * be converted from the initial currency is.
     *
     * @param amount the converted value provided by the user
     * @param inst the currency relationship used
     * @param ui Ui instance
     * @return if the amount is valid
     */
    public boolean validateTargetValue (BigDecimal amount, Forex inst, Ui ui) {
        if (amount.compareTo(new BigDecimal(0.01)) < 0) {
            Currency init = inst.getInitial();
            Currency targ = inst.getTarget();
            Forex reverse = new Forex(targ, init);
            BigDecimal allowedMin = reverse.convert(new BigDecimal(0.01));
            allowedMin = allowedMin.setScale(2, RoundingMode.UP);
            ui.printMessage("You must convert at least " + allowedMin + " " + init + " to " + targ);
            return false;
        }
        return true;
    }
}
