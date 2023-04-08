package seedu.duke.validator;

import seedu.duke.constants.ErrorMessage;
import seedu.duke.exceptions.InvalidBigDecimalException;

import java.math.BigDecimal;

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
}
