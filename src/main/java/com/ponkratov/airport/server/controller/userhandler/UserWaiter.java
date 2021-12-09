package com.ponkratov.airport.server.controller.userhandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UserWaiter implements Runnable{
    private static final Logger LOG = LogManager.getLogger();

    private static boolean isActive = true;

    private static final String SERVER_PROPERTIES = "src/main/java/com/ponkratov/airport/server/controller/userhandler/server.properties";
    private static final int PORT;

    static {
        Properties serverProperties = new Properties();
        try (InputStream propertiesFile = Files.newInputStream(Paths.get(SERVER_PROPERTIES))) {
            serverProperties.load(propertiesFile);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load server properties.");
        }

        PORT = Integer.parseInt(serverProperties.getProperty("port"));
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            while(isActive) {
                Socket s = ss.accept();
                new Thread(new UserThread(s)).start();
            }
            ss.close();
        } catch (Exception e) {
            LOG.fatal("Can not create user thread.", e);
            throw new RuntimeException("Can not create user thread.", e);
        }
    }

    public static void stopServer() {
        UserWaiter.isActive = false;
    }
}
