package com.aston_rest_api.dao.daoimpl;

import com.aston_rest_api.dao.ProductDao;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDaoImplTest {
    private static HikariDataSource dataSource = new HikariDataSource();
    private static ConnectionManagerImpl connectionManager;
    private static ProductDaoImpl productDao;
    private static Product product;

    private static List<User> buyers = new ArrayList<>();
    private static List<Product> productList = new ArrayList<>();
    private static List<Sale>orders=new ArrayList<>();
    private static ProductDescription description=new ProductDescription.ProductDescriptionBuilder(1)
            .setProductId(1)
            .setCountryOfOrigin("China")
            .setType("hand tool")
            .setBrand("Makita")
            .setIssueDate(LocalDate.of(2024,8,02))
            .build();

    private static Sale sale1=new Sale.SaleBuilder(1)
            .setBuyerId(1)
            .setProductId(1)
            .setDateOfSale(LocalDate.of(2024,4,8))
            .setAmountSale(1)
            .build();
    private static Sale sale2=new Sale.SaleBuilder(2)
            .setBuyerId(1)
            .setProductId(1)
            .setDateOfSale(LocalDate.of(2024,3,25))
            .setAmountSale(1)
            .build();






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
        productDao = new ProductDaoImpl(connectionManager);
    }

    @AfterEach
    void setDown() {
        productDao = null;
    }

    @Test
    @Order(1)
    void successfulFindProductBuyers() throws DaoException {
        User user = new User.UserBuilder(1)
                .setLogin("michai@gmail.com")
                .setPassword("asrg346")
                .setName("Michail")
                .setSurname("Radzivil")
                .setUsersRole(1)
                .build();
        buyers.add(user);
        buyers.add(user);
        product = new Product.ProductBuilder(1).build();
        Assertions.assertEquals(buyers, productDao.findProductBuyers(product).get().getBuyers());
    }

    @Test
    @Order(2)
    void unsuccessfulFindProductBuyers() throws DaoException {
        User user = new User.UserBuilder(1)
                .setLogin("michai@gmail.com")
                .setPassword("123")
                .setName("Michail")
                .setSurname("Radzivil")
                .setUsersRole(1)
                .build();
        buyers.add(user);
        buyers.add(user);
        product = new Product.ProductBuilder(15).build();
        Assertions.assertNotEquals(buyers, productDao.findProductBuyers(product).get().getBuyers());
    }

    @Test
    @Order(3)
    void successfulFindAllProducts() throws DaoException {
        Product product1 = new Product.ProductBuilder(1).setProductName("hammer").setProductPrice(10.25).setAmount(5).build();
        Product product2 = new Product.ProductBuilder(2).setProductName("hammer drill").setProductPrice(125.25).setAmount(2).build();
        Product product3 = new Product.ProductBuilder(3).setProductName("grinder tool").setProductPrice(84.15).setAmount(4).build();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        Assertions.assertEquals(productList, productDao.findAll());
    }

    @Test
    @Order(4)
    void unsuccessfulFindAllProducts() throws DaoException {
        Product product1 = new Product.ProductBuilder(1).setProductName("hammerrr").setProductPrice(10.25).setAmount(5).build();
        Product product2 = new Product.ProductBuilder(2).setProductName("hammer drill").setProductPrice(125.25).setAmount(2).build();
        Product product3 = new Product.ProductBuilder(3).setProductName("grinder tool").setProductPrice(84.15).setAmount(4).build();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        Assertions.assertNotEquals(productList, productDao.findAll());
    }
    @Test
    @Order(5)
    void successfulFindProductById() throws DaoException {
        Product expected = new Product.ProductBuilder(1).setProductName("hammer").setProductPrice(10.25).setAmount(5).build();
        Product product=new Product.ProductBuilder(1).build();
        Assertions.assertEquals(expected,productDao.findProductById(product.getId()).get());
    }
    @Test
    @Order(6)
    void unsuccessfulFindProductById() throws DaoException {
        Product product=new Product.ProductBuilder().build();
        Assertions.assertFalse(productDao.findProductById(product.getId()).isPresent());
    }
    @Test
    @Order(7)
    void successfulFindAllProductOrders() throws DaoException{
        product = new Product.ProductBuilder(1)
                .setProductName("hammer")
                .setProductPrice(10.25)
                .setAmount(5)
                .build();
        product.setDescription(description);
        orders.add(sale1);
        orders.add(sale2);
        Optional<Product>productOptional=productDao.findAllProductOrders(product);
        Assertions.assertAll(
                ()->assertTrue(productOptional.isPresent()),
                ()->assertEquals(orders,productOptional.get().getOrders())
        );
    }
    @Test
    @Order(8)
    void unsuccessfulFindAllProductOrders() throws DaoException{
        product = new Product.ProductBuilder(10)
                .setProductName("hammer")
                .setProductPrice(10.25)
                .setAmount(5)
                .build();
        product.setDescription(description);
        orders.add(sale1);
        orders.add(sale2);
        Optional<Product>productOptional=productDao.findAllProductOrders(product);
        Assertions.assertNotEquals(orders,productOptional.get().getOrders());
    }
    @Test
    @Order(9)
    void FindAllProductOrdersIfNull() throws DaoException{
        product = null;
        orders.add(sale1);
        orders.add(sale2);
        Optional<Product>productOptional=productDao.findAllProductOrders(product);
        Assertions.assertTrue(productOptional.isEmpty());
    }

    @Test
    @Order(10)
    void successfulUpdate() throws DaoException {
        Product product = new Product.ProductBuilder(1)
                .setProductName("other")
                .setProductPrice(100.25)
                .setAmount(150)
                .build();
        ProductDescription description=new ProductDescription
                .ProductDescriptionBuilder(1)
                .setProductId(1)
                .setCountryOfOrigin("Russia")
                .setType("other")
                .setBrand("other")
                .setIssueDate(LocalDate.of(2023,2,9))
                .build();
        product.setDescription(description);
        Assertions.assertTrue(productDao.update(product));
    }
    @Test
    @Order(11)
    void unsuccessfulUpdate() throws DaoException {
        Product product = new Product.ProductBuilder(10)
                .setProductName("other")
                .setProductPrice(100.25)
                .setAmount(150)
                .build();
        ProductDescription description=new ProductDescription
                .ProductDescriptionBuilder(10)
                .setProductId(1)
                .setCountryOfOrigin("Russia")
                .setType("other")
                .setBrand("other")
                .setIssueDate(LocalDate.of(2022,2,9))
                .build();
        product.setDescription(description);
       Assertions.assertFalse(productDao.update(product));
    }

    @Test
    @Order(12)
    void successfulInsert() throws DaoException {
        Product product = new Product.ProductBuilder(100)
                .setProductName("new product")
                .setProductPrice(1.25)
                .setAmount(1)
                .build();
        ProductDescription description=new ProductDescription
                .ProductDescriptionBuilder(101)
                .setProductId(100)
                .setCountryOfOrigin("Republic of Belarus")
                .setType("new type")
                .setBrand("new brand")
                .setIssueDate(LocalDate.of(2022,2,9))
                .build();
        product.setDescription(description);
        Assertions.assertTrue(productDao.insert(product));
    }
    @Test
    @Order(13)
    void unsuccessfulInsert() throws DaoException {
        Product product = new Product.ProductBuilder(110)
                .setProductName("new")
                .setProductPrice(11.25)
                .setAmount(11)
                .build();
        ProductDescription description=new ProductDescription
                .ProductDescriptionBuilder(111)
                .setProductId(110)
                .setCountryOfOrigin("Republic of Belarus")
                .setType("new type")
                .setBrand("new brand")
                .setIssueDate(LocalDate.of(2022,2,9))
                .build();
        product.setDescription(description);
        productDao.insert(product);
       Assertions.assertThrows(DaoException.class,()->productDao.insert(product));
    }

    @Test
    @Order(14)
    void successfulDelete() throws DaoException {
        Product product = new Product.ProductBuilder(1)
                .build();
        ProductDescription description=new ProductDescription
                .ProductDescriptionBuilder(1)
                .setProductId(1)
                .build();
        product.setDescription(description);
        Assertions.assertTrue(productDao.delete(product));
    }
    @Test
    @Order(15)
    void unsuccessfulDelete() throws DaoException {
        Product product = new Product.ProductBuilder(19)
                .build();
        ProductDescription description=new ProductDescription
                .ProductDescriptionBuilder(19)
                .setProductId(1)
                .build();
        product.setDescription(description);
        Assertions.assertFalse(productDao.delete(product));
    }
    @Test
    @Order(16)
    void deleteIfProductNull() throws DaoException {
        product=null;
        Assertions.assertFalse(productDao.delete(product));
    }
    @Test
    @Order(17)
    void updateIfProductNull() throws DaoException {
        product=null;
        Assertions.assertFalse(productDao.update(product));
    }
    @Test
    @Order(18)
    void insertIfProductNull() throws DaoException {
        product=null;
        Assertions.assertFalse(productDao.insert(product));
    }
    @Test
    @Order(19)
    void findIfProductNull() throws DaoException {
        Assertions.assertFalse(productDao.findProductById(0).isPresent());
    }
    @Test
    @Order(20)
    void findBuyersIfProductNull() throws DaoException {
        product=null;
        Assertions.assertFalse(productDao.findProductBuyers(product).isPresent());
    }

}