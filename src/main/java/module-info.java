module com.ponkratov.airport.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires mysql.connector.java;
    requires com.fasterxml.jackson.databind;
    requires jbcrypt;


    opens com.ponkratov.airport.server.view to javafx.fxml;
    exports com.ponkratov.airport.server.view;

    exports com.ponkratov.airport.server.controller.command;
    exports com.ponkratov.airport.server.model.entity;
}