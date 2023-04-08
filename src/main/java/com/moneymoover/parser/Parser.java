package com.moneymoover.parser;


import com.moneymoover.commands.AddCommand;
import com.moneymoover.commands.BalanceCommand;
import com.moneymoover.commands.Command;
import com.moneymoover.commands.CommandType;
import com.moneymoover.commands.CreateAccountCommand;
import com.moneymoover.commands.DeleteAccountCommand;
import com.moneymoover.commands.ExchangeCommand;
import com.moneymoover.commands.ExitCommand;
import com.moneymoover.commands.HelpCommand;
import com.moneymoover.commands.ShowRateCommand;
import com.moneymoover.commands.TransactionCommand;
import com.moneymoover.commands.WithdrawCommand;
import com.moneymoover.constants.ErrorMessage;


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
    public static Command parseInput (String input) throws IndexOutOfBoundsException,
            IllegalArgumentException {

        String[] args = input.split(" ");

        CommandType command;
        try {
            command = CommandType.get(args[0]);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(ErrorMessage.UNKNOWN_COMMAND);
        }


        switch (command) {
        case BALANCE:
            return new BalanceCommand(input);
        case EXIT:
            return new ExitCommand();
        case ADD:
            return new AddCommand(input);
        case WITHDRAW:
            return new WithdrawCommand(input);
        case SHOW_RATE:
            return new ShowRateCommand(input);
        case EXCHANGE:
            return new ExchangeCommand(input);
        case CREATE_ACCOUNT:
            return new CreateAccountCommand(input);
        case DELETE_ACCOUNT:
            return new DeleteAccountCommand(input);
        case HELP:
            return new HelpCommand(input);
        case TRANSACTION:
            return new TransactionCommand(input);
        default:
            throw new IllegalArgumentException(ErrorMessage.UNKNOWN_COMMAND);
        }
    }
}
