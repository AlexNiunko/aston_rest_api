package com.astonrest.dao;

import com.astonrest.exception.DaoException;
import com.astonrest.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findUserByLoginAndPassword(User user) throws DaoException;
    Optional<User>findUserPurchases(User user) throws DaoException;
}
