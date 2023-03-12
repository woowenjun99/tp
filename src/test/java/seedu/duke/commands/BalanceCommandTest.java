package seedu.duke.commands;

import org.junit.jupiter.api.Test;
import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Currency;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class BalanceCommandTest {
    @Test
    public void processCommand_noCurrencySpecified_shouldReturnAll() {
        try {
            AccountList account = new AccountList();
            Method method = BalanceCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance", account);
            assertEquals("ALL", method.invoke(command));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_oneCurrencySpecified_shouldReturnOne() {
        try {
            AccountList account = new AccountList();
            Method method = BalanceCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance CNY", account);
            assertEquals("CNY", method.invoke(command));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_multipleCurrenciesSpecified_shouldThrowError() {
        try {
            AccountList account = new AccountList();
            Method method = BalanceCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance CNY JPY", account);
            assertThrows(InvocationTargetException.class, () -> {
                method.invoke(command);
            });
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void convertStringToEnum_invalidCurrency_shouldThrowIllegalArgumentException() {
        try {
            AccountList account = new AccountList();
            Method method = BalanceCommand.class.getDeclaredMethod("convertStringToEnum", String.class);
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance Me", account);
            assertThrows(IllegalArgumentException.class, () -> {
                method.invoke(command);
            });
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void convertStringToEnum_validCurrency_shouldReturnCorrespondingCurrency() {
        try {
            AccountList account = new AccountList();
            Method method = BalanceCommand.class.getDeclaredMethod("convertStringToEnum", String.class);
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance",account);
            assertEquals(Currency.CNY, method.invoke(command, "CNY"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getBalance_ifCurrencyIsNotSpecified_shouldReturnAllCurrencies() {
        AccountList account = new AccountList();
        account.addAccount(Currency.CNY, 200);
        account.addAccount(Currency.EUR, 40);

        try {
            Method method = BalanceCommand.class.getDeclaredMethod("getBalance", String.class);
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance", account);
            HashMap<Currency, Account> output = (HashMap<Currency, Account>) method.invoke(command, "ALL");
            assertEquals(2, output.size());
            assertEquals(200, (int) 100 * output.get(Currency.CNY).getBalance());
            assertEquals(40, (int) 100 * output.get(Currency.EUR).getBalance());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getBalance_ifNoAccountExists_shouldThrowException() {
        try {
            AccountList account = new AccountList();
            Method method = BalanceCommand.class.getDeclaredMethod("getBalance", String.class);
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance", account);
            assertThrows(InvocationTargetException.class, ()->{
                method.invoke(command, "CNY");
            });
        } catch (Exception e) {
            fail();
        }
    }
}
