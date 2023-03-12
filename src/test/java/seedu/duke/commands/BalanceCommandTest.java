package seedu.duke.commands;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class BalanceCommandTest {
    @Test
    public void processCommand_NoCurrencySpecified_ShouldReturnAll() {
        try {
            Method method = BalanceCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance");
            assertEquals("ALL", method.invoke(command));
        } catch (Exception e) {
            fail();
        }
    }
}
