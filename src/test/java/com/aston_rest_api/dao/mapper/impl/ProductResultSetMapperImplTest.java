package com.aston_rest_api.dao.mapper.impl;

import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.dao.mapper.ListResultSetMapper;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.User;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductResultSetMapperImplTest {
    private static Product product = new Product.ProductBuilder(1)
            .setProductName("hammer")
            .setProductPrice(10.25)
            .setAmount(5)
            .build();
    private  static ProductDescription description = new ProductDescription.ProductDescriptionBuilder(1)
            .setProductId(1)
            .setCountryOfOrigin("China")
            .setType("hand tool")
            .setBrand("Expert")
            .setIssueDate(LocalDate.of(2024, 2, 9))
            .build();
    private static HikariDataSource dataSource = new HikariDataSource();
    private static ConnectionManagerImpl connectionManager;

    private static ResultSetMapper mapper = ProductResultSetMapperImpl.getInstance();
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
    void setUp() throws SQLException {
        connectionManager = ConnectionManagerImpl.getInstance(dataSource);
    }

    @AfterEach
    void setDown() {
        connectionManager = null;
    }

    @Test
    void mapItem() throws DaoException {
        product.setDescription(description);
        Optional<Product>optionalProduct=Optional.empty();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ProductDaoImpl.FIND_PRODUCT_BY_ID)) {
            statement.setLong(1,1);
            try (ResultSet resultSet = statement.executeQuery()) {
                optionalProduct=mapper.mapItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to map product " + e);
        }
        Optional<Product> finalOptionalProduct = optionalProduct;
        Optional<Product> finalOptionalProduct1 = optionalProduct;
        Assertions.assertAll(
                () -> assertTrue(finalOptionalProduct.isPresent()),
                () -> assertEquals(product, finalOptionalProduct1.get())
        );
    }

    @Test
    void mapListItems() throws DaoException {
        List<Product> productList;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ProductDaoImpl.FIND_ALL_PRODUCTS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                productList=mapper.mapListItems(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to map product list " + e);
        }
        Assertions.assertEquals(3,productList.size());
    }

    @Test
    void mapItemEntities() throws DaoException {
        List<User> buyers;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(ProductDaoImpl.FIND_ALL_PRODUCT_BUYERS)) {
            statement.setLong(1,product.getId());
            ListResultSetMapper listMapper=(ListResultSetMapper) mapper;
            try (ResultSet resultSet = statement.executeQuery()) {
                buyers=listMapper.mapItemEntities(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to map product list " + e);
        }
        Assertions.assertEquals(2,buyers.size());
    }
}