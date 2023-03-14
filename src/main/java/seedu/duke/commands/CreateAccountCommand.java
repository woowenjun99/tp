package seedu.duke.commands;

import seedu.duke.AccountList;
import seedu.duke.Currency;
import seedu.duke.constants.ErrorMessage;
import seedu.duke.constants.Message;
import seedu.duke.exceptions.AccountAlreadyExistsException;
import seedu.duke.ui.Ui;

public class CreateAccountCommand extends Command{

    CreateAccountCommand(String input){
        super(false, input);
    }

    @Override
    public void execute(Ui ui, AccountList accounts) {
        String[] userInputs = input.split(" ");
        if(userInputs.length != 2 || !userInputs[1].contains("$/")){
            ui.printMessage(ErrorMessage.INVALID_CREATE_ACCOUNT_COMMAND);
        }
        String currencyString = userInputs[1].replace("$/", "");
        Currency currency;
        try{
            currency = Currency.valueOf(currencyString);
            accounts.addAccount(currency, 0.0f);
            ui.printf(Message.SUCCESSFUL_CREATE_ACCOUNT_COMMAND.getMessage(), currency);
        }catch (IllegalArgumentException e){
            ui.printMessage(ErrorMessage.INVALID_CREATE_ACCOUNT_COMMAND);
        }catch (AccountAlreadyExistsException e){
            ui.printMessage(ErrorMessage.ACCOUNT_ALREADY_EXISTS);
        }
    }
}
