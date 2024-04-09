package com.aston_rest_api.dao.mapper.impl;

import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.dao.daoimpl.SaleDaoImpl;
import com.aston_rest_api.dao.daoimpl.UserDaoImpl;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.Sale;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SaleResultSetMapperImplTest {

    private static Sale sale = new Sale.SaleBuilder(1)
            .setBuyerId(1)
            .setProductId(1)
            .setDateOfSale(LocalDate.of(2024, 4, 8))
            .setAmountSale(1)
            .build();

    private static Product product = new Product.ProductBuilder(1)
            .setProductName("hammer")
            .setProductPrice(10.25)
            .setAmount(5)
            .build();

    private static ProductDescription description = new ProductDescription.ProductDescriptionBuilder(1)
            .setProductId(1)
            .setCountryOfOrigin("China")
            .setType("hand tool")
            .setBrand("Expert")
            .setIssueDate(LocalDate.of(2024, 2, 9))
            .build();
    private static final String FIND_SALE_BY_ID =
            """
                    SELECT * FROM tool_box.sales s JOIN tool_box.products p 
                    ON s.product_id=p.id_product JOIN tool_box.product_descriptions pd
                    ON pd.product_id=p.id_product WHERE s.id_sale=?
                    """;

    private static HikariDataSource dataSource = new HikariDataSource();
    private static ConnectionManagerImpl connectionManager;

    private static ResultSetMapper mapper = SaleResultSetMapperImpl.getInstance();
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
        sale.setProductOfSale(product);
        Optional<Sale> optionalSale = Optional.empty();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SALE_BY_ID)) {
            statement.setLong(1, 1);
            try (ResultSet resultSet = statement.executeQuery()) {
                optionalSale = mapper.mapItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to map product " + e);
        }
        Optional<Sale> finalOptionalSale = optionalSale;
        Optional<Sale> finalOptionalSale1 = optionalSale;
        Assertions.assertAll(
                () -> assertTrue(finalOptionalSale1.isPresent()),
                () -> assertEquals(sale, finalOptionalSale.get())
        );
    }

    @Test
    void mapListItems() throws DaoException {
        List<Sale> saleList;
        try(Connection connection= connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(SaleDaoImpl.FIND_ALL_SALES)){
            try(ResultSet resultSet=statement.executeQuery()){
                saleList=mapper.mapListItems(resultSet);
            }
        }catch (SQLException e){
            throw new DaoException("Failed test map list Item "+e);
        }
        Assertions.assertEquals(3,saleList.size());
    }

    @Test
    void mapItemEntities() throws DaoException {
        HashMap<Long,Product>map;
        try(Connection connection=connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(UserDaoImpl.FIND_ALL_USER_PURCHASES)){
            statement.setLong(1,1);
            try(ResultSet resultSet=statement.executeQuery()){
                map= (HashMap<Long, Product>) mapper.mapItemEntities(resultSet);
            }
        }catch (SQLException e){
            throw new DaoException("Failed to test map item entities "+e);
        }
        Assertions.assertEquals(2,map.size());
    }
}