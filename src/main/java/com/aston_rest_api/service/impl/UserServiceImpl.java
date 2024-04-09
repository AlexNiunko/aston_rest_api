package com.aston_rest_api.service.impl;

import com.aston_rest_api.dao.daoimpl.UserDaoImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;
import com.aston_rest_api.service.UserService;
import com.aston_rest_api.validator.ParameterValidator;

import javax.sql.rowset.serial.SerialException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao;

    public UserServiceImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
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
            result=userDao.insert(user);
        } catch (DaoException e) {
            throw new ServiceException("Failed to add new user"+e);
        }
        return result;
    }

    @Override
    public HashMap<Long, Product> selectUserPurchases(User user) throws ServiceException {
        HashMap<Long,Product>map=new HashMap<>();
        if (user==null){
            return map;
        }
        try {
            Optional<User>optionalUser=userDao.findUserPurchases(user);
            if (optionalUser.isPresent()){
                map= (HashMap<Long, Product>) optionalUser.get().getPurchases();
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to find user purchases "+e);
        }
        return map;
    }

    @Override
    public boolean deleteUser(User user) throws ServiceException {
        boolean result=false;
        if (user==null){
            return false;
        }
        try {
            result=userDao.delete(user);
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
            result=userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update user "+e);
        }
        return result;
    }

    @Override
    public List<User> selectAllUsers() throws ServiceException {
        List<User>userList=new ArrayList<>();
        try {
            userList=userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to select all users "+e);
        }
        return userList;
    }
}
