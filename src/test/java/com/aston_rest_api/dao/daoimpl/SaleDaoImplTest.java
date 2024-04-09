package com.aston_rest_api.dao.daoimpl;

import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.Sale;
import com.aston_rest_api.model.User;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SaleDaoImplTest {

    private static HikariDataSource dataSource = new HikariDataSource();
    private static ConnectionManagerImpl connectionManager;
    private static SaleDaoImpl saleDao;
    private static List<Sale> saleList = new ArrayList<>();

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
        saleDao = new SaleDaoImpl(connectionManager);
    }

    @AfterEach
    void setDown() {
        saleDao = null;
    }

    @Test
    @Order(1)
    void findAll() throws DaoException {
        Sale sale1=new Sale.SaleBuilder(1)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,4,8))
                .setAmountSale(1)
                .build();
        Sale sale2=new Sale.SaleBuilder(2)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,3,25))
                .setAmountSale(1)
                .build();
        Sale sale3=new Sale.SaleBuilder(3)
                .setBuyerId(22)
                .setProductId(2)
                .setDateOfSale(LocalDate.of(2024,1,25))
                .setAmountSale(2)
                .build();
        saleList.add(sale1);
        saleList.add(sale2);
        saleList.add(sale3);
        System.out.println(saleDao.findAll());
        Assertions.assertTrue(saleDao.findAll().equals(saleList));
    }

    @Test
    @Order(2)
    void findSalesByDateExist() throws DaoException {
        LocalDate date=LocalDate.of(2024,4,8);
        Sale sale1=new Sale.SaleBuilder(1)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,4,8))
                .setAmountSale(1)
                .build();
        saleList.clear();
        saleList.add(sale1);
        Assertions.assertEquals(saleList,saleDao.findSalesByDate(date));
    }
    @Test
    @Order(3)
    void findSalesByDateDoesntExist() throws DaoException {
        LocalDate date=LocalDate.of(2034,4,8);
        Sale sale1=new Sale.SaleBuilder(1)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,4,8))
                .setAmountSale(1)
                .build();
        saleList.add(sale1);
        Assertions.assertNotEquals(saleList,saleDao.findSalesByDate(date));
    }

    @Test
    @Order(4)
    void findSalesByProductExist() throws DaoException {
        Product product=new Product.ProductBuilder(1).build();
        Sale sale1=new Sale.SaleBuilder(1)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,4,8))
                .setAmountSale(1)
                .build();
        Sale sale2=new Sale.SaleBuilder(2)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,3,25))
                .setAmountSale(1)
                .build();
        saleList.clear();
        saleList.add(sale2);
        saleList.add(sale1);
        Assertions.assertEquals(saleList,saleDao.findSalesByProduct(product));
    }
    @Test
    @Order(5)
    void findSalesByProductDoesntExist() throws DaoException {
        Product product=new Product.ProductBuilder(10).build();
        Sale sale1=new Sale.SaleBuilder(1)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,4,8))
                .setAmountSale(1)
                .build();
        Sale sale2=new Sale.SaleBuilder(2)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,3,25))
                .setAmountSale(1)
                .build();
        saleList.add(sale2);
        saleList.add(sale1);
        Assertions.assertNotEquals(saleList,saleDao.findSalesByProduct(product));
    }

    @Test
    @Order(6)
    void successfulUpdate() throws DaoException {
        Sale sale1=new Sale.SaleBuilder(1)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,4,8))
                .setAmountSale(5)
                .build();
        Assertions.assertTrue(saleDao.update(sale1));
    }

    @Test
    @Order(7)
    void unsuccessfulUpdate() throws DaoException {
        Sale sale1=new Sale.SaleBuilder(12)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,4,8))
                .setAmountSale(5)
                .build();
        Assertions.assertFalse(saleDao.update(sale1));
    }

    @Test
    @Order(8)
    void successfulInsert() throws DaoException {
        Sale sale1=new Sale.SaleBuilder(10)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,4,9))
                .setAmountSale(2)
                .build();
        Assertions.assertTrue(saleDao.insert(sale1));
    }

    @Test
    @Order(9)
    void unsuccessfulInsert() throws DaoException {
        Sale sale1=new Sale.SaleBuilder(10)
                .setBuyerId(1)
                .setProductId(1)
                .setDateOfSale(LocalDate.of(2024,4,9))
                .setAmountSale(2)
                .build();
        Assertions.assertThrows(DaoException.class,()->saleDao.insert(sale1));
    }

    @Test
    @Order(10)
    void successfulDelete() throws DaoException {
        Sale sale=new Sale.SaleBuilder(1).build();
        Assertions.assertTrue(saleDao.delete(sale));
    }

    @Test
    @Order(11)
    void unsuccessfulDelete() throws DaoException {
        Sale sale=new Sale.SaleBuilder(11).build();
        Assertions.assertFalse(saleDao.delete(sale));
    }
    @Test
    @Order(12)
    void FindSalesByDateIfDateNull() throws DaoException {
        LocalDate date=null;
        List<Sale>list=new ArrayList<>();
        Assertions.assertEquals(list,saleDao.findSalesByDate(date));
    }
    @Test
    @Order(13)
    void FindSalesByProductIfProductNull() throws DaoException {
        Product product=null;
        List<Sale>list=new ArrayList<>();
        Assertions.assertEquals(list,saleDao.findSalesByProduct(product));
    }
    @Test
    @Order(14)
    void updateIfSaleNull() throws DaoException {
        Sale sale=null;
        Assertions.assertFalse(saleDao.update(sale));
    }
    @Test
    @Order(15)
    void insertIfSaleNull() throws DaoException {
        Sale sale=null;
        Assertions.assertFalse(saleDao.insert(sale));
    }
    @Test
    @Order(16)
    void deleteNull() throws DaoException {
        Sale sale=null;
        Assertions.assertFalse(saleDao.delete(sale));
    }




}