package com.ponkratov.airport.server.view;

import com.ponkratov.airport.server.controller.userhandler.UserThread;
import com.ponkratov.airport.server.controller.userhandler.UserWaiter;
import com.ponkratov.airport.server.exception.PoolException;
import com.ponkratov.airport.server.model.pool.CustomConnectionPool;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerController {
    private static final Logger LOG = LogManager.getLogger();

    @FXML
    public Button startButton;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        if (startButton.getText().equals("Start")) {
            welcomeText.setText("Server started");
            CustomConnectionPool.getInstance();
            LOG.info("Server started");
            new Thread(new UserWaiter()).start();
            startButton.setText("Stop");
        } else {
            UserWaiter.stopServer();
            try {
                CustomConnectionPool.getInstance().destroyPool();
            } catch (PoolException e) {
                LOG.error("Error while destroing pool");
            }
            welcomeText.setText("Server stopped");
            LOG.info("Server stopped");
            startButton.setText("Start");
        }
    }

    @FXML
    static void initialize() {
    }
}