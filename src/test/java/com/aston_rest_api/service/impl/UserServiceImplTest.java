package com.aston_rest_api.service.impl;

import com.aston_rest_api.dao.daoimpl.UserDaoImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private User user = new User
            .UserBuilder(1)
            .setLogin("michai@gmail.com")
            .setPassword("123")
            .setName("Michail")
            .setSurname("Radzivil")
            .setUsersRole(1)
            .build();
    private static UserDaoImpl userDao;
    private static UserServiceImpl userService;


    @BeforeAll
    static void init() {
        userDao = Mockito.mock(UserDaoImpl.class);
        userService = new UserServiceImpl(userDao);
    }

    @AfterAll
    static void finish() {
        userDao = null;
        userService = null;
    }

    @Test
    void shouldAuthenticateUser() throws ServiceException, DaoException {
        Mockito.doReturn(Optional.of(user)).when(userDao).findUserByLoginAndPassword(user);
        Optional<User> optionalUserReturn = userService.authenticate(user);
        Assertions.assertTrue(optionalUserReturn.isPresent());
    }
    @Test
    void shouldNotAuthenticateUser() throws ServiceException, DaoException {
        Optional<User> optionalUser = Optional.empty();
        Mockito.doReturn(optionalUser).when(userDao).findUserByLoginAndPassword(user);
        Optional<User> optionalUserReturn = userService.authenticate(null);
        Assertions.assertTrue(optionalUserReturn.isEmpty());
    }
    @Test
    void throwAuthenticateUser() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(userDao).findUserByLoginAndPassword(user);
        Assertions.assertThrows(ServiceException.class,()->userService.authenticate(user));
    }


    @Test
    void shouldAddNewUser() throws DaoException, ServiceException {
        Mockito.doReturn(true).when(userDao).insert(user);
        Assertions.assertTrue(userService.addNewUser(user));
    }

    @Test
    void shouldNotAddNewUser() throws DaoException, ServiceException {
        Mockito.doReturn(false).when(userDao).insert(null);
        Assertions.assertFalse(userService.addNewUser(null));
    }
    @Test
    void throwAddNewUser() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(userDao).insert(user);
        Assertions.assertThrows(ServiceException.class,()->userService.addNewUser(user));
    }

    @Test
    void shouldSelectAllUsers() throws DaoException, ServiceException {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        Mockito.doReturn(userList).when(userDao).findAll();
        Assertions.assertEquals(userService.selectAllUsers(), userList);
    }

    @Test
    void shouldNotSelectAllUsers() throws DaoException, ServiceException {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        List<User> empty = new ArrayList<>();
        Mockito.doReturn(empty).when(userDao).findAll();
        Assertions.assertNotEquals(userService.selectAllUsers(), userList);
    }
    @Test
    void throwSelectAllUsers() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(userDao).findAll();
        Assertions.assertThrows(ServiceException.class,()->userService.selectAllUsers());
    }

    @Test
    void shouldDeleteUser() throws ServiceException, DaoException {
        Mockito.doReturn(true).when(userDao).delete(user);
        Assertions.assertTrue(userService.deleteUser(user));
    }
    @Test
    void shouldNotDeleteUser() throws ServiceException, DaoException {
        Mockito.doReturn(false).when(userDao).delete(null);
        Assertions.assertFalse(userService.deleteUser(null));
    }
    @Test
    void throwDeleteUser() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(userDao).delete(user);
        Assertions.assertThrows(ServiceException.class,()->userService.deleteUser(user));
    }

    @Test
    void shouldUpdateUser() throws ServiceException, DaoException {
        Mockito.doReturn(true).when(userDao).update(user);
        Assertions.assertTrue(userService.updateUser(user));
    }
    @Test
    void shouldNotUpdateUser() throws ServiceException, DaoException {
        Mockito.doReturn(false).when(userDao).update(null);
        Assertions.assertFalse(userService.updateUser(null));
    }
    @Test
    void throwUpdateUser() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(userDao).update(user);
        Assertions.assertThrows(ServiceException.class,()->userService.updateUser(user));
    }

    @Test
    void shouldSelectUserPurchases() throws DaoException, ServiceException {
        HashMap<Long, Product> map=new HashMap<>();
        Product product=new Product.ProductBuilder(1).build();
        map.put(1L,product);
        user.setPurchases(map);
        Optional<User>optionalUser=Optional.of(user);
        Mockito.doReturn(optionalUser).when(userDao).findUserPurchases(user);
        Assertions.assertEquals(map,userService.selectUserPurchases(user));
    }
    @Test
    void shouldNotSelectUserPurchases() throws DaoException, ServiceException {
        HashMap<Long, Product> map=new HashMap<>();
        Product product=new Product.ProductBuilder(1).build();
        map.put(1L,product);
        user.setPurchases(map);
        Optional<User>optionalUser=Optional.empty();
        Mockito.doReturn(optionalUser).when(userDao).findUserPurchases(null);
        Assertions.assertNotEquals(map,userService.selectUserPurchases(null));
    }
    @Test
    void throwSelectUserPurchases() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(userDao).findUserPurchases(user);
        Assertions.assertThrows(ServiceException.class,()->userService.selectUserPurchases(user));
    }

}