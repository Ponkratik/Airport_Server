package com.ponkratov.airport.server.controller.command;

import com.ponkratov.airport.server.controller.command.impl.*;

import java.util.EnumMap;

public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private final EnumMap<CommandType, ActionCommand> commands = new EnumMap<>(CommandType.class);

    private CommandProvider() {
        commands.put(CommandType.AUTHENTICATE, new AuthenticateUserCommand());
        commands.put(CommandType.FINDALLUSERS, new FindAllUsersCommand());
        commands.put(CommandType.CHANGEPASSWORD, new ChangeUserPasswordCommand());
        commands.put(CommandType.RESTOREPASSWORD, new RestoreUserPasswordCommand());
        commands.put(CommandType.REGISTER, new RegisterUserCommand());
        commands.put(CommandType.UPDATEUSER, new UpdateUserCommand());
        commands.put(CommandType.FINDUSERSBYNAMEREGEXP, new FindUsersByNameRegexpCommand());
        commands.put(CommandType.FINDUSERSBYLOGINREGEXP, new FindUsersByLoginRegexpCommand());
        commands.put(CommandType.FINDUSERSBYROLE, new FindUsersByRoleCommand());
        commands.put(CommandType.FINDUSERBYEMAIL, new FindUserByEmailCommand());

        commands.put(CommandType.FINDALLROLES, new FindAllRolesCommand());
        commands.put(CommandType.FINDROLEBYID, new FindRoleByIDCommand());
        commands.put(CommandType.FINDROLEBYNAME, new FindRoleByNameCommand());

        commands.put(CommandType.FINDALLPLANES, new FindAllPlanesCommand());
        commands.put(CommandType.CREATEPLANE, new CreatePlaneCommand());
        commands.put(CommandType.UPDATEPLANE, new UpdatePlaneCommand());
        commands.put(CommandType.FINDPLANEBYNUMBER, new FindPlaneByNumberCommand());
        commands.put(CommandType.FINDPLANESBYMODELREGEXP, new FindPlanesByModelRegexpCommand());
        commands.put(CommandType.FINDPLANEBYID, new FindPlaneByIDCommand());

        commands.put(CommandType.FINDALLFLIGHTSTATUSES, new FindAllFlightStatusesCommand());
        commands.put(CommandType.FINDFLIGHTSTATUSBYID, new FindFlightStatusByIDCommand());
        commands.put(CommandType.FINDFLIGHTSTATUSBYNAME, new FindFlightStatusByStatusNameCommand());

        commands.put(CommandType.CREATEFLIGHT, new CreateFlightCommand());
        commands.put(CommandType.FINDALLFLIGHTS, new FindAllFlightsCommand());
        commands.put(CommandType.FINDDEPARRFLIGHTS, new FindDepArrFlightsCommand());
        commands.put(CommandType.UPDATEFLIGHT, new UpdateFlightCommand());

        commands.put(CommandType.FINDALLAIRPORTS, new FindAllAirportsCommand());
     }

    public static CommandProvider getInstance() {
        return instance;
    }

    public ActionCommand getCommand(String commandName) {
        CommandType commandType = CommandType.getCommandType(commandName);
        return commands.get(commandType);
    }
}
