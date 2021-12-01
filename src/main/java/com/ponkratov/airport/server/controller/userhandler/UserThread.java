package com.ponkratov.airport.server.controller.userhandler;

import com.ponkratov.airport.server.controller.command.Request;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandProvider;
import com.ponkratov.airport.server.controller.command.CommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class UserThread implements Runnable{
    private static final Logger LOG = LogManager.getLogger();

    private Socket s;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private ObjectMapper objectMapper;
    private Request request;
    private CommandProvider commandProvider;
    private ActionCommand command;
    private CommandResult commandResult;

    private static int userID;
    private static int roleID;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        UserThread.userID = userID;
    }

    public static int getRoleID() {
        return roleID;
    }

    public static void setRoleID(int roleID) {
        UserThread.roleID = roleID;
    }

    public UserThread(Socket s) throws IOException {
        this.s = s;
        oos = new ObjectOutputStream(this.s.getOutputStream());
        ois = new ObjectInputStream(this.s.getInputStream());
        commandProvider = CommandProvider.getInstance();
        objectMapper = new ObjectMapper();
    }

    public void run() {
        LOG.info("Client " + s.getInetAddress() + ":" + s.getPort() + " connected");

        while (s.isConnected()) {
            try {
                request = objectMapper.readValue((String) ois.readObject(), Request.class);
                command = commandProvider.getCommand(request.getRequestCommand());
                commandResult = command.execute(request.getRequestParams());
                oos.writeObject(objectMapper.writeValueAsString(commandResult));
            } catch (IOException e) {
                if (e.getMessage().equals("Connection reset")) {
                    LOG.info("Client " + s.getInetAddress() + ":" + s.getPort() + " disconnected");
                    break;
                } else {
                    LOG.error("Can not read message from client.", e);
                    throw new RuntimeException("Can not read message from client.", e);
                }
            } catch (ClassNotFoundException e) {
                LOG.error("Request class not found.", e);
                throw new RuntimeException("Request class not found.", e);
            }
        }
    }
}
