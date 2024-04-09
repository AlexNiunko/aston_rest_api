package com.aston_rest_api.dao.daoimpl;

import com.aston_rest_api.dao.UserDao;
import com.aston_rest_api.db.ConnectionManager;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class UserDaoImplTest {
    private static HikariDataSource dataSource = new HikariDataSource();
    private static ConnectionManagerImpl connectionManager;
    private static UserDaoImpl userDao;
    private static User user;
    private static List<User> userList = new ArrayList<>();

    static {
        User user1 = new User.UserBuilder(1).setLogin("michai@gmail.com").setPassword("123").setName("Michail").setSurname("Radzivil").setUsersRole(1).build();
        User user2 = new User.UserBuilder(22).setLogin("stefan@tut.by").setPassword("12345").setName("Stefan").setSurname("Batoyi").setUsersRole(1).build();
        User user3 = new User.UserBuilder(33).setLogin("stanslau@mail.ru").setPassword("12459").setName("Stanislau").setSurname("Poniatovskiy").setUsersRole(1).build();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
    }

    private static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:10-alpine")
            .withUsername("postgres")
            .withPassword("pass")
            .withInitScript("DB-migration.sql")
            .withDatabaseName("tool_box_test");

    @BeforeAll
    static void init() throws SQLException {
        container.start();
        dataSource.setDriverClassName(container.getDriverClassName());
        dataSource.setJdbcUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(1000);
        dataSource.setAutoCommit(true);
        dataSource.setLoginTimeout(10);
    }


    @AfterAll
    static void clean() {
        connectionManager = null;
        container.stop();
    }

    @BeforeEach
    void setUp() {
        connectionManager = ConnectionManagerImpl.getInstance(dataSource);
        userDao = new UserDaoImpl(connectionManager);
    }

    @AfterEach
    void setDown() {
        userDao = null;
    }

    @Test
    @Order(1)
    void successfulFindUserPurchases() throws DaoException {
        HashMap<Long, Product> purchases = new HashMap<>();
        Product item = new Product.ProductBuilder(1).setProductName("hammer").setProductPrice(10.25).setAmount(1).build();
        ProductDescription itemDescription = new ProductDescription.ProductDescriptionBuilder(1)
                .setProductId(1)
                .setType("hand tool").setBrand("Expert").setCountryOfOrigin("China")
                .setIssueDate(LocalDate.of(2024, 2, 9))
                .build();
        item.setDescription(itemDescription);
        purchases.put(1L, item);
        purchases.put(2L, item);
        user=new User.UserBuilder(1)
                .setLogin("michai@gmail.com")
                .setPassword("123")
                .setName("Michail")
                .setSurname("Radzivil")
                .build();
        System.out.println(purchases);
        Assertions.assertAll(
                () -> assertTrue(userDao.findUserPurchases(user).isPresent()),
                () -> assertEquals(user.getPurchases(),purchases)
        );
    }
    @Test
    @Order(2)
    void successfulFindAllUsers() throws DaoException {
        List<User> actual = userDao.findAll();
        Assertions.assertEquals(actual, userList);
    }
    @Test
    @Order(3)
    void successfulFindUserByLoginAndPassword() throws DaoException {
        user = new User.UserBuilder()
                .setLogin("michai@gmail.com")
                .setPassword("123")
                .build();
        Assertions.assertTrue(userDao.findUserByLoginAndPassword(user).isPresent());
    }
    @Test
    @Order(4)
    void FindUserByLoginAndPasswordIfUserNull() throws DaoException {
        user = null;
        Assertions.assertFalse(userDao.findUserByLoginAndPassword(user).isPresent());
    }
    @Test
    @Order(5)
    void unsuccessfulFindUserPurchases() throws DaoException {
        user = new User.UserBuilder(10)
                .setLogin("michai@gmail.com")
                .setPassword("123")
                .setName("Michail")
                .setSurname("Radzivil")
                .build();
        Assertions.assertNull(user.getPurchases());
    }
    @Test
    @Order(6)
    void unsuccessfulFindAllUsers() throws DaoException {
        User person = new User.UserBuilder(3).build();
        userList.add(person);
        List<User> actual = userDao.findAll();
        Assertions.assertNotEquals(actual, userList);
        userList.remove(2);
    }
    @Test
    @Order(7)
    void unsuccessfulFindUserByLoginAndPassword() throws DaoException {
        user = new User.UserBuilder()
                .setLogin("mmmichai@gmail.com")
                .setPassword("123")
                .build();
        Assertions.assertTrue(userDao.findUserByLoginAndPassword(user).isEmpty());
    }
    @Test
    @Order(8)
    void successfulUpdate() throws DaoException {
        user = new User.UserBuilder(1)
                .setLogin("otherLogin")
                .setPassword("otherPassword")
                .setName("otherName")
                .setSurname("otherSurname")
                .build();
        Assertions.assertTrue(userDao.update(user));
    }

    @Test
    @Order(9)
    void unsuccessfulUpdate() throws DaoException {
        user = new User.UserBuilder(0)
                .setLogin("otherLogin")
                .setPassword("otherPassword")
                .setName("otherName")
                .setSurname("otherSurname")
                .build();
        Assertions.assertFalse(userDao.update(user));
    }
    @Test
    @Order(10)
    void successfulInsert() throws DaoException {
        user = new User.UserBuilder(10).setName("Vasil").setSurname("Bykau").setPassword("23112")
                .setLogin("Writer").setUsersRole(1).build();
        Assertions.assertTrue(userDao.insert(user));
    }

    @Test
    @Order(11)
    void insertIfUserNull() throws DaoException {
        user = null;
        Assertions.assertFalse(userDao.insert(user));
    }

    @Test
    @Order(12)
    void throwExceptionInsert() throws DaoException {
        user = new User.UserBuilder(123)
                .setLogin("michail@tut.by")
                .setPassword("999")
                .setName("Michail")
                .setSurname("Pupkin")
                .setUsersRole(1)
                .build();
        userDao.insert(user);
        Assertions.assertThrows(DaoException.class, () -> userDao.insert(user));
    }

    @Test
    @Order(13)
    void successfulDelete() throws DaoException {
        user = new User.UserBuilder(1).build();
        Assertions.assertTrue(userDao.delete(user));
    }

    @Test
    @Order(14)
    void unsuccessfulDelete() throws DaoException {
        user = new User.UserBuilder(1000).build();
        Assertions.assertFalse(userDao.delete(user));
    }
    @Test
    @Order(15)
    void deleteIfUserNull() throws DaoException {
        user = null;
        Assertions.assertFalse(userDao.delete(user));
    }

    @Test
    @Order(16)
    void updateIfUserNull() throws DaoException {
        user = null;
        Assertions.assertFalse(userDao.update(user));
    }

    //    @Test
//    void throwExceptionFindAllUsers() throws DaoException {
//        Assertions.assertThrows(DaoException.class, () -> userDao.findAll());
//    }

//        @Test
//        void throwExceptionDelete() {
//            user = new User.UserBuilder(1).build();
//            dataSource.setJdbcUrl("aerg");
//            Assertions.assertThrows(DaoException.class, () -> userDao.delete(user));
//        }
    //    @Test
//    void throwExceptionFindUserPurchases() throws DaoException {
//        user=new User.UserBuilder(1)
//                .setLogin("michai@gmail.com")
//                .setPassword("123")
//                .setName("Michail")
//                .setSurname("Radzivil")
//                .build();
//        dataSource.setJdbcUrl("wrong Url");
//        Assertions.assertThrows(DaoException.class,()->userDao.findUserPurchases(user));
//        dataSource.setJdbcUrl(container.getJdbcUrl());
//    }

    //    @Test
//    void throwExceptionFindUserByLoginAndPassword() throws DaoException {
//        user=new User.UserBuilder()
//                .build();
//        dataSource.setJdbcUrl("Wrong url");
//        Assertions.assertThrows(DaoException.class,()->userDao.findUserByLoginAndPassword(user));
//        dataSource.setJdbcUrl(container.getJdbcUrl());
//    }
//    @Test
//    void throwExceptionUpdate() throws DaoException {
//        user = new User.UserBuilder(1)
//                .setLogin("otherLogin")
//                .setPassword("otherPassword")
//                .setName("otherName")
//                .setSurname("otherSurname")
//                .build();
//        dataSource.setJdbcUrl("wrong url");
//        Assertions.assertThrows(DaoException.class, () -> userDao.delete(user));
//    }

}