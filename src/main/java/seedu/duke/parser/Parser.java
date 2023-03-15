package seedu.duke.parser;


import seedu.duke.commands.*;
import seedu.duke.constants.Message;

/**
 * Class for parsing user input to return the appropriate command
 */
public class Parser {
    /**
     * Parses the user input and returns the appropriate command
     *
     * @param input the user input
     * @return the command to be executed
     * @throws IndexOutOfBoundsException if the user input is invalid
     * @throws IllegalArgumentException  if the user input is invalid
     */
    public static Command parseInput(String input) throws IndexOutOfBoundsException,
            IllegalArgumentException {

        String[] args = input.split(" ");

        CommandType command;
        try {
            command = CommandType.get(args[0]);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(Message.ERR_UNKNOWN_COMMAND.getMessage());
        }


        switch (command) {
        case BALANCE:
            return new BalanceCommand(input);
        case EXIT:
            return new ExitCommand();
        case ADD:
            return new AddCommand(input);
        case SHOW_RATE:
            try {
                return new ShowRateCommand(input);
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(Message.ERR_INVALID_SHOW_RATE.getMessage());
            }
        case EXCHANGE:
            return new ExchangeCommand(input);
        case CREATE_ACCOUNT:
            return new CreateAccountCommand(input);
        case DELETE_ACCOUNT:
            return new DeleteAccountCommand(input);
        case HELP:
            return new HelpCommand(input);
        default:
            throw new IllegalArgumentException(Message.ERR_UNKNOWN_COMMAND.getMessage());
        }
    }
}
