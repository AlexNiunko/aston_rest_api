package com.astonrest.db;

import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class Configuration {
    private static final Properties PROPERTIES = new Properties();
    public static final HikariDataSource dataSource = new HikariDataSource();
    private static final String PROPERTIES_PATH = "datasource.properties";

    private Configuration() {
    }

    static {
        try (InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            PROPERTIES.load(inputStream);
            dataSource.setDriverClassName(PROPERTIES.getProperty("collectionManagerImpl.db.driver"));
            dataSource.setJdbcUrl(PROPERTIES.getProperty("collectionManagerImpl.db.url"));
            dataSource.setUsername(PROPERTIES.getProperty("collectionManagerImpl.user"));
            dataSource.setPassword(PROPERTIES.getProperty("collectionManagerImpl.password"));
            dataSource.setMinimumIdle(5);
            dataSource.setMaximumPoolSize(1000);
            dataSource.setAutoCommit(true);
            dataSource.setLoginTimeout(10);
        } catch (IOException | SQLException e) {
            throw new ExceptionInInitializerError("Failed to read configuration fail" + e);
        }
    }


}
