package seedu.duke;

import seedu.duke.exceptions.NotEnoughInAccountException;

public class Account {
    private int balance;
    private Currency currency;

    Account(int initialBalance, Currency currency){
        this.currency = currency;
        balance = initialBalance;
    }

    public float getBalance(){
        return balance / 100.0f;
    }
    public Currency getCurrencyType(){
        return currency;
    }

    /**
     * Updates the balance of the account
     * @param changeInBalance the amount added or subtract from the account
     * @param action specifies whether to add or subtract value
     * @throws NotEnoughInAccountException if the balance would become negative
     */
    public void updateBalance(float changeInBalance, String action) throws NotEnoughInAccountException {
        int newBalance;
        if (action.equals("add")) {
            newBalance = balance + (int)(changeInBalance * 100);
        } else if (action.equals("subtract")) {
            newBalance = balance - (int)(changeInBalance * 100);
        } else {
            System.out.println("Error in updating balance");
            return;
        }
        if(newBalance < 0){
            throw new NotEnoughInAccountException();
        } 
        balance = newBalance;
    }

    private static String currencyToString(Currency currency){
        return currency.name();
    }

    @Override
    public String toString(){
        String currencyType = currencyToString(currency);
        return currencyType + ": " + balance / 100.0f;
    }

}
