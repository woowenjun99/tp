package com.moneymoover.commands;

import com.moneymoover.AccountList;
import com.moneymoover.Currency;
import com.moneymoover.Transaction;
import com.moneymoover.TransactionManager;
import com.moneymoover.constants.DateConstants;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.constants.Message;
import com.moneymoover.storage.TestStore;
import com.moneymoover.ui.Ui;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class TransactionCommandTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp () {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    private boolean isWindows () {
        return System.getProperty("os.name").contains("Windows");
    }

    @Test
    public void execute_successPrintAll_shouldPrintAllTransactions () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        Transaction transaction = new Transaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans");
        command.execute(ui, accounts);
        if (isWindows()) {
            assertEquals(Message.SHOW_ALL_TRANSACTIONS_HEADER.getMessage() + "\r\n" +
                    transaction.toString(), outputStreamCaptor.toString().trim());
        } else {
            assertEquals(Message.SHOW_ALL_TRANSACTIONS_HEADER.getMessage() + "\n" +
                    transaction.toString(), outputStreamCaptor.toString().trim());
        }
    }

    @Test
    public void execute_noTransactionsRecorded_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        Command command = new TransactionCommand("trans");
        command.execute(ui, accounts);
        Assertions.assertEquals(ErrorMessage.NO_TRANSACTIONS_RECORDED, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_invalidFlag_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        Command command = new TransactionCommand("trans duke");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.INVALID_TRANSACTION_FLAG, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_successfulDescSearch_shouldPrintRelevantTransactions () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        Transaction transaction = new Transaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans desc broccoli");
        command.execute(ui, accounts);
        if (isWindows()) {
            assertEquals(Message.SHOW_TRANSACTIONS_OF_DESC_HEADER.getMessage() + "broccoli:" +
                    "\r\n" + transaction.toString(), outputStreamCaptor.toString().trim());
        } else {
            assertEquals(Message.SHOW_TRANSACTIONS_OF_DESC_HEADER.getMessage() + "broccoli:" +
                    "\n" + transaction.toString(), outputStreamCaptor.toString().trim());
        }
    }

    @Test
    public void execute_invalidDescSearchParameters_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        Command command = new TransactionCommand("trans desc");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.INVALID_TRANSACTIONS_OF_DESC, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_noMatchingDescription_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans desc chicken");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.NO_TRANSACTIONS_FOUND, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_invalidDateSearchParameters_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy chicken rice",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans d 01");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.INVALID_TRANSACTIONS_OF_DATE, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_successfulDateSearch_shouldPrintRelevantTransactions () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        Transaction transaction = new Transaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        String dateString = LocalDate.now().format(DateConstants.INPUT_DATE_FORMATTER);
        Command command = new TransactionCommand("trans d " + dateString);
        command.execute(ui, accounts);
        if (isWindows()) {
            assertEquals(Message.SHOW_TRANSACTIONS_OF_DATE_HEADER.getMessage() + dateString + ":" +
                    "\r\n" + transaction.toString(), outputStreamCaptor.toString().trim());
        } else {
            assertEquals(Message.SHOW_TRANSACTIONS_OF_DATE_HEADER.getMessage() + dateString + ":" +
                    "\n" + transaction.toString(), outputStreamCaptor.toString().trim());
        }
    }

    @Test
    public void execute_notEnoughDateSearchParameters_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);

        Command command = new TransactionCommand("trans d");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.INVALID_TRANSACTIONS_OF_DATE, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_noMatchingDate_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy chicken rice",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans d 01-02-2023");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.NO_TRANSACTIONS_FOUND, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_successfulCurrencySearch_shouldPrintRelevantTransactions () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        Transaction transaction = new Transaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans c SGD");
        command.execute(ui, accounts);
        if (isWindows()) {
            assertEquals(Message.SHOW_TRANSACTIONS_OF_CURRENCY_HEADER.getMessage() + "SGD:" + "\r\n" +
                    transaction.toString(), outputStreamCaptor.toString().trim());
        } else {
            assertEquals(Message.SHOW_TRANSACTIONS_OF_CURRENCY_HEADER.getMessage() + "SGD:" + "\n" +
                    transaction.toString(), outputStreamCaptor.toString().trim());
        }
    }

    @Test
    public void execute_invalidCurrencySearchParameters_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy chicken rice",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans c POL");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.INVALID_CURRENCY, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_notEnoughCurrencySearchParameters_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);

        Command command = new TransactionCommand("trans c");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.INVALID_TRANSACTIONS_OF_CURRENCY, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_noMatchingCurrency_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy chicken rice",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans c USD");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.NO_TRANSACTIONS_FOUND, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_successfulMonthSearch_shouldPrintRelevantTransactions () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        Transaction transaction = new Transaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy broccoli",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        String dateString = LocalDate.now().format(DateConstants.INPUT_DATE_FORMATTER);
        Command command = new TransactionCommand("trans m " + dateString.substring(3));
        command.execute(ui, accounts);
        if (isWindows()) {
            assertEquals(Message.SHOW_TRANSACTIONS_OF_MONTH_HEADER.getMessage() + dateString.substring(3) +
                    ":" + "\r\n" + transaction.toString(), outputStreamCaptor.toString().trim());
        } else {
            assertEquals(Message.SHOW_TRANSACTIONS_OF_MONTH_HEADER.getMessage() + dateString.substring(3) +
                    ":" + "\n" + transaction.toString(), outputStreamCaptor.toString().trim());
        }
    }

    @Test
    public void execute_invalidMonthSearchParameters_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy chicken rice",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans m June 2023");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.INVALID_TRANSACTIONS_OF_MONTH, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_notEnoughMonthSearchParameters_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy chicken rice",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans m");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.INVALID_TRANSACTIONS_OF_MONTH, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_noMatchingMonth_shouldPrintError () {
        Ui ui = new Ui();
        TestStore store = new TestStore();
        AccountList accounts = new AccountList(store);
        TransactionManager.getInstance().populateTransactions(new ArrayList<>());
        TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy chicken rice",
                false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
        Command command = new TransactionCommand("trans m 02-2023");
        command.execute(ui, accounts);
        assertEquals(ErrorMessage.NO_TRANSACTIONS_FOUND, outputStreamCaptor.toString().trim());
    }

    @Test
    public void printTransactionsByFlag_invalidFlag_shouldThrowException () {
        try {
            Ui ui = new Ui();
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            Method method = TransactionCommand.class.getDeclaredMethod("printTransactionsByFlag",
                    Ui.class, String[].class, String.class);
            method.setAccessible(true);
            Command command = new TransactionCommand("trans duke");
            String[] args = {"trans", "duke"};
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, ui, args, "duke"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void printTransactionsByFlag_notEnoughParametersForDescSearch_shouldThrowException () {
        try {
            Ui ui = new Ui();
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            Method method = TransactionCommand.class.getDeclaredMethod("printTransactionsByFlag",
                    Ui.class, String[].class, String.class);
            method.setAccessible(true);
            Command command = new TransactionCommand("trans desc");
            String[] args = {"trans", "desc"};
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, ui, args, "desc"));
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void printTransactionsByFlag_notEnoughParametersForDateSearch_shouldThrowException () {
        try {
            Ui ui = new Ui();
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            Method method = TransactionCommand.class.getDeclaredMethod("printTransactionsByFlag",
                    Ui.class, String[].class, String.class);
            method.setAccessible(true);
            Command command = new TransactionCommand("trans d");
            String[] args = {"trans", "d"};
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, ui, args, "d"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void printTransactionsByFlag_invalidParametersForDateSearch_shouldThrowException () {
        try {
            Ui ui = new Ui();
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            TransactionManager.getInstance().populateTransactions(new ArrayList<>());
            TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy chicken rice",
                    false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
            Method method = TransactionCommand.class.getDeclaredMethod("printTransactionsByFlag",
                    Ui.class, String[].class, String.class);
            method.setAccessible(true);
            Command command = new TransactionCommand("trans d 20th June 2000");
            String[] args = {"trans", "d", "20th", "June", "2000"};
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, ui, args, "d"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void printTransactionsByFlag_notEnoughParametersForCurrencySearch_shouldThrowException () {
        try {
            Ui ui = new Ui();
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            Method method = TransactionCommand.class.getDeclaredMethod("printTransactionsByFlag",
                    Ui.class, String[].class, String.class);
            method.setAccessible(true);
            Command command = new TransactionCommand("trans c");
            String[] args = {"trans", "c"};
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, ui, args, "c"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void printTransactionsByFlag_invalidParametersForCurrencySearch_shouldThrowException () {
        try {
            Ui ui = new Ui();
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            Method method = TransactionCommand.class.getDeclaredMethod("printTransactionsByFlag",
                    Ui.class, String[].class, String.class);
            method.setAccessible(true);
            Command command = new TransactionCommand("trans c POL");
            String[] args = {"trans", "c", "POL"};
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, ui, args, "c"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void printTransactionsByFlag_notEnoughParametersForMonthSearch_shouldThrowException () {
        try {
            Ui ui = new Ui();
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            Method method = TransactionCommand.class.getDeclaredMethod("printTransactionsByFlag",
                    Ui.class, String[].class, String.class);
            method.setAccessible(true);
            Command command = new TransactionCommand("trans m");
            String[] args = {"trans", "m"};
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, ui, args, "m"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void printTransactionsByFlag_invalidParametersForMonthSearch_shouldThrowException () {
        try {
            Ui ui = new Ui();
            TestStore store = new TestStore();
            AccountList accounts = new AccountList(store);
            TransactionManager.getInstance().populateTransactions(new ArrayList<>());
            TransactionManager.getInstance().addTransaction(Currency.SGD, "Buy chicken rice",
                    false, BigDecimal.valueOf(5), BigDecimal.valueOf(10));
            Method method = TransactionCommand.class.getDeclaredMethod("printTransactionsByFlag",
                    Ui.class, String[].class, String.class);
            method.setAccessible(true);
            Command command = new TransactionCommand("trans m JUNE 2023");
            String[] args = {"trans", "m", "JUNE", "2023"};
            assertThrows(InvocationTargetException.class, () -> method.invoke(command, ui, args, "m"));
        } catch (Exception e) {
            fail();
        }
    }
}
