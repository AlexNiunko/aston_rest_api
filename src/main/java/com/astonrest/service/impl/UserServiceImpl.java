package com.astonrest.service.impl;

import com.astonrest.dao.BaseDao;
import com.astonrest.dao.UserDao;
import com.astonrest.exception.DaoException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.User;
import com.astonrest.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private BaseDao<User> userBaseDao;
    private UserDao userDao;
    private static UserService userService;
    public static UserService getUserService(UserDao userDao){
        if (userService==null){
            userService=new UserServiceImpl(userDao);
        }
        return userService;
    }

    private UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
        this.userBaseDao=(BaseDao) this.userDao;
    }

    @Override
    public Optional<User> authenticate(User user) throws ServiceException {
        Optional<User>optionalUser=Optional.empty();
        if (user==null){
            return optionalUser;
        }
        try {
            optionalUser=userDao.findUserByLoginAndPassword(user);
        } catch (DaoException e) {
            throw new ServiceException("Failed to authenticate user "+e);
        }
        return optionalUser;
    }

    @Override
    public boolean addNewUser(User user) throws ServiceException {
        boolean result=false;
        if (user==null){
            return result;
        }
        try {
            result= userBaseDao.insert(user);
        } catch (DaoException e) {
            throw new ServiceException("Failed to add new user"+e);
        }
        return result;
    }

    @Override
    public List<Product> selectUserPurchases(User user) throws ServiceException {
        List<Product>list=new ArrayList<>();
        if (user==null){
            return list;
        }
        try {
            Optional<User>optionalUser=userDao.findUserPurchases(user);
            if (optionalUser.isPresent()){
                list=  optionalUser.get().getPurchases();
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to find user purchases "+e);
        }
        return list;
    }

    @Override
    public boolean deleteUser(User user) throws ServiceException {
        boolean result=false;
        if (user==null){
            return false;
        }
        try {
            result= userBaseDao.delete(user);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete user "+e);
        }
        return result;
    }

    @Override
    public boolean updateUser(User user) throws ServiceException {
        boolean result=false;
        if (user==null){
            return false;
        }
        try {
            result= userBaseDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update user "+e);
        }
        return result;
    }

    @Override
    public List<User> selectAllUsers() throws ServiceException {
        List<User>userList=new ArrayList<>();
        try {
            userList= userBaseDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to select all users "+e);
        }
        return userList;
    }
}
