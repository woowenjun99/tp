package seedu.duke;

public class ShowRatesCommand /*extends Command*/ {

    private Forex exchangeRate;
    private float amount;

    public ShowRatesCommand (Currency initial, Currency target, int amount) {
        super(false);
        this.amount = amount;
        exchangeRate = new Forex(initial, target);
    }

    public void execute() {
        String from = "1 " + exchangeRate.getInitial();
        String to = exchangeRate.convert(amount) + " " + exchangeRate.getTarget();
        System.out.println(from + " = " + to);
    }
}