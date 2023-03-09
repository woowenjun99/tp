package seedu.duke;

public class Account {
    private int balance;
    private Currency currency;

    public int getBalance(){
        return balance;
    }
    public Currency getCurrencyType(){
        return currency;
    }
    public void updateBalance(int changeInBalance){
        int newBalance = balance - changeInBalance;
        if(newBalance < 0){
            // todo throw some exception
        }
        balance = newBalance;
    }

    Account(Currency currency, int initialBalance){
        this.currency = currency;
        balance = initialBalance;
    }


}
