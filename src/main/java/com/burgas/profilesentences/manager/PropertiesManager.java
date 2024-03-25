package com.burgas.profilesentences.manager;

import com.burgas.profilesentences.exception.PropertiesManagerException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PropertiesManager {

    private static final Properties PROPERTIES = new Properties();

    private PropertiesManager() {
    }

    public static Properties fileProperties() {

        try (InputStream inputStream = PropertiesManager.class
                .getClassLoader().getResourceAsStream("files.properties")){

            PROPERTIES.load(inputStream);

        } catch (IOException e) {
            throw new PropertiesManagerException(e);
        }

        return PROPERTIES;
    }

    public static Connection createConnection() {

        try (InputStream resource = PropertiesManager.class
                .getClassLoader().getResourceAsStream("db.properties")){

            PROPERTIES.load(resource);
            Class.forName(PROPERTIES.getProperty("driver"));

            return DriverManager.getConnection(PROPERTIES.getProperty("url"), PROPERTIES);

        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
