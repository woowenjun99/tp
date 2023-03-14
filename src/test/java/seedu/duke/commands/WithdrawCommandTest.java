package seedu.duke.commands;

import org.junit.jupiter.api.Test;
import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.ui.Ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class WithdrawCommandTest {
    @Test
    public void getCurrency_invalidCurrency_shouldThrowException(){
        try {
            Method method = WithdrawCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            WithdrawCommand command = new WithdrawCommand("add JPY 200");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, "JP"));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_commandLessThanThreeWords_shouldThrowException() {
        try {
            Method method = WithdrawCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            WithdrawCommand command = new WithdrawCommand("add JPY");
            WithdrawCommand command1 = new WithdrawCommand("add");
            WithdrawCommand command2 = new WithdrawCommand("200 JPY");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command));
            assertThrows(InvocationTargetException.class, () -> method.invoke(command1));
            assertThrows(InvocationTargetException.class, () -> method.invoke(command2));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_negativeWithdrawalAmount_shouldThrowException() {
        try {
            Method method = WithdrawCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            WithdrawCommand command = new WithdrawCommand("add -100 JPY");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void processCommand_amountNotInt_shouldThrowException() {
        try {
            Method method = WithdrawCommand.class.getDeclaredMethod("processCommand");
            method.setAccessible(true);
            WithdrawCommand command = new WithdrawCommand("add m JPY");
            WithdrawCommand command1 = new WithdrawCommand("add JPY m");
            assertThrows(InvocationTargetException.class, () -> method.invoke(command));
            assertThrows(InvocationTargetException.class, () -> method.invoke(command1));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getCurrency_validCurrency_shouldReturnCorrespondingCurrency(){
        try {
            Method method = WithdrawCommand.class.getDeclaredMethod("getCurrency", String.class);
            method.setAccessible(true);
            WithdrawCommand command = new WithdrawCommand("add JPY 200");
            assertEquals(Currency.JPY,method.invoke(command, "JPY"));
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void execute_correctInputProvided_shouldUpdateAmount(){
        try {
            AccountList accounts = new AccountList();
            accounts.addAccount(Currency.KRW, 4000);
            WithdrawCommand command = new WithdrawCommand("withdraw 2000 KRW");
            Ui ui = new Ui();
            command.execute(ui, accounts);
            int expectedAmount =  (int) accounts.getBalance(Currency.KRW).get(Currency.KRW).getBalance();
            assertEquals(2000.00, expectedAmount);
        } catch (Exception e) {
            fail();
        }
    }
}
