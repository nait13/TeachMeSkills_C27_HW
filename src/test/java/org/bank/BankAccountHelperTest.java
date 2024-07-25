package org.bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS )
class BankAccountHelperTest {
    @InjectMocks BankAccountHelper bankAccountHelper;

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
        double expected = amount * ExchangeRates.EUR_TO_USD;
        double result = bankAccountHelper.convertCurrency(BankAccountHelper.EUR_ACCOUNT , BankAccountHelper.USD_ACCOUNT, amount);
        assertEquals(expected,result,0.0001);
    }
    @Test
    void testConvertCurrency_EuroToByn() {
        double amount = 100;
        double expected = amount * ExchangeRates.EUR_TO_BYN;
        double result = bankAccountHelper.convertCurrency(BankAccountHelper.EUR_ACCOUNT , BankAccountHelper.BYN_ACCOUNT, amount);
        assertEquals(expected,result,0.0001);
    }
    @Test
    void testConvertCurrency_UsdToByn() {
        double amount = 100;
        double expected = amount * ExchangeRates.USD_TO_BYN;
        double result = bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT , BankAccountHelper.BYN_ACCOUNT, amount);
        assertEquals(expected,result,0.0001);
    }
    @Test
    void testConvertCurrency_UsdToEuro() {
        double amount = 100;
        double expected = amount * ExchangeRates.USD_TO_EUR;
        double result = bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT , BankAccountHelper.EUR_ACCOUNT, amount);
        assertEquals(expected,result,0.0001);
    }

    @Test
    void testConvertCurrency_NegativeAmount()
    {
      assertThrows(IllegalArgumentException.class, ()->{
         bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT,BankAccountHelper.EUR_ACCOUNT,-1);
      });
    }

    @Test
    void testConvertCurrency_ZeroAmount()
    {
        assertThrows(IllegalArgumentException.class, ()->{
            bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT,BankAccountHelper.EUR_ACCOUNT,0);
        });
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
        double amount = 60.0;

        BankAccount account = new BankAccount();
        account.setCurrentBalance(100.0);
        account.setAccountCurrency(BankAccountHelper.USD_ACCOUNT);


        boolean result = bankAccountHelper.isBalanceValidForWithdraw(account,amount,BankAccountHelper.USD_ACCOUNT);

        assertTrue(result);
    }

    @Test
    void testIsBalanceValidForWithdraw_NotValid() {
        BankAccount account = new BankAccount();
        account.setCurrentBalance(100.0);
        account.setAccountCurrency(BankAccountHelper.USD_ACCOUNT);

        double amount = 110.0;

        boolean result = bankAccountHelper.isBalanceValidForWithdraw(account,amount,BankAccountHelper.USD_ACCOUNT);

        assertFalse(result);
    }

    @Test
    void testIsAvailableForDailyWithdraw_Valid() {
        double dailyLimit = 100.0;
        double amount = 80.0;

        BankAccount account = new BankAccount();
        account.setDailyLimit(dailyLimit);

        boolean result = bankAccountHelper.isAvailableForDailyWithdraw(account,amount);

        assertTrue(result);
    }
    @Test
    void testIsAvailableForDailyWithdraw_NotValid() {
        double dailyLimit = 100.0;
        double amount = 110.0;

        BankAccount account = new BankAccount();
        account.setDailyLimit(dailyLimit);

        boolean result = bankAccountHelper.isAvailableForDailyWithdraw(account,amount);

        assertFalse(result);
    }
}