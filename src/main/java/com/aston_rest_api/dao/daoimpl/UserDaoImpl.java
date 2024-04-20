package com.aston_rest_api.dao.daoimpl;

import com.aston_rest_api.dao.BaseDao;
import com.aston_rest_api.dao.mapper.ListResultSetMapper;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.dao.mapper.impl.UserResultSetMapperImpl;
import com.aston_rest_api.db.Configuration;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.DaoException;
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
    public static final String FIND_USER_BY_LOGIN_AND_PASSWORD =
            "SELECT * FROM tool_box.users u WHERE u.password=? and u.login=? ";
    public static final String FIND_ALL_USER_PURCHASES =
            """
                    SELECT * FROM tool_box.users u 
                    JOIN tool_box.sales s 
                    ON u.user_id=s.buyer_id 
                    JOIN tool_box.products p 
                    ON s.product_id=p.id_product 
                    JOIN tool_box.product_descriptions pd
                    ON pd.product_id=p.id_product
                    WHERE u.user_id=?
                    """;
    public static final String FIND_ALL_USERS =
            "SELECT * FROM tool_box.users";
    public static final String INSERT_USER = "INSERT INTO tool_box.users(user_id,login,password,name,surname,users_role) VALUES (?,?,?,?,?,?) ";

    public static final String DELETE_USER_BY_ID =
            "DELETE FROM tool_box.users WHERE user_id=?";
    public static final String UPDATE_USER =
            "UPDATE tool_box.users SET login=?,password=?,name=?,surname=? WHERE user_id=?";


    private ResultSetMapper<User> resultSetMapper = UserResultSetMapperImpl.getInstance();

    private ConnectionManagerImpl connectionManager;

    public UserDaoImpl(ConnectionManagerImpl connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        boolean result = false;
        if (user == null) {
            return result;
        }
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
            throw new DaoException("Failed to insert user " + e);
        }
        return result;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        boolean result = false;
        if (user == null) {
            return result;
        }
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statement.setLong(1, user.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException | RuntimeException e) {
            throw new DaoException("Failed to delete user " + e);
        }
        return result;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            userList = resultSetMapper.mapListItems(resultSet);
        } catch (SQLException | RuntimeException e) {
            throw new DaoException("Failed to select list of uses " + e);
        }
        return userList;
    }

    @Override
    public boolean update(User user) throws DaoException {
        boolean result = false;
        if (user == null) {
            return result;
        }
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setLong(5, user.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException | RuntimeException e) {
            throw new DaoException("Failed update user " + e);
        }
        return result;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(User user) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        if (user == null) {
            return optionalUser;
        }
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
        } catch (SQLException e) {
            throw new DaoException("Failed to find user  login and password " + e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserPurchases(User user) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        if (user == null) {
            return optionalUser;
        }
        List<Product> purchases;
        long userId = user.getId();
        ListResultSetMapper<Product> listResultSetMapper = (ListResultSetMapper) resultSetMapper;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USER_PURCHASES);
        ) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {

                purchases = listResultSetMapper.mapItemEntities(resultSet);
            }
        } catch (SQLException | RuntimeException e) {
            throw new DaoException("Failed to find user purchases " + e);
        }
        user.setPurchases(purchases);
        optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }
}
