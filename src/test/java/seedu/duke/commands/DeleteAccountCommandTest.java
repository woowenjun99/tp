package seedu.duke.commands;

import org.junit.jupiter.api.Test;
import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.exceptions.NoAccountException;
import seedu.duke.ui.Ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteAccountCommandTest {
    @Test
    public void getCurrency_invalidCurrencyProvided_shouldThrowException () {
        try {
            Method method = DeleteAccountCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            DeleteAccountCommand command = new DeleteAccountCommand("delete-account JPY");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, "JP"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getCurrency_validCurrencyProvided_shouldReturnCorrespondingCurrency () {
        try {
            Method method = DeleteAccountCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            DeleteAccountCommand command = new DeleteAccountCommand("delete-account JPY");
            assertEquals(Currency.JPY, method.invoke(command, "JPY"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_commandLessThanTwoWords_shouldThrowException () {
        try {
            Method method = AddCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            DeleteAccountCommand command = new DeleteAccountCommand("delete-account");
            assertThrows(IllegalArgumentException.class, () -> method.invoke(command));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void execute_accountNotEmpty_shouldThrowException () {
        try {
            AccountList accounts = new AccountList();
            accounts.addAccount(Currency.KRW, 1000);
            DeleteAccountCommand command = new DeleteAccountCommand("delete-account KRW");
            Ui ui = new Ui();
            command.execute(ui, accounts);

            // Account should not be removed if account not empty
            assertDoesNotThrow(() -> accounts.getAccount(Currency.KRW));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteAccount_nonExistCurrency_shouldThrowException () {
        try {
            Method method = AccountList.class.getDeclaredMethod(("deleteAccount"), Currency.class);
            method.setAccessible(true);
            AccountList accounts = new AccountList();
            accounts.addAccount(Currency.KRW, 1000);
            assertThrows(InvocationTargetException.class, () -> method.invoke(accounts, Currency.JPY));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void execute_correctInputProvided_shouldDeleteAccount () {
        try {
            AccountList accounts = new AccountList();
            accounts.addAccount(Currency.KRW, 0);
            DeleteAccountCommand command = new DeleteAccountCommand("delete-account KRW");
            Ui ui = new Ui();
            command.execute(ui, accounts);

            assertThrows(NoAccountException.class, () -> accounts.getAccount(Currency.KRW));
        } catch (Exception e) {
            fail();
        }
    }
}
