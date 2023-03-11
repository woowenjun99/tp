package seedu.duke.parser;

import seedu.duke.commands.Command;
import seedu.duke.commands.CommandType;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.ShowRateCommand;
public class Parser {
    public static Command parseInput(String input) throws IndexOutOfBoundsException, IllegalArgumentException{
        CommandType command;
        String[] args = input.split(" ");

        // trim whitespace and convert to uppercase so input matches the enum names
        command = CommandType.get(args[0]);

        switch(command){
        case EXIT:
            return new ExitCommand();
        case SHOW_RATE:
            return new ShowRateCommand(args[1], args[2]);
        default:
            throw new IllegalArgumentException("Invalid command");
        }
    }
}
