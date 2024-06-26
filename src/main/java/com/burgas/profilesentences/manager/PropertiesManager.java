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
    public static final String FILES_PROPERTIES = "files.properties";
    public static final String DB_PROPERTIES = "db.properties";

    private PropertiesManager() {
    }

    public static Properties fileProperties() {

        try (InputStream inputStream = PropertiesManager.class
                .getClassLoader().getResourceAsStream(FILES_PROPERTIES)){

            PROPERTIES.load(inputStream);

        } catch (IOException e) {
            throw new PropertiesManagerException(e);
        }

        return PROPERTIES;
    }

    public static Connection createConnection() {

        try (InputStream resource = PropertiesManager.class
                .getClassLoader().getResourceAsStream(DB_PROPERTIES)){

            PROPERTIES.load(resource);
            Class.forName(PROPERTIES.getProperty("driver"));

            return DriverManager.getConnection(PROPERTIES.getProperty("url"), PROPERTIES);

        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
