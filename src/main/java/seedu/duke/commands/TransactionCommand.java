package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.TransactionManager;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.constants.Message;
import seedu.duke.exceptions.NoTransactionsRecordedException;
import seedu.duke.ui.Ui;

/**
 * Command to print the transactions performed by the user
 */
public class TransactionCommand extends Command {
    public TransactionCommand (String input) {
        super(false, input);
    }

    /**
     * Prints the transactions made by the user
     * Optional parameters allow the user to search by date, month, description or currency
     *
     * @param ui       The instance of the UI class
     * @param accounts The instance of the AccountList class
     */
    @Override
    public void execute (Ui ui, AccountList accounts) {
        String[] args = input.split(" ");
        TransactionManager transactionManager = TransactionManager.getInstance();
        try {
            if (args.length == 1) {
                // Print all transactions
                String transactionsString = transactionManager.getAllTransactionsString();
                ui.printMessage(Message.SHOW_ALL_TRANSACTIONS_HEADER.getMessage());
                ui.printMessage(transactionsString);
                return;
            }
            // At this point there is an additional flag in user input
            String flag = args[1];
            switch (flag) {
            case TransactionFlag.TRANSACTION_DESC_FLAG:
            case TransactionFlag.TRANSACTION_MONTH_FLAG:
            case TransactionFlag.TRANSACTION_DATE_FLAG:
            case TransactionFlag.TRANSACTION_CURRENCY_FLAG:
            }
        } catch (NoTransactionsRecordedException e) {
            ui.printMessage(ErrorMessage.NO_TRANSACTIONS_RECORDED);
        }
    }

}
