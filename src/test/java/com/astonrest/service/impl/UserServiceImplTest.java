package com.astonrest.service.impl;

import com.astonrest.dao.BaseDao;
import com.astonrest.dao.UserDao;
import com.astonrest.dao.impl.UserDaoImpl;
import com.astonrest.exception.DaoException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.User;
import com.astonrest.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class UserServiceImplTest {
    private User user = new User
            .UserBuilder(1)
            .setLogin("michai@gmail.com")
            .setPassword("123")
            .setName("Michail")
            .setSurname("Radzivil")
            .setUsersRole(1)
            .build();
    private static UserDao userDao;
    private static BaseDao userBaseDao;
    private static UserService userService;


    @BeforeAll
    static void init() {
        userDao = Mockito.mock(UserDaoImpl.class);
        userBaseDao=(BaseDao)userDao;
        userService = UserServiceImpl.getUserService(userDao);
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
        Mockito.doReturn(true).when(userBaseDao).insert(user);
        Assertions.assertTrue(userService.addNewUser(user));
    }

    @Test
    void shouldNotAddNewUser() throws DaoException, ServiceException {
        Mockito.doReturn(false).when(userBaseDao).insert(null);
        Assertions.assertFalse(userService.addNewUser(null));
    }
    @Test
    void throwAddNewUser() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(userBaseDao).insert(user);
        Assertions.assertThrows(ServiceException.class,()->userService.addNewUser(user));
    }

    @Test
    void shouldSelectAllUsers() throws DaoException, ServiceException {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        Mockito.doReturn(userList).when(userBaseDao).findAll();
        Assertions.assertEquals(userService.selectAllUsers(), userList);
    }

    @Test
    void shouldNotSelectAllUsers() throws DaoException, ServiceException {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        List<User> empty = new ArrayList<>();
        Mockito.doReturn(empty).when(userBaseDao).findAll();
        Assertions.assertNotEquals(userService.selectAllUsers(), userList);
    }
    @Test
    void throwSelectAllUsers() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(userBaseDao).findAll();
        Assertions.assertThrows(ServiceException.class,()->userService.selectAllUsers());
    }

    @Test
    void shouldDeleteUser() throws ServiceException, DaoException {
        Mockito.doReturn(true).when(userBaseDao).delete(user);
        Assertions.assertTrue(userService.deleteUser(user));
    }
    @Test
    void shouldNotDeleteUser() throws ServiceException, DaoException {
        Mockito.doReturn(false).when(userBaseDao).delete(null);
        Assertions.assertFalse(userService.deleteUser(null));
    }
    @Test
    void throwDeleteUser() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(userBaseDao).delete(user);
        Assertions.assertThrows(ServiceException.class,()->userService.deleteUser(user));
    }

    @Test
    void shouldUpdateUser() throws ServiceException, DaoException {
        Mockito.doReturn(true).when(userBaseDao).update(user);
        Assertions.assertTrue(userService.updateUser(user));
    }
    @Test
    void shouldNotUpdateUser() throws ServiceException, DaoException {
        Mockito.doReturn(false).when(userBaseDao).update(null);
        Assertions.assertFalse(userService.updateUser(null));
    }
    @Test
    void throwUpdateUser() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(userBaseDao).update(user);
        Assertions.assertThrows(ServiceException.class,()->userService.updateUser(user));
    }

    @Test
    void shouldSelectUserPurchases() throws DaoException, ServiceException {
        List<Product>purchases=new ArrayList<>();
        Product product=new Product.ProductBuilder(1).build();
        purchases.add(product);
        user.setPurchases(purchases);
        Optional<User>optionalUser=Optional.of(user);
        Mockito.doReturn(optionalUser).when(userDao).findUserPurchases(user);
        Assertions.assertEquals(purchases,userService.selectUserPurchases(user));
    }
    @Test
    void shouldNotSelectUserPurchases() throws DaoException, ServiceException {
        List<Product> list=new ArrayList<>();
        Product product=new Product.ProductBuilder(1).build();
        list.add(product);
        user.setPurchases(list);
        Optional<User>optionalUser=Optional.empty();
        Mockito.doReturn(optionalUser).when(userDao).findUserPurchases(null);
        Assertions.assertNotEquals(list,userService.selectUserPurchases(null));
    }
    @Test
    void throwSelectUserPurchases() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(userDao).findUserPurchases(user);
        Assertions.assertThrows(ServiceException.class,()->userService.selectUserPurchases(user));
    }

}