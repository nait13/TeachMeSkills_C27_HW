package org.bank;

public class BankAccountHelper
{
    public static String USD_ACCOUNT = "USD";
    public static String EUR_ACCOUNT = "EUR";
    public static String BYN_ACCOUNT = "BYN";

    public double convertCurrency(String fromCurrency, String toCurrency, double amount) throws IllegalArgumentException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        double result = amount;
        if (fromCurrency.equals(USD_ACCOUNT))
        {
            if (toCurrency.equals(EUR_ACCOUNT))
            {
                result = amount * ExchangeRates.USD_TO_EUR;
            }
            else if (toCurrency.equals(BYN_ACCOUNT))
            {
                result = amount * ExchangeRates.USD_TO_BYN;
            }
        }
        else if (fromCurrency.equals(EUR_ACCOUNT))
        {
            if (toCurrency.equals(USD_ACCOUNT))
            {
                result = amount * ExchangeRates.EUR_TO_USD;
            }
            else if (toCurrency.equals(BYN_ACCOUNT))
            {
                result = amount * ExchangeRates.EUR_TO_BYN;
            }
        }
        else if (fromCurrency.equals(BYN_ACCOUNT))
        {
            if (toCurrency.equals(USD_ACCOUNT))
            {
                result = amount * ExchangeRates.BYN_TO_USD;
            }
            else if (toCurrency.equals(EUR_ACCOUNT))
            {
                result = amount * ExchangeRates.BYN_TO_EUR;
            }
        }

        return result;
    }

    public boolean isBalanceValidForWithdraw(BankAccount account, double amount, String currency)
    {
        double desiredAmount = convertCurrency(currency, account.getAccountCurrency(), amount);

        return (account.getCurrentBalance() - desiredAmount) >= 0;
    }

    public boolean isAvailableForDailyWithdraw(BankAccount account, double amount)
    {
        return (account.getDailyLimit() - amount) >= 0;
    }
}
