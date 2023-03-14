package seedu.duke;

import seedu.duke.exceptions.InsufficientAccountBalance;
import seedu.duke.exceptions.NoAccountException;

import java.util.HashMap;

public class AccountList {
    // Currency implementation only specifies one account per currency, if required would have to change to a
    // Hashmap of ArrayList of account in the future and randomly generate an ID for that account
    private final HashMap<Currency, Account> accountHashMap;


    public AccountList() {
        accountHashMap = new HashMap<>();
    }

    public void addAccount(Currency currency, float initialBalance) {
        if (accountHashMap.containsKey(currency)) {
            // todo throw some exception signalling account already exists
        }
        accountHashMap.put(currency, new Account((int)initialBalance * 100, currency));
    }


    public void deleteAccount(Currency currency) {
        if (!accountHashMap.containsKey(currency)) {
            // todo throw some exception signalling account already exists
        }
        accountHashMap.remove(currency);
    }

    /**
     * Gets the specific currency from the AccountList.
     * 
     * @param currency The currency account that we would like to retrieve.
     * @return A hashmap containing the currency and the account details.
     * @throws NoAccountException If the account does not exist.
     */
    public HashMap<Currency, Account> getBalance(Currency currency) throws NoAccountException {
        if (!accountHashMap.containsKey(currency)) {
            throw new NoAccountException();
        }
        HashMap<Currency, Account> newMap = new HashMap<>();
        newMap.put(currency, accountHashMap.get(currency));
        return newMap;
    }

    /**
     * Gets all the accounts from the accountList.
     *
     * @return The hashmap containing all the accounts.
     */
    public HashMap<Currency, Account> getAccountHashMap() {
        return accountHashMap;
    }

    /**
     * Retrieves an account for a chosen currency
     * @param currency the currency of the account to be returned
     * @return the currency account
     * @throws NoAccountException if the user does not have an account for that currency
     */
    public Account getAccount(Currency currency) throws NoAccountException {
        if (!this.getAccountHashMap().containsKey(currency)) {
            throw new NoAccountException();
        }
        return this.getAccountHashMap().get(currency);
    }

    public void addAmount(Currency currency, int amount) throws NoAccountException {
        if (!accountHashMap.containsKey(currency)) {
            throw new NoAccountException();
        }

        int currentAmount = (int) accountHashMap.get(currency).getBalance();
        int newBalance = currentAmount + amount;
        accountHashMap.put(currency, new Account(newBalance, currency));
    }

    public int withdrawAmount(int amount,Currency currency) throws NoAccountException, InsufficientAccountBalance{
        if(!accountHashMap.containsKey(currency)){
            throw new NoAccountException();
        }

        int currentAmount = this.getAccount(currency).getBalance();
        int newBalance = currentAmount - amount;
        if(newBalance < 0){
            throw new InsufficientAccountBalance();
        }
        accountHashMap.put(currency, new  Account(newBalance,currency));
        return newBalance;
    }
}
