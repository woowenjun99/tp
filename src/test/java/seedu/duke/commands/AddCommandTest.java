package seedu.duke.commands;

import org.junit.jupiter.api.Test;
import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.ui.Ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddCommandTest {
    @Test
    public void getCurrency_invalidCurrencyProvided_shouldThrowException() {
        try {
            AccountList account = new AccountList();            
            Method method = AddCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY 200", account);
            assertThrows(InvocationTargetException.class, () -> {
                method.invoke(command, "JP");
            });
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getCurrency_validCurrencyProvided_shouldReturnCorrespondingCurrency() {
        try {
            AccountList account = new AccountList();            
            Method method = AddCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY 200", account);
            assertEquals(Currency.JPY, method.invoke(command, "JPY"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_commandLessThanThreeWords_shouldThrowException() {
        try {
            AccountList account = new AccountList();            
            Method method = AddCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY", account);
            assertThrows(InvocationTargetException.class, () -> {
                method.invoke(command);
            });
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_amountNotInt_shouldThrowException() {
        try {
            AccountList account = new AccountList();            
            Method method = AddCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY m", account);
            assertThrows(InvocationTargetException.class, () -> {
                method.invoke(command);
            });
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_correctInputFormat_shouldNotThrowException() {
        try {
            AccountList account = new AccountList();
            Method method = AddCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY 200", account);
            assertDoesNotThrow(() -> {
                method.invoke(command);
            });
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void execute_correctInputProvided_shouldUpdateAmount() {
        try {
            AccountList account = new AccountList();
            account.addAccount(Currency.KRW, 2000);
            AddCommand command = new AddCommand("add KRW 2000", account);
            Ui ui = new Ui();
            command.execute(ui);
            int expectedAmount =  (int) account.getBalance(Currency.KRW).get(Currency.KRW).getBalance() * 100;
            assertEquals(4000, expectedAmount);
        } catch (Exception e) {
            fail();
        }
    }
}
