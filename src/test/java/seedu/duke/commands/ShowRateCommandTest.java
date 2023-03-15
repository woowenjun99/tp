package seedu.duke.commands;

import org.junit.jupiter.api.Test;
import seedu.duke.ui.Ui;
import seedu.duke.AccountList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

public class ShowRateCommandTest {

    @Test
    public void testExecute_correctSyntax_shouldNotException() {
        try {
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
}
