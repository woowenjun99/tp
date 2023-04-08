package seedu.duke.commands;

import seedu.duke.storage.TestStore;
import seedu.duke.ui.Ui;
import seedu.duke.AccountList;
import seedu.duke.Forex;
import seedu.duke.exceptions.IllegalCurrencyException;
import seedu.duke.exceptions.InvalidBigDecimalException;
import seedu.duke.exceptions.InvalidShowrateArgumentException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ShowRateCommandTest {

    @Test
    public void testExecute_correctSyntax_shouldNotException () {
        try {
            Forex.initializeRates();
            Ui ui = new Ui();
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            ShowRateCommand cmd1 = new ShowRateCommand("show-rate THB SGD 1.0");
            ShowRateCommand cmd2 = new ShowRateCommand("show-rate THB SGD");
            assertDoesNotThrow(() -> cmd1.execute(ui, accounts));
            assertDoesNotThrow(() -> cmd2.execute(ui, accounts));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testParseInput_invalidCurrency_shouldThrowIllegalCurrencyException () {
        try {
            ShowRateCommand cmd1 = new ShowRateCommand("show-rate THB XYZ");
            ShowRateCommand cmd2 = new ShowRateCommand("show-rate SGD 1");
            assertThrows(IllegalCurrencyException.class, cmd1::parseInput);
            assertThrows(IllegalCurrencyException.class, cmd2::parseInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testParseInput_invalidInputLength_shouldThrowInvalidShowrateArgumentException () {
        try {
            ShowRateCommand cmd1 = new ShowRateCommand("show-rate");
            ShowRateCommand cmd2 = new ShowRateCommand("show-rate THB");
            ShowRateCommand cmd3 = new ShowRateCommand("show-rate SGD THB 1 2");
            assertThrows(InvalidShowrateArgumentException.class, cmd1::parseInput);
            assertThrows(InvalidShowrateArgumentException.class, cmd2::parseInput);
            assertThrows(InvalidShowrateArgumentException.class, cmd3::parseInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testParseAmount_nonNumericValue_shouldThrowInvalidBigDecimalException () {
        try {
            ShowRateCommand cmd1 = new ShowRateCommand("show-rate THB SGD F");
            ShowRateCommand cmd2 = new ShowRateCommand("show-rate THB SGD 1.0f");
            assertThrows(InvalidBigDecimalException.class, cmd1::parseAmount);
            assertThrows(InvalidBigDecimalException.class, cmd2::parseAmount);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testParseAmount_negativeValue_shouldThrowInvalidBigDecimalException () {
        try {
            ShowRateCommand cmd1 = new ShowRateCommand("show-rate THB SGD -0.001");
            ShowRateCommand cmd2 = new ShowRateCommand("show-rate THB SGD -1000000");
            assertThrows(InvalidBigDecimalException.class, cmd1::parseAmount);
            assertThrows(InvalidBigDecimalException.class, cmd2::parseAmount);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testParseAmount_largeValue_shouldThrowInvalidBigDecimalException () {
        try {
            ShowRateCommand cmd = new ShowRateCommand("show-rate THB SGD 10000000000000000");
            assertThrows(InvalidBigDecimalException.class, cmd::parseAmount);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testParseAmount_smallValue_shouldThrowInvalidBigDecimalException () {
        try {
            ShowRateCommand cmd = new ShowRateCommand("show-rate THB SGD 0.000001");
            assertThrows(InvalidBigDecimalException.class, cmd::parseAmount);
        } catch (Exception e) {
            fail();
        }
    }
}
