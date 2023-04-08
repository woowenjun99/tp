package com.moneymoover.commands;

import com.moneymoover.AccountList;
import com.moneymoover.Currency;
import com.moneymoover.storage.TestStore;
import com.moneymoover.ui.Ui;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class WithdrawCommandTest {
    @Test
    public void getCurrency_invalidCurrency_shouldThrowException () {
        try {
            Method method = WithdrawCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            WithdrawCommand command = new WithdrawCommand("withdraw JPY 200");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, "JP"));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_commandLessThanThreeWords_shouldThrowException () {
        try {
            Method method = WithdrawCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            WithdrawCommand command = new WithdrawCommand("withdraw JPY");
            WithdrawCommand command1 = new WithdrawCommand("withdraw");
            WithdrawCommand command2 = new WithdrawCommand("200 JPY");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command));
            assertThrows(InvocationTargetException.class, () -> method.invoke(command1));
            assertThrows(InvocationTargetException.class, () -> method.invoke(command2));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_negativeWithdrawalAmount_shouldThrowException () {
        try {
            Method method = WithdrawCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            WithdrawCommand command = new WithdrawCommand("withdraw -100 JPY");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_amountNotInt_shouldThrowException () {
        try {
            Method method = WithdrawCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            WithdrawCommand command = new WithdrawCommand("withdraw m JPY");
            WithdrawCommand command1 = new WithdrawCommand("withdraw JPY m");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command));
            assertThrows(InvocationTargetException.class, () -> method.invoke(command1));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getCurrency_validCurrency_shouldReturnCorrespondingCurrency () {
        try {
            Method method = WithdrawCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            WithdrawCommand command = new WithdrawCommand("withdraw JPY 200");
            Assertions.assertEquals(Currency.JPY, method.invoke(command, "JPY"));
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void execute_correctInputProvided_shouldUpdateAmount () {
        try {
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            accounts.addAccount(Currency.KRW, 4000);
            WithdrawCommand command = new WithdrawCommand("withdraw KRW 2000");
            Ui ui = new Ui();
            command.execute(ui, accounts);
            int expectedAmount = (int) accounts.getAccount(Currency.KRW).getBalance();
            assertEquals(2000, expectedAmount);
        } catch (Exception e) {
            fail();
        }
    }
}
