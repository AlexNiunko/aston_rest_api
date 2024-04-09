package com.aston_rest_api.service;

import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(User user) throws ServiceException;
    boolean addNewUser(User user) throws ServiceException;
    HashMap<Long, Product> selectUserPurchases(User user) throws ServiceException;
    boolean deleteUser(User user) throws ServiceException;
    boolean updateUser(User user) throws ServiceException;
    List<User>selectAllUsers() throws ServiceException;
}
