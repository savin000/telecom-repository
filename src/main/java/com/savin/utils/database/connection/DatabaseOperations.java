package com.savin.utils.database.connection;

import com.savin.repository.core.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseOperations {
    private static final Logger LOGGER = LogManager.getLogger();

    private String driver;

    private String url;

    private String user;

    private String password;

    public DatabaseOperations() {
        loadProperties();
    }

    public void save(Repository repository) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class was not found", e);
        }

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

        } catch (SQLException e) {
            LOGGER.error("Database access error or the url is null", e);
        }
    }

    private void loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = DatabaseOperations.class.getResourceAsStream("/resources/connection.properties")) {
            properties.load(inputStream);

            driver = properties.getProperty(DatabaseOperationsConstants.DRIVER_PROPERTY_NAME);
            url = properties.getProperty(DatabaseOperationsConstants.URL_PROPERTY_NAME);
            user = properties.getProperty(DatabaseOperationsConstants.USER_PROPERTY_NAME);
            password = properties.getProperty(DatabaseOperationsConstants.PASSWORD_PROPERTY_NAME);
        } catch (IOException e) {
            LOGGER.error("Something went wrong while getting properties", e);
        }
    }
}
