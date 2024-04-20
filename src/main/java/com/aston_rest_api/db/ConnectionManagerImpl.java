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
    private HikariDataSource dataSource;
    private static Lock locker = new ReentrantLock();
    private static ConnectionManagerImpl connectionInstance;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);

    private ConnectionManagerImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ConnectionManagerImpl getInstance(HikariDataSource dataSource) {
        if (!isInitialized.get()) {
            try {
                locker.lock();
                if (connectionInstance == null) {
                    connectionInstance = new ConnectionManagerImpl(dataSource);
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
