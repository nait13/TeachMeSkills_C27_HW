package org.bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS )
class BankAccountHelperTest {
    private @InjectMocks BankAccountHelper bankAccountHelper;
    private @Mock BankAccount bankAccount;

    @Test
    void testConvertCurrency_BynToEuro() {
        double amount = 100;
        double expected = amount * ExchangeRates.BYN_TO_EUR;
        double result = bankAccountHelper.convertCurrency(BankAccountHelper.BYN_ACCOUNT , BankAccountHelper.EUR_ACCOUNT, amount);
        assertEquals(expected,result,0.0001);
    }
    @Test
    void testConvertCurrency_BynToUsd() {
        double amount = 100;
        double expected = amount * ExchangeRates.BYN_TO_USD;
        double result = bankAccountHelper.convertCurrency(BankAccountHelper.BYN_ACCOUNT , BankAccountHelper.USD_ACCOUNT, amount);
        assertEquals(expected,result,0.0001);
    }

    @Test
    void testConvertCurrency_EuroToUsd() {
        double amount = 100;
        double expected = 100 * ExchangeRates.EUR_TO_USD;
        double result = bankAccountHelper.convertCurrency(BankAccountHelper.EUR_ACCOUNT , BankAccountHelper.USD_ACCOUNT, amount);
        assertEquals(expected,result,0.0001);
    }
    @Test
    void testConvertCurrency_EuroToByn() {
        double amount = 100;
        double expected = 100 * ExchangeRates.EUR_TO_BYN;
        double result = bankAccountHelper.convertCurrency(BankAccountHelper.EUR_ACCOUNT , BankAccountHelper.BYN_ACCOUNT, amount);
        assertEquals(expected,result,0.0001);
    }
    @Test
    void testConvertCurrency_UsdToByn() {
        double amount = 100;
        double expected = 100 * ExchangeRates.USD_TO_BYN;
        double result = bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT , BankAccountHelper.BYN_ACCOUNT, amount);
        assertEquals(expected,result,0.0001);
    }
    @Test
    void testConvertCurrency_UsdToEuro() {
        double amount = 100;
        double expected = 100 * ExchangeRates.USD_TO_EUR;
        double result = bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT , BankAccountHelper.EUR_ACCOUNT, amount);
        assertEquals(expected,result,0.0001);
    }

    @Test
    void testConvertCurrency_NullFromCurrency() {
        assertThrows(NullPointerException.class,()-> {
            bankAccountHelper.convertCurrency(null, BankAccountHelper.EUR_ACCOUNT, 100);
        });
    }

    @Test
    void testConvertCurrency_NullToCurrency() {
        assertThrows(NullPointerException.class,()-> {
            bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT, null, 100);
        });
    }


    @Test
    void testIsBalanceValidForWithdraw_Valid() {
        when(bankAccount.getAccountCurrency()).thenReturn(BankAccountHelper.USD_ACCOUNT);
        when(bankAccount.getCurrentBalance()).thenReturn(100.00);

        double amount = 100.00;
        String currency = BankAccountHelper.USD_ACCOUNT;

        boolean result = bankAccountHelper.isBalanceValidForWithdraw(bankAccount,amount,currency);
        assertTrue(result);
    }

    @Test
    void testIsBalanceValidForWithdraw_NotValid() {
        when(bankAccount.getAccountCurrency()).thenReturn(BankAccountHelper.USD_ACCOUNT);
        when(bankAccount.getCurrentBalance()).thenReturn(100.00);

        double amount = 300.00;
        String currency = BankAccountHelper.EUR_ACCOUNT;

        boolean result = bankAccountHelper.isBalanceValidForWithdraw(bankAccount,amount,currency);
        assertFalse(result);
    }
    @Test
    void testIsAvailableForDailyWithdraw_Valid() {
        when(bankAccount.getDailyLimit()).thenReturn(100.00);
        double amount = 50;
        boolean result = bankAccountHelper.isAvailableForDailyWithdraw(bankAccount,amount);
        assertTrue(result);
    }
    @Test
    void testIsAvailableForDailyWithdraw_NotValid() {
        when(bankAccount.getDailyLimit()).thenReturn(100.00);
        double amount = 150.00;
        boolean result = bankAccountHelper.isAvailableForDailyWithdraw(bankAccount,amount);
        assertFalse(result);
    }
}