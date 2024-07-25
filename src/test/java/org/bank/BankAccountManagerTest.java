package org.bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountManagerTest {
    @InjectMocks
    BankAccountManager bankAccountManager;
    @Mock
    BankAccountHelper bankAccountHelper;


    @Test
    void transferMoney_Success() throws InvalidBankOperationException {
        double balance = 100.0;
        double dailyLimit = 100.0;
        double amount = 10.0;
        double convertedAmount = amount * ExchangeRates.USD_TO_EUR;

        BankAccount bankAccountFrom = new BankAccount();
        bankAccountFrom.setAccountCurrency(BankAccountHelper.USD_ACCOUNT);
        bankAccountFrom.setDailyLimit(dailyLimit);
        bankAccountFrom.setCurrentBalance(balance);

        BankAccount bankAccountTo = new BankAccount();
        bankAccountTo.setAccountCurrency(BankAccountHelper.EUR_ACCOUNT);
        bankAccountTo.setDailyLimit(dailyLimit);

        when(bankAccountHelper.isBalanceValidForWithdraw(bankAccountFrom, amount, BankAccountHelper.USD_ACCOUNT)).thenReturn(true);
        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom, amount)).thenReturn(true);

        when(bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT,BankAccountHelper.EUR_ACCOUNT, amount)).thenReturn(convertedAmount);

        bankAccountManager.transferMoney(bankAccountFrom, bankAccountTo, amount);

        double expectedBalanceAccountFrom = balance - convertedAmount;
        double expectedDalyLimit = dailyLimit - convertedAmount;

        assertEquals(expectedBalanceAccountFrom , bankAccountFrom.getCurrentBalance());
        assertEquals(expectedDalyLimit , bankAccountTo.getDailyLimit());
    }

    @Test
    void transferMoney_InvalidDailyLimit() {
        double amount = 100.0;

        BankAccount bankAccountFrom = new BankAccount();
        bankAccountFrom.setDailyLimit(0);
        bankAccountFrom.setAccountCurrency(BankAccountHelper.USD_ACCOUNT);

        BankAccount bankAccountTo = new BankAccount();
        bankAccountTo.setAccountCurrency(BankAccountHelper.BYN_ACCOUNT);

        when(bankAccountHelper.isBalanceValidForWithdraw(bankAccountFrom, amount, BankAccountHelper.USD_ACCOUNT)).thenReturn(true);
        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom, amount)).thenReturn(false);

        assertThrows(InvalidBankOperationException.class, () -> bankAccountManager.transferMoney(bankAccountFrom, bankAccountTo, amount));

        verify(bankAccountHelper, never()).convertCurrency(BankAccountHelper.USD_ACCOUNT,BankAccountHelper.BYN_ACCOUNT,amount);
    }
    @Test
    void testTransferMoney_InvalidBalance(){
        BankAccount fromAccount = new BankAccount();
        fromAccount.setAccountCurrency(BankAccountHelper.USD_ACCOUNT);

        BankAccount toAccount = new BankAccount();

        double amount = 50.0;

        when(bankAccountHelper.isBalanceValidForWithdraw(fromAccount, amount, fromAccount.getAccountCurrency())).thenReturn(false);

        assertThrows(InvalidBankOperationException.class, () -> {
            bankAccountManager.transferMoney(fromAccount, toAccount, amount);
        });
    }

    @Test
    void withdrawMoney_Success() throws InvalidBankOperationException {
        double balance = 100.0;
        double dailyLimit = 100.0;
        double amount = 10.0;
        double convertedAmount = ExchangeRates.USD_TO_EUR * amount;

        BankAccount bankAccountFrom = new BankAccount();
        bankAccountFrom.setAccountCurrency(BankAccountHelper.USD_ACCOUNT);
        bankAccountFrom.setDailyLimit(dailyLimit);
        bankAccountFrom.setCurrentBalance(balance);

        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom, amount)).thenReturn(true);
        when(bankAccountHelper.isBalanceValidForWithdraw(bankAccountFrom, amount, BankAccountHelper.USD_ACCOUNT)).thenReturn(true);
        when(bankAccountHelper.convertCurrency(BankAccountHelper.EUR_ACCOUNT,BankAccountHelper.USD_ACCOUNT,amount)).thenReturn(convertedAmount);

        double result = bankAccountManager.withdrawMoney(bankAccountFrom,amount,BankAccountHelper.EUR_ACCOUNT);

        double newBalance = balance - convertedAmount;
        double newDalyLimit = dailyLimit - convertedAmount;

        assertEquals(convertedAmount,result);
        assertEquals(bankAccountFrom.getCurrentBalance(),newBalance);
        assertEquals(bankAccountFrom.getDailyLimit(),newDalyLimit);
    }

    @Test
    void withdrawMoney_InvalidDailyLimit() {
        double amount = 100;
        double dailyLimit = 100;

        BankAccount bankAccountFrom = new BankAccount();
        bankAccountFrom.setAccountCurrency(BankAccountHelper.EUR_ACCOUNT);
        bankAccountFrom.setDailyLimit(dailyLimit);

        when(bankAccountHelper.isBalanceValidForWithdraw(bankAccountFrom,amount,BankAccountHelper.EUR_ACCOUNT)).thenReturn(true);
        when(bankAccountHelper.isAvailableForDailyWithdraw(bankAccountFrom,amount)).thenReturn(false);

        assertThrows(InvalidBankOperationException.class, ()->{
           bankAccountManager.withdrawMoney(bankAccountFrom,amount,BankAccountHelper.USD_ACCOUNT);
        });

        verify(bankAccountHelper, never()).convertCurrency(BankAccountHelper.USD_ACCOUNT,BankAccountHelper.EUR_ACCOUNT,amount);
    }

    @Test
    void withdrawMoney_InvalidBalance() {
        double amount = 100;
        double dailyLimit = 100;

        BankAccount bankAccountFrom = new BankAccount();
        bankAccountFrom.setAccountCurrency(BankAccountHelper.EUR_ACCOUNT);
        bankAccountFrom.setDailyLimit(dailyLimit);

        when(bankAccountHelper.isBalanceValidForWithdraw(bankAccountFrom,amount,BankAccountHelper.EUR_ACCOUNT)).thenReturn(false);

        assertThrows(InvalidBankOperationException.class, ()->{
            bankAccountManager.withdrawMoney(bankAccountFrom,amount,BankAccountHelper.USD_ACCOUNT);
        });

        verify(bankAccountHelper, never()).convertCurrency(BankAccountHelper.USD_ACCOUNT,BankAccountHelper.EUR_ACCOUNT,amount);
    }

    @Test
    void addMoney_Success() {
        double amount = 10.0;
        double balance = 100.0;
        double convertedAmount = amount * ExchangeRates.EUR_TO_BYN;

        BankAccount bankAccountTo = new BankAccount();
        bankAccountTo.setCurrentBalance(balance);
        bankAccountTo.setAccountCurrency(BankAccountHelper.BYN_ACCOUNT);

        when(bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT,bankAccountTo.getAccountCurrency(),amount)).thenReturn(convertedAmount);

        bankAccountManager.addMoney(bankAccountTo,amount,BankAccountHelper.USD_ACCOUNT);

        double expected = balance + convertedAmount;

        assertEquals(expected,bankAccountTo.getCurrentBalance());
    }
}