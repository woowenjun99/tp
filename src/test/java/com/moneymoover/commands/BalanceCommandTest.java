package com.moneymoover.commands;

import com.moneymoover.Account;
import com.moneymoover.AccountList;
import com.moneymoover.Currency;
import com.moneymoover.storage.TestStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class BalanceCommandTest {
    @Test
    public void processCommand_noCurrencySpecified_shouldReturnAll () {
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
    public void processCommand_oneCurrencySpecified_shouldReturnOne () {
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
    public void processCommand_multipleCurrenciesSpecified_shouldThrowError () {
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
    public void convertStringToEnum_invalidCurrency_shouldThrowIllegalArgumentException () {
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
    public void convertStringToEnum_validCurrency_shouldReturnCorrespondingCurrency () {
        try {
            Method method = BalanceCommand.class.getDeclaredMethod("convertStringToEnum", String.class);
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance");
            Assertions.assertEquals(Currency.CNY, method.invoke(command, "CNY"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getBalance_ifCurrencyIsNotSpecified_shouldReturnAllCurrencies () {
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);

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
            Account cnyAccount = null;
            Account eurAccount = null;
            for (Account account : output) {
                if (account.getCurrencyType().equals(Currency.EUR)) {
                    eurAccount = account;
                } else if (account.getCurrencyType().equals(Currency.CNY)) {
                    cnyAccount = account;
                }
            }
            if (cnyAccount == null || eurAccount == null) {
                fail();
            }
            System.out.println(cnyAccount.getBalance());
            System.out.println(eurAccount.getBalance());
            assertEquals(2, output.size());
            assertEquals(200, cnyAccount.getBalance());
            assertEquals(40, eurAccount.getBalance());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getBalance_ifNoAccountExists_shouldThrowException () {
        try {
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            Method method = BalanceCommand.class.getDeclaredMethod("getAccounts", String.class, AccountList.class);
            method.setAccessible(true);
            BalanceCommand command = new BalanceCommand("balance");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, "CNY", accounts));
        } catch (Exception e) {
            fail();
        }
    }
}
