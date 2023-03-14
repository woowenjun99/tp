package seedu.duke.commands;

import org.junit.jupiter.api.Test;
import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WithdrawCommandTest {
    @Test
    public void execute_correctInputProvided_shouldUpdateAmount(){
        try {
            AccountList account = new AccountList();
            account.addAccount(Currency.KRW, 4000);
            WithdrawCommand command = new WithdrawCommand("withdraw 2000 KRW");
            Ui ui = new Ui();
            command.execute(ui, account);
            int expectedAmount =  (int) account.getBalance(Currency.KRW).get(Currency.KRW).getBalance() * 100;
            assertEquals(2000, expectedAmount);
        } catch (Exception e) {
            fail();
        }
    }
}
