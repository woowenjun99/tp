package seedu.duke.commands;

import org.junit.jupiter.api.Test;
import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Currency;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class BalanceCommandTest {
    @Test
    public void processCommand_noCurrencySpecified_shouldReturnAll() {
        try {
            Method method = BalanceCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance");
            assertEquals("ALL", method.invoke(command));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_oneCurrencySpecified_shouldReturnOne() {
        try {
            Method method = BalanceCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance CNY");
            assertEquals("CNY", method.invoke(command));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_multipleCurrenciesSpecified_shouldThrowError() {
        try {
            Method method = BalanceCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance CNY JPY");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void convertStringToEnum_invalidCurrency_shouldThrowIllegalArgumentException() {
        try {
            Method method = BalanceCommand.class.getDeclaredMethod("convertStringToEnum", String.class);
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, "ME"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void convertStringToEnum_validCurrency_shouldReturnCorrespondingCurrency() {
        try {
            Method method = BalanceCommand.class.getDeclaredMethod("convertStringToEnum", String.class);
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance");
            assertEquals(Currency.CNY, method.invoke(command, "CNY"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getBalance_ifCurrencyIsNotSpecified_shouldReturnAllCurrencies() {
        AccountList accounts = new AccountList();

        try {
            accounts.addAccount(Currency.CNY, 200.0f);
            accounts.addAccount(Currency.EUR, 40.0f);
            Method method = BalanceCommand.class.getDeclaredMethod("getAccounts", String.class, AccountList.class);
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance");
            ArrayList<Account> output = (ArrayList<Account>) method.invoke(command,
                    "ALL",
                    accounts
            );
            Account CNYAccount = null;
            Account EURAccount = null;
            for(Account account : output){
                if(account.getCurrencyType().equals(Currency.EUR)){
                    EURAccount = account;
                }
                else if(account.getCurrencyType().equals(Currency.CNY)){
                    CNYAccount = account;
                }
            }
            if(CNYAccount == null || EURAccount == null){
                fail();
            }
            System.out.println(CNYAccount.getBalance());
            System.out.println(EURAccount.getBalance());
            assertEquals(2, output.size());

            assertEquals(200, CNYAccount.getBalance());
            assertEquals(40, EURAccount.getBalance());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getBalance_ifNoAccountExists_shouldThrowException() {
        try {
            AccountList account = new AccountList();
            Method method = BalanceCommand.class.getDeclaredMethod("getAccounts", String.class, AccountList.class);
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance");
            assertThrows(InvocationTargetException.class, ()-> method.invoke(command, "CNY", account));
        } catch (Exception e) {
            fail();
        }
    }
}
