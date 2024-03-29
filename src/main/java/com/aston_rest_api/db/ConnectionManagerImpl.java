package com.aston_rest_api.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionManagerImpl implements ConnectionManager {
    private static HikariDataSource dataSource;
    private static Lock locker = new ReentrantLock();
    private static ConnectionManagerImpl connectionInstance;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static final Properties PROPERTIES=new Properties();
    private static final String PROPERTIES_PATH="datasource.properties";


    private ConnectionManagerImpl() {
        try(InputStream inputStream=getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH)){
            PROPERTIES.load(inputStream);
            dataSource=new HikariDataSource();
            dataSource.setDriverClassName(PROPERTIES.getProperty("collectionManagerImpl.db.driver"));
            dataSource.setJdbcUrl(PROPERTIES.getProperty("collectionManagerImpl.db.url"));
            dataSource.setUsername(PROPERTIES.getProperty("collectionManagerImpl.user"));
            dataSource.setPassword(PROPERTIES.getProperty("collectionManagerImpl.password"));
            dataSource.setMinimumIdle(5);
            dataSource.setMaximumPoolSize(10);
            dataSource.setAutoCommit(false);
            dataSource.setLoginTimeout(3);
        }catch (IOException |SQLException e){
            throw new ExceptionInInitializerError("Failed to read configuration fail"+e);
        }
    }

    public static ConnectionManagerImpl getInstance() {
        if (!isInitialized.get()) {
            try {
                locker.lock();
                if (connectionInstance == null) {
                    connectionInstance = new ConnectionManagerImpl();
                    isInitialized.set(true);
                }
            } finally {
                locker.unlock();
            }
        }
        return connectionInstance;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
