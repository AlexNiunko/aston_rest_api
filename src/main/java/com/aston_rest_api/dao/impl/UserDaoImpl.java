package com.aston_rest_api.dao.impl;

import com.aston_rest_api.dao.BaseDao;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.dao.mapper.impl.UserResultSetMapperImpl;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDaoImpl extends BaseDao<User> implements com.aston_rest_api.dao.UserDao {
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD =
            "SELECT * FROM users WHERE u.password=? and u.login=? ";
    private static final String FIND_ALL_USER_PURCHASES =
            """
                    SELECT * FROM users u JOIN sales s 
                    ON u.user_id=s.buyer_id JOIN products p 
                    ON s.product_id=p.id_product JOIN product_descriptions pd
                    ON pd.product_id=p.id_product
                    WHERE u.user_id=?
                    """;
    private static final String FIND_ALL_USERS =
            "SELECT * FROM users";
    private static final String INSERT_USER =
            """
                    INSERT INTO users (user_id,login,password,name,surname,users_role) 
                    VALUES(?,?,?,?,?,?)
                    """;
    private static final String DELETE_USER_BY_ID =
            "DELETE FROM users WHERE user_id=?";
    private static final String UPDATE_USER =
            "UPDATE users SET login=?,password=?,name=?,surname=? WHERE user_id=?";

    private ConnectionManagerImpl connectionManager;
    private ResultSetMapper<User,Product> resultSetMapper;

    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
        this.connectionManager = ConnectionManagerImpl.getInstance();
        this.resultSetMapper = UserResultSetMapperImpl.getInstance();
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(User user) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSurname());
            statement.setInt(6, user.getUsersRole());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {

        }
        return result;
    }

    @Override
    public boolean delete(User user) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statement.setLong(1, user.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {

        }
        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            ResultSet resultSet = statement.getResultSet();
            userList = resultSetMapper.mapListItems(resultSet);
        } catch (SQLException e) {

        }
        return userList;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {

        }
        return result;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(User user) {
        Optional<User> optionalUser = Optional.empty();
        String login = user.getLogin();
        String password = user.getPassword();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD);
        ) {
            statement.setString(1, password);
            statement.setString(2, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                optionalUser = resultSetMapper.mapItem(resultSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserPurchases(User user) {
        Optional<User> optionalUser = Optional.empty();
        Map<Long, Product> purchases;
        long userId = user.getId();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USER_PURCHASES);
        ) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                purchases = resultSetMapper.mapItemEntities(resultSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        user.setPurchases(purchases);
        optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }
}
