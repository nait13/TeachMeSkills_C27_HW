package org.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountManagerTest {
    @InjectMocks
    BankAccountManager bankAccountManager;
    @Mock
    BankAccountHelper bankAccountHelper;
    @Mock
    BankAccount bankAccountFrom;
    @Mock
    BankAccount bankAccountTo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void transferMoney_Success() throws InvalidBankOperationException {
        double amount = 100.0;
        double convertedAmount = 120.0;

        when(bankAccountFrom.getAccountCurrency()).thenReturn(BankAccountHelper.USD_ACCOUNT);
        when(bankAccountTo.getAccountCurrency()).thenReturn(BankAccountHelper.EUR_ACCOUNT);
        when(bankAccountFrom.getDailyLimit()).thenReturn(500.0);
        when(bankAccountFrom.getCurrentBalance()).thenReturn(1000.0);
        when(bankAccountTo.getDailyLimit()).thenReturn(300.0);

        when(bankAccountHelper.isBalanceValidForWithdraw(bankAccountFrom, amount, BankAccountHelper.USD_ACCOUNT)).thenReturn(true);
        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom, amount)).thenReturn(true);
        when(bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT, BankAccountHelper.EUR_ACCOUNT, amount)).thenReturn(convertedAmount);

        bankAccountManager.transferMoney(bankAccountFrom, bankAccountTo, amount);

        verify(bankAccountFrom).setCurrentBalance(1000.0 - convertedAmount);
        verify(bankAccountFrom).setDailyLimit(500.0 - amount);
        verify(bankAccountTo).setDailyLimit(300 - convertedAmount);
    }

    @Test
    void transferMoney_DailyLimitFalseORAvailableForDailyWithdrawFalse() {
        double amount = 444.0;
        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom, amount)).thenReturn(false);
        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom, amount)).thenReturn(true);

        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom, amount)).thenReturn(true);
        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom, amount)).thenReturn(false);

        assertThrows(InvalidBankOperationException.class, () -> bankAccountManager.transferMoney(bankAccountFrom, bankAccountTo, amount));

        verify(bankAccountFrom, never()).setCurrentBalance(anyDouble());
        verify(bankAccountFrom, never()).setDailyLimit(anyDouble());
        verify(bankAccountTo, never()).setCurrentBalance(anyDouble());
    }


    @Test
    void withdrawMoney_Success() throws InvalidBankOperationException {
        double amount = 100.0;
        double convertedAmount = ExchangeRates.USD_TO_EUR * amount;
        when(bankAccountFrom.getAccountCurrency()).thenReturn(BankAccountHelper.USD_ACCOUNT);
        when(bankAccountFrom.getDailyLimit()).thenReturn(500.00);
        when(bankAccountFrom.getCurrentBalance()).thenReturn(1000.0);

        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom, amount)).thenReturn(true);
        when(bankAccountHelper.isBalanceValidForWithdraw(bankAccountFrom, amount, BankAccountHelper.USD_ACCOUNT)).thenReturn(true);
        when(bankAccountHelper.convertCurrency(BankAccountHelper.EUR_ACCOUNT,BankAccountHelper.USD_ACCOUNT,amount)).thenReturn(convertedAmount);

        double result = bankAccountManager.withdrawMoney(bankAccountFrom,amount,BankAccountHelper.EUR_ACCOUNT);

        verify(bankAccountFrom).setCurrentBalance(1000.0 - convertedAmount);
        verify(bankAccountFrom).setDailyLimit(500.0 - convertedAmount);

        assertEquals(convertedAmount,result);
    }

    @Test
    void withdrawMoney_ExceedsDailyLimit() {
        when(bankAccountHelper.isBalanceValidForWithdraw(bankAccountFrom,100.0,BankAccountHelper.EUR_ACCOUNT)).thenReturn(true);
        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom,100.0)).thenReturn(false);

        assertThrows(InvalidBankOperationException.class, ()->{
           bankAccountManager.withdrawMoney(bankAccountFrom,100.0,BankAccountHelper.USD_ACCOUNT);
        });

        verify(bankAccountFrom, never()).setCurrentBalance(anyDouble());
        verify(bankAccountFrom, never()).setDailyLimit(anyDouble());
    }

    @Test
    void withdrawMoney_InsufficientBalance() {
        double amount = 100.0;

        when(bankAccountFrom.getAccountCurrency()).thenReturn(BankAccountHelper.USD_ACCOUNT);
        when(bankAccountHelper.isBalanceValidForWithdraw(bankAccountFrom, amount, BankAccountHelper.USD_ACCOUNT)).thenReturn(false);

        assertThrows(InvalidBankOperationException.class, () -> {
            bankAccountManager.withdrawMoney(bankAccountFrom, amount, BankAccountHelper.USD_ACCOUNT);
        });

        verify(bankAccountFrom, never()).setCurrentBalance(anyDouble());
        verify(bankAccountFrom, never()).setDailyLimit(anyDouble());
    }

    @Test
    void addMoney_Success() {
        double amount = 20;
        double convertedAmount = 100;

        when(bankAccountTo.getCurrentBalance()).thenReturn(1000.0);
        when(bankAccountTo.getAccountCurrency()).thenReturn(BankAccountHelper.USD_ACCOUNT);
        when(bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT,BankAccountHelper.USD_ACCOUNT,amount)).thenReturn(convertedAmount);

        bankAccountManager.addMoney(bankAccountTo,amount,BankAccountHelper.USD_ACCOUNT);

        verify(bankAccountTo).setCurrentBalance(1000.0 + convertedAmount);
    }
}