package seedu.duke;

import java.util.HashMap;

public class AccountList {
    // Currency implementation only specifies one account per currency, if required would have to change to a
    // Hashmap of ArrayList of account in the future and randomly generate an ID for that account
    private HashMap<Currency, Account> accountHashMap;

    public void addAccount(Currency currency, int initialBalance){
        if(accountHashMap.containsKey(currency)){
            // todo throw some exception signalling account already exists
        }
        accountHashMap.put(currency, new Account(initialBalance, currency));
    }

    public void deleteAccount(Currency currency){
        if(!accountHashMap.containsKey(currency)){
            // todo throw some exception signalling account already exists
        }
        accountHashMap.remove(currency);
    }

    public float getBalance(Currency currency){
        if(!accountHashMap.containsKey(currency)){
            // todo throw some exception signalling no such account
        }
        return accountHashMap.get(currency).getBalance();
    }
}
