package com.moneymoover.commands;

import com.moneymoover.AccountList;
import com.moneymoover.Currency;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.exceptions.NoAccountException;
import com.moneymoover.storage.TestStore;
import com.moneymoover.ui.Ui;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class CreateAccountTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp () {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void execute_correctInputProvided_shouldCreateAccount () {
        try {
            Ui ui = new Ui();
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            Command command = new CreateAccountCommand("create-account EUR");
            command.execute(ui, accounts);
            assertDoesNotThrow(() -> {
                accounts.getAccount(Currency.EUR);
            });
            assertThrows(NoAccountException.class, () -> accounts.getAccount(Currency.USD));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void execute_multipleCurrenciesProvided_shouldThrowException () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        Command command = new CreateAccountCommand("create-account EUR USD");
        command.execute(ui, accounts);
        Assertions.assertEquals(ErrorMessage.INVALID_CREATE_ACCOUNT_COMMAND, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_invalidCurrencyProvided_shouldThrowException () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        Command command = new CreateAccountCommand("create-account XYZ");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.INVALID_CURRENCY, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_currencyAccountAlreadyExists_shouldThrowException () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        Command command = new CreateAccountCommand("create-account EUR");
        command.execute(ui, accounts);
        command.execute(ui, accounts);
        assertEquals("You have successfully added the EUR account\n" +
                ErrorMessage.ACCOUNT_ALREADY_EXISTS, outputStreamCaptor.toString().trim());
    }
}
