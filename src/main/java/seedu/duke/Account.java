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
        String currencyType;
        switch (currency){
        case SGD:
            currencyType = "SGD";
            break;
        case USD:
            currencyType = "USD";
            break;
        case CNY:
            currencyType = "CNY";
            break;
        case EUR:
            currencyType = "EUR";
            break;
        case GBP:
            currencyType = "GBP";
            break;
        case IDR:
            currencyType = "IDR";
            break;
        case JPY:
            currencyType = "JPY";
            break;
        case KRW:
            currencyType = "KRW";
            break;
        case MYR:
            currencyType = "MYR";
            break;
        case THB:
            currencyType = "THB";
            break;
        case VND:
            currencyType = "VND";
            break;
        default:
            // todo throw some error for undefined or unhandled currency type
            currencyType = "";
        }
        return currencyType;
    }
    @Override
    public String toString(){
        String currencyType = currencyToString(CURRENCY);
        return currencyType + ": " + Float.toString(balance / 100.0f);
    }

}
