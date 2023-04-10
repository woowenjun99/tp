package com.moneymoover.commands;

import com.moneymoover.exceptions.InvalidBigDecimalException;
import com.moneymoover.exceptions.InvalidExchangeArgumentException;
import com.moneymoover.exceptions.ExchangeSameCurrencyException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

public class ExchangeCommandTest {

    @Test
    public void testParseAmount_nonNumericInput_shouldThrowInvalidBigDecimalException () {
        try {
            ExchangeCommand cmd = new ExchangeCommand("exchange THB SGD xyz");
            assertThrows(InvalidBigDecimalException.class, cmd::parseAmount);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testParseAmount_negativeInput_shouldThrowInvalidBigDecimalException () {
        try {
            ExchangeCommand cmd = new ExchangeCommand("exchange THB SGD -1.0");
            assertThrows(InvalidBigDecimalException.class, cmd::parseAmount);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testFormatInput_incorrectSyntax_shouldThrowInvalidExchangeArgumentException () {
        try {
            ExchangeCommand cmd1 = new ExchangeCommand("exchange THB SGD 1.0 2.0");
            ExchangeCommand cmd2 = new ExchangeCommand("exchange THB SGD");
            ExchangeCommand cmd3 = new ExchangeCommand("exchange THB");
            ExchangeCommand cmd4 = new ExchangeCommand("exchange");
            assertThrows(InvalidExchangeArgumentException.class, cmd1::formatInput);
            assertThrows(InvalidExchangeArgumentException.class, cmd2::formatInput);
            assertThrows(InvalidExchangeArgumentException.class, cmd3::formatInput);
            assertThrows(InvalidExchangeArgumentException.class, cmd4::formatInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testFormatInput_invalidCurrency_shouldThrowIllegalArgumentException () {
        try {
            ExchangeCommand cmd = new ExchangeCommand("exchange THB XYZ 1.0");
            assertThrows(IllegalArgumentException.class, cmd::formatInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testFormatInput_correctSyntax_shouldNotThrow () {
        try {
            ExchangeCommand cmd = new ExchangeCommand("exchange THB SGD 1.0");
            assertDoesNotThrow(cmd::formatInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testFormatInput_sameCurrency_shouldThrowExchangeSameCurrencyException () {
        try {
            ExchangeCommand cmd1 = new ExchangeCommand("exchange THB THB 1.0");
            ExchangeCommand cmd2 = new ExchangeCommand("exchange SGD SGD 1.0");
            ExchangeCommand cmd3 = new ExchangeCommand("exchange MYR MYR 1.0");
            assertThrows(ExchangeSameCurrencyException.class, cmd1::formatInput);
            assertThrows(ExchangeSameCurrencyException.class, cmd2::formatInput);
            assertThrows(ExchangeSameCurrencyException.class, cmd3::formatInput);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testParseAmount_correctSyntax_shouldNotThrow () {
        try {
            ExchangeCommand cmd = new ExchangeCommand("exchange THB SGD 1.0");
            assertDoesNotThrow(cmd::parseAmount);
        } catch (Exception e) {
            fail();
        }
    }
}

