package com.aston_rest_api.dao.mapper.impl;

import com.aston_rest_api.dao.daoimpl.UserDaoImpl;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.Sale;
import com.aston_rest_api.model.User;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserResultSetMapperImplTest {
    private static User user=new User.UserBuilder(1)
            .setLogin("michai@gmail.com")
            .setPassword("123")
            .setName("Michail")
            .setSurname("Radzivil")
            .setUsersRole(1)
            .build();
    private static HikariDataSource dataSource = new HikariDataSource();
    private static ConnectionManagerImpl connectionManager;

    private static ResultSetMapper mapper = UserResultSetMapperImpl.getInstance();
    private static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:10-alpine")
            .withUsername("postgres")
            .withPassword("pass")
            .withInitScript("DB-migration.sql")
            .withDatabaseName("tool_box_test");

    @BeforeAll
    static void init() throws SQLException {
        container.start();
        dataSource.setDriverClassName(container.getDriverClassName());
        dataSource.setJdbcUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(1000);
        dataSource.setAutoCommit(true);
        dataSource.setLoginTimeout(10);
    }

    @AfterAll
    static void clean() {
        connectionManager = null;
        container.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        connectionManager = ConnectionManagerImpl.getInstance(dataSource);
    }

    @AfterEach
    void setDown() {
        connectionManager = null;
    }

    @Test
    void mapItem() throws DaoException {
        Optional<User>optionalUser;
        try(Connection connection= connectionManager.getConnection();
            PreparedStatement statement=connection.prepareStatement(UserDaoImpl.FIND_USER_BY_LOGIN_AND_PASSWORD)){
            statement.setString(1, user.getPassword());
            statement.setString(2,user.getLogin());
            try(ResultSet resultSet=statement.executeQuery()){
                optionalUser=mapper.mapItem(resultSet);
            }
        }catch (SQLException e){
            throw new DaoException("Failed test map item "+e);
        }
        Assertions.assertAll(
                () -> assertTrue(optionalUser.isPresent()),
                ()-> assertEquals(user,optionalUser.get())
        );
    }
    @Test
    void mapListItems() throws DaoException {
     List<User> userList;
     try(Connection connection= connectionManager.getConnection();
         PreparedStatement statement=connection.prepareStatement(UserDaoImpl.FIND_ALL_USERS)
     ){
         try(ResultSet resultSet=statement.executeQuery()){
             userList=mapper.mapListItems(resultSet);
         }
     }catch (SQLException e){
         throw new DaoException("Failed to test map list item "+e);
     }
     Assertions.assertEquals(3,userList.size());

    }

    @Test
    void mapItemEntities() throws DaoException {
        Map<Long,Product>purchases;
        try(Connection connection= connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(UserDaoImpl.FIND_ALL_USER_PURCHASES)){
            statement.setLong(1,user.getId());
            try(ResultSet resultSet=statement.executeQuery()){
                purchases=mapper.mapItemEntities(resultSet);
            }
        }catch (SQLException e){
            throw new DaoException("Failed to test map entitties "+e);
        }
        Assertions.assertEquals(2,purchases.size());

    }




}