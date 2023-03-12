package seedu.duke;

import seedu.duke.Account;
import seedu.duke.AccountList;
import seedu.duke.Forex;
import seedu.duke.Currency;
import seedu.duke.commands.Command;
import seedu.duke.ui.Ui;

public class ExchangeCommand extends Command {

    private Forex exchangeRate;
    private float amount;

    public ExchangeCommand (String input) {
        super(false, input);
        this.amount = amount;
        Currency initial = Currency.THB;
        Currency target = Currency.SGD;
        exchangeRate = new Forex(initial, target);
    }

    @Override
    public void execute(Ui ui) {
        // Throw an error if accounts for either currency doesn't exist, or initial doesn't have enough
        exchangeRate.printForex();
        Account oldAcc = AccountList.getAccount(exchangeRate.getInitial());
        oldAcc.updateBalance(amount, "subtract");
        Account newAcc = AccountList.getAccount(exchangeRate.getTarget());
        newAcc.updateBalance(exchangeRate.convert(amount), "add");
    }
}