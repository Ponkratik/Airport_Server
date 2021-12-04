package com.ponkratov.airport.server.model.pool;

import com.ponkratov.airport.server.exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Logger LOG = LogManager.getLogger();

    private static final String DATABASE_PROPERTIES = "src/main/java/com/ponkratov/airport/server/model/pool/database.properties";
    private static final String DATABASE_URL;
    private static final String DATABASE_USERNAME;
    private static final String DATABASE_PASSWORD;

    static {
        Properties databaseProperties = new Properties();
        try (InputStream propertiesFile = Files.newInputStream(Paths.get(DATABASE_PROPERTIES))) {
            databaseProperties.load(propertiesFile);
        } catch (IOException e) {
            LOG.fatal("Failed to load database properties.", e);
            throw new ExceptionInInitializerError("Failed to load database properties.");
        }

        DATABASE_URL = databaseProperties.getProperty("url");
        DATABASE_USERNAME = databaseProperties.getProperty("username");
        DATABASE_PASSWORD = databaseProperties.getProperty("password");

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            LOG.fatal("Unable to register driver for database connection.", e);
            throw new ExceptionInInitializerError("Unable to register driver for database connection.");
        }
    }

    private ConnectionFactory() {}

    static Connection getConnection() throws PoolException {
        try {
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            throw new PoolException("Failed to get connection from database", e);
        }
    }
}
