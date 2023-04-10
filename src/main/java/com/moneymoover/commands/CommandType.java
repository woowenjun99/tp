package com.moneymoover.commands;

import com.moneymoover.constants.ErrorMessage;

public enum CommandType {
    ADD("add"),
    WITHDRAW("withdraw"),
    BALANCE("balance"),
    EXIT("exit"),
    SHOW_RATE("show-rate"),
    EXCHANGE("exchange"),
    CREATE_ACCOUNT("create-account"),
    DELETE_ACCOUNT("delete-account"),
    HELP("help"),
    TRANSACTION("trans");
    private String command;

    CommandType (String command) {
        this.command = command;
    }

    public String getCommand () {
        return command;
    }

    public static CommandType get (String command) {
        if (command == null) {
            throw new NullPointerException("Command is null");
        }
        for (CommandType commandType : CommandType.values()) {
            if (commandType.getCommand().equals(command)) {
                return commandType;
            }
        }
        throw new IllegalArgumentException(ErrorMessage.UNKNOWN_COMMAND);
    }
}
