package com.astonrest.service;

import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(User user) throws ServiceException;
    boolean addNewUser(User user) throws ServiceException;
    List<Product> selectUserPurchases(User user) throws ServiceException;
    boolean deleteUser(User user) throws ServiceException;
    boolean updateUser(User user) throws ServiceException;
    List<User>selectAllUsers() throws ServiceException;
}
