package seedu.duke.commands;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


public class AddCommandTest {
    @Test
    public void getCurrency_invalidCurrencyProvided_shouldThrowException() {
        try {
            Method method = AddCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY 200");
            assertThrows(InvocationTargetException.class, () -> {
                method.invoke(command, "JP");
            });
        } catch (Exception e) {
            fail();
        }
    }
}
