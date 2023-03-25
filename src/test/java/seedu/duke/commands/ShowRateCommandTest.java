package seedu.duke.commands;

import seedu.duke.ui.Ui;
import seedu.duke.AccountList;
import seedu.duke.Forex;
import seedu.duke.Currency;
import seedu.duke.exceptions.InvalidNumberException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class ShowRateCommandTest {

    @Test
    public void testExecute_correctSyntax_shouldNotException() {
        try {
            Forex.initializeRates();
            Ui ui = new Ui();
            AccountList accounts = new AccountList();
            ShowRateCommand cmd1 = new ShowRateCommand("show-rate THB SGD 1.0");
            ShowRateCommand cmd2 = new ShowRateCommand("show-rate THB SGD");
            assertDoesNotThrow(() -> cmd1.execute(ui, accounts));
            assertDoesNotThrow(() -> cmd2.execute(ui, accounts));
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Potential exceptions are all caught and handled by execute, so if
     * no exception is thrown in these cases, then it means the code
     * does not break under any of these circumstances.
     */
    @Test
    public void testExecute_incorrectSyntax_shouldHandleThrowInvalidShowRateArgumentException() {
        try {
            Ui ui = new Ui();
            AccountList accounts = new AccountList();
            ShowRateCommand cmd1 = new ShowRateCommand("show-rate THB");
            ShowRateCommand cmd2 = new ShowRateCommand("show-rate THB SGD USD MYR");
            cmd1.execute(ui, accounts);
            cmd2.execute(ui, accounts);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testExecute_invalidCurrencyOrNumber_shouldHandleIllegalArgumentException() {
        try {
            Ui ui = new Ui();
            AccountList accounts = new AccountList();
            ShowRateCommand cmd1 = new ShowRateCommand("show-rate THB XYZ");
            ShowRateCommand cmd2 = new ShowRateCommand("show-rate THB SGD CS_2113");
            cmd1.execute(ui, accounts);
            cmd2.execute(ui, accounts);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testExecute_negativeNumber_shouldHandleInvalidNumberException() {
        try {
            Ui ui = new Ui();
            AccountList accounts = new AccountList();
            ShowRateCommand cmd = new ShowRateCommand("show-rate THB SGD -1.24");
            cmd.execute(ui, accounts);
        } catch (Exception e) {
            fail();
        }
    }

    public void testGetRateString_negativeNumber_shouldThrowInvalidNumberException() {
        try {
            Ui ui = new Ui();
            AccountList accounts = new AccountList();
            ShowRateCommand cmd = new ShowRateCommand("show-rate THB SGD 1");
            Forex inst = new Forex(Currency.SGD, Currency.USD);
            BigDecimal negativeVal = new BigDecimal(-1.25);
            assertThrows(InvalidNumberException.class, () -> cmd.getRateString(inst, negativeVal));
        } catch (Exception e) {
            fail();
        }
    }
}
