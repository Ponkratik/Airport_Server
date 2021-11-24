package com.ponkratov.airport.server.controller.command;

import com.ponkratov.airport.server.controller.command.impl.AuthenticateUserCommand;
import com.ponkratov.airport.server.controller.command.impl.ChangeUserPasswordCommand;
import com.ponkratov.airport.server.controller.command.impl.FindAllUsersCommand;
import com.ponkratov.airport.server.controller.command.impl.RestoreUserPasswordCommand;

import java.util.EnumMap;

public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private final EnumMap<CommandType, ActionCommand> commands = new EnumMap<>(CommandType.class);

    private CommandProvider() {
        commands.put(CommandType.AUTHENTICATE, new AuthenticateUserCommand());
        commands.put(CommandType.FINDALLUSERS, new FindAllUsersCommand());
        commands.put(CommandType.CHANGEPASSWORD, new ChangeUserPasswordCommand());
        commands.put(CommandType.RESTOREPASSWORD, new RestoreUserPasswordCommand());
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public ActionCommand getCommand(String commandName) {
        CommandType commandType = CommandType.getCommandType(commandName);
        return commands.get(commandType);
    }
}
