package com.aston_rest_api.dao;

import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findUserByLoginAndPassword(User user) throws DaoException;
    Optional<User>findUserPurchases(User user) throws DaoException;
}
