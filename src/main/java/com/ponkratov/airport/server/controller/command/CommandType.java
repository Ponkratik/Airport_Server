package com.ponkratov.airport.server.controller.command;

public enum CommandType {
    REGISTER,
    AUTHENTICATE,
    LOGOUT,

    FINDALLUSERS,
    CHANGEPASSWORD,
    RESTOREPASSWORD,
    BLOCKUSER,
    UPDATEUSERROLE,
    UPDATEUSER,
    FINDUSERSBYLOGINREGEXP,
    FINDUSERBYEMAIL,
    FINDUSERSBYROLE,
    FINDUSERSBYNAMEREGEXP,

    FINDALLROLES,
    FINDROLEBYID,
    FINDROLEBYNAME,

    FINDALLPLANES,
    CREATEPLANE,
    UPDATEPLANE,
    DELETEPLANE,
    FINDPLANEBYNUMBER,
    FINDPLANESBYMODELREGEXP,
    FINDPLANEBYID,

    FINDALLFLIGHTSTATUSES,
    FINDFLIGHTSTATUSBYNAME,
    FINDFLIGHTSTATUSBYID,

    FINDALLFLIGHTS,
    FINDDEPARRFLIGHTS,
    CREATEFLIGHT,
    UPDATEFLIGHT,

    FINDALLAIRPORTS,

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
