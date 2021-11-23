package com.ponkratov.airport.server.controller.userhandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.ServerSocket;
import java.net.Socket;

public class UserWaiter implements Runnable{
    private static final Logger LOG = LogManager.getLogger();
    private static boolean isActive = true;
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(11111);
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
