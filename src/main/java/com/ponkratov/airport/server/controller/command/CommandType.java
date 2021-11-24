package com.ponkratov.airport.server.controller.command;

public enum CommandType {
    REGISTER,
    AUTHENTICATE,
    LOGOUT,
    FINDALLUSERS,
    CHANGEPASSWORD,
    RESTOREPASSWORD,
    DEFAULT;
    //TODO: Сделать команду для DEFAULT

    public static CommandType getCommandType(String commandName) {
        if (commandName == null) {
            return DEFAULT;
        }
        CommandType commandType;
        try {
            commandType = valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = DEFAULT;
        }
        return commandType;
    }
}
