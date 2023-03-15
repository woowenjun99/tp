package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exceptions.TooLargeAmountException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AccountTest {
    @Test
    public void updateBalance_exceedAmount_shouldThrowException () {
        Account account = new Account(0, Currency.JPY);
        assertThrows(TooLargeAmountException.class,
                () -> account.updateBalance(10_000_001, "add")
        );
    }

    @Test
    public void updateBalance_validInput_shouldAddCorrectly () {
        Account account = new Account(0, Currency.JPY);
        assertDoesNotThrow(() -> account.updateBalance(10_000_000, "add"));
        assertEquals(10_000_000, account.getBalance());
    }
}
