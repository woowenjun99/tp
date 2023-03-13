package seedu.duke;

public class Account {
    private int balance;
    private final Currency CURRENCY;

    Account(int initialBalance, Currency currency){
        this.CURRENCY = currency;
        balance = initialBalance;
    }

    public float getBalance(){
        return balance / 100.0f;
    }
    public Currency getCurrencyType(){
        return CURRENCY;
    }
    public void updateBalance(float changeInBalance){
        int newBalance = balance - (int)(changeInBalance * 100);
        if(newBalance < 0){
            // todo throw some exception
        }
        balance = newBalance;
    }

    private static String currencyToString(Currency currency){
        return currency.name();
    }
    @Override
    public String toString(){
        String currencyType = currencyToString(CURRENCY);
        return currencyType + ": " + balance / 100.0f;
    }

}
