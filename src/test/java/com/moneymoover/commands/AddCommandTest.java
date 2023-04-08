package com.moneymoover.commands;

import org.junit.jupiter.api.Test;
import com.moneymoover.AccountList;
import com.moneymoover.Currency;
import com.moneymoover.TransactionManager;
import com.moneymoover.storage.TestStore;
import com.moneymoover.ui.Ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddCommandTest {
    @Test
    public void getCurrency_invalidCurrencyProvided_shouldThrowException () {
        try {
            Method method = AddCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY 200");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, "JP"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getCurrency_validCurrencyProvided_shouldReturnCorrespondingCurrency () {
        try {
            Method method = AddCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY 200");
            assertEquals(Currency.JPY, method.invoke(command, "JPY"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_commandLessThanThreeWords_shouldThrowException () {
        try {
            Method method = AddCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_amountNotInt_shouldThrowException () {
        try {
            Method method = AddCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY m");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_correctInputFormat_shouldNotThrowException () {
        try {
            Method method = AddCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            AddCommand command1 = new AddCommand("add JPY 200");
            assertDoesNotThrow(() -> {
                method.invoke(command1);
            });

            AddCommand command2 = new AddCommand("add JPY 200 income");
            assertDoesNotThrow(() -> {
                method.invoke(command2);
            });

            AddCommand command3 = new AddCommand("add JPY 200 part time");
            assertDoesNotThrow(() -> {
                method.invoke(command3);
            });

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void execute_correctInputProvided_shouldUpdateAmount () {
        try {
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            TransactionManager transactions = TransactionManager.getInstance();
            accounts.addAccount(Currency.KRW, 4000.0f);
            AddCommand command = new AddCommand("add KRW 200.00");
            Ui ui = new Ui();
            command.execute(ui, accounts);

            int expectedAmount = (int) accounts.getAccount(Currency.KRW).getBalance();

            assertEquals(4200, expectedAmount);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_amountLessThanZero_shouldThrowException () {
        try {
            Method method = AddCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            AddCommand command = new AddCommand("add JPY -1");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command));

        } catch (Exception e) {
            fail();
        }
    }
}
