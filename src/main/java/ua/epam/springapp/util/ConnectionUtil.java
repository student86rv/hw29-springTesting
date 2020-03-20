package ua.epam.springapp.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionUtil {

    private static Logger LOGGER = Logger.getLogger(ConnectionUtil.class.getName());
    private static BasicDataSource DATASOURCE = new BasicDataSource();
    private static Properties properties;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Database driver registration failed");
        }        properties = readProperties();
        DATASOURCE.setUrl(properties.getProperty("db.url"));
        DATASOURCE.setUsername(properties.getProperty("db.username"));
        DATASOURCE.setPassword(properties.getProperty("db.password"));
        DATASOURCE.setMinIdle(5);
        DATASOURCE.setMaxIdle(10);
        DATASOURCE.setMaxOpenPreparedStatements(100);
    }

    private static Properties readProperties() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = null;
        try (InputStream input = classLoader.getResourceAsStream("local.properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Database config reading failed");
        }
        return properties;
    }

    public static Connection getConnection() throws SQLException {
        return DATASOURCE.getConnection();
    }
}
