package com.astonrest.dao.impl;

import com.astonrest.dao.BaseDao;
import com.astonrest.dao.ProductDao;
import com.astonrest.dao.mapper.ListResultSetMapper;
import com.astonrest.dao.mapper.ResultSetMapper;
import com.astonrest.dao.mapper.impl.ProductResultSetMapperImpl;
import com.astonrest.dao.mapper.impl.SaleResultSetMapperImpl;
import com.astonrest.db.ConnectionManager;
import com.astonrest.exception.DaoException;
import com.astonrest.model.Product;
import com.astonrest.model.Sale;
import com.astonrest.model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements BaseDao<Product>, ProductDao {

    public static final String INSERT_PRODUCT =
            """
                    INSERT 
                    INTO tool_box.products 
                    (id_product,product_name,product_price,amount_of_product)
                    VALUES(?,?,?,?)
                    """;
    public static final String INSERT_PRODUCT_DESCRIPTION =
            """
                    INSERT 
                    INTO tool_box.product_descriptions 
                    (id_description,product_id,country_of_origin,type_of_product,brand_of_product,issue_date)
                    VALUES(?,?,?,?,?,?)
                    """;
    public static final String DELETE_PRODUCT_BY_ID =
            """
                    DELETE 
                    FROM tool_box.products 
                    WHERE id_product=?
                    """;
    public static final String DELETE_PRODUCT_DESCRIPTION_BY_ID =
            """
                    DELETE 
                    FROM tool_box.product_descriptions 
                    WHERE id_description=? 
                    """;
    public static final String FIND_ALL_PRODUCTS =
            """
                    SELECT * 
                    FROM  tool_box.products p  
                    JOIN tool_box.product_descriptions pd 
                    ON p.id_product=pd.product_id 
                    """;
    public static final String UPDATE_PRODUCT_BY_ID =
            """
                    UPDATE tool_box.products 
                    SET product_name=?,product_price=?,amount_of_product=?
                    WHERE id_product=?
                    """;
    public static final String UPDATE_PRODUCT_DESCRIPTION_BY_ID =
            """
                    UPDATE tool_box.product_descriptions 
                    SET country_of_origin=?,type_of_product=?,brand_of_product=?,issue_date=?
                    WHERE id_description=?
                    """;
    public static final String FIND_PRODUCT_BY_ID =
            """
                    SELECT * 
                    FROM tool_box.products p 
                    JOIN tool_box.product_descriptions pd 
                    ON p.id_product=pd.product_id 
                    WHERE p.id_product=?
                    """;
    public static final String FIND_ALL_PRODUCT_BUYERS =
            """
                    SELECT * 
                    FROM tool_box.products p 
                    JOIN tool_box.sales s 
                    ON p.id_product=s.product_id
                    JOIN tool_box.users u 
                    ON u.user_id=s.buyer_id
                    WHERE p.id_product=? 
                    """;
    public static final String FIND_ALL_PRODUCT_ORDERS =
            """
                    SELECT*
                    FROM tool_box.sales 
                    WHERE product_id=?
                    """;

    private ConnectionManager connectionManager;
    private ResultSetMapper<Product> productResultSetMapper = ProductResultSetMapperImpl.getInstance();
    private ResultSetMapper<Sale> saleResultSetSaleMapper = SaleResultSetMapperImpl.getInstance();
    private static ProductDao productDao;

    public static ProductDao getProductDao(ConnectionManager connectionManager) {
        if (productDao == null) {
            productDao = new ProductDaoImpl(connectionManager);
        }
        return productDao;
    }

    private ProductDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean insert(Product product) throws DaoException {
        boolean result = false;
        if (product == null) {
            return result;
        }
        result = isInsert(product);
        return result;
    }


    @Override
    public boolean delete(Product product) throws DaoException {
        boolean result = false;
        if (product == null) {
            return result;
        }
        result = isDelete(product);
        return result;
    }


    @Override
    public List<Product> findAll() throws DaoException {
        List<Product> productList;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PRODUCTS);
             ResultSet resultSet = statement.executeQuery()) {
            productList = productResultSetMapper.mapListItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Failed to find all products " + e);
        }
        return productList;
    }

    @Override
    public boolean update(Product product) throws DaoException {
        boolean result = false;
        if (product == null) {
            return result;
        }
        result = isUpdate(product);
        return result;
    }


    @Override
    public Optional<Product> findProductById(long idProduct) throws DaoException {
        Optional<Product> optionalProduct = Optional.empty();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PRODUCT_BY_ID)) {
            statement.setLong(1, idProduct);
            try (ResultSet resultSet = statement.executeQuery()) {
                optionalProduct = productResultSetMapper.mapItem(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find product by id " + e);
        }
        return optionalProduct;
    }

    @Override
    public Optional<Product> findProductBuyers(Product product) throws DaoException {
        Optional<Product> optionalProduct = Optional.empty();
        if (product == null) {
            return optionalProduct;
        }
        List<User> buyers;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PRODUCT_BUYERS)) {
            statement.setLong(1, product.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                ListResultSetMapper<User> listResultSetMapper = (ListResultSetMapper<User>) productResultSetMapper;
                buyers = listResultSetMapper.mapItemEntities(resultSet);
                product.setBuyers(buyers);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find product buyers " + e);
        }
        optionalProduct = Optional.ofNullable(product);
        return optionalProduct;
    }

    @Override
    public Optional<Product> findAllProductOrders(Product product) throws DaoException {
        Optional<Product> optionalProduct = Optional.empty();
        if (product == null) {
            return optionalProduct;
        }
        List<Sale> orders;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PRODUCT_ORDERS)) {
            statement.setLong(1, product.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                orders = saleResultSetSaleMapper.mapListItems(resultSet);
                product.setOrders(orders);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find product orders" + e);
        }
        optionalProduct = Optional.ofNullable(product);
        return optionalProduct;
    }

    private boolean isInsert(Product product) throws DaoException {
        boolean result;
        try (Connection connection = connectionManager.getConnection()) {
            result = isResultInsertTransaction(product, connection);
        } catch (SQLException e) {
            throw new DaoException("Failed to insert product " + e);
        }
        return result;
    }

    private static boolean isResultInsertTransaction(Product product, Connection connection) throws SQLException, DaoException {
        boolean result;
        try (PreparedStatement productStatement = connection.prepareStatement(INSERT_PRODUCT);
             PreparedStatement descriptionStatement = connection.prepareStatement(INSERT_PRODUCT_DESCRIPTION)) {
            connection.setAutoCommit(false);
            inputDataInsert(product, productStatement, descriptionStatement);
            result = (productStatement.executeUpdate() == 1 && descriptionStatement.executeUpdate() == 1);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new DaoException("Failed to insert new product " + e);
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    private static void inputDataInsert(Product product, PreparedStatement productStatement, PreparedStatement descriptionStatement) throws SQLException {
        productStatement.setLong(1, product.getId());
        productStatement.setString(2, product.getProductName());
        productStatement.setDouble(3, product.getProductPrice());
        productStatement.setInt(4, product.getAmount());
        descriptionStatement.setLong(1, product.getDescription().getId());
        descriptionStatement.setLong(2, product.getDescription().getProductID());
        descriptionStatement.setString(3, product.getDescription().getCountryOfOrigin());
        descriptionStatement.setString(4, product.getDescription().getType());
        descriptionStatement.setString(5, product.getDescription().getBrand());
        descriptionStatement.setDate(6, Date.valueOf(product.getDescription().getIssueDate()));
    }

    private boolean isDelete(Product product) throws DaoException {
        boolean result;
        try (Connection connection = connectionManager.getConnection()) {
            result = isResultDeleteTransaction(product, connection);
        } catch (SQLException e) {
            throw new DaoException("Failed delete product " + e);
        }
        return result;
    }

    private static boolean isResultDeleteTransaction(Product product, Connection connection) throws SQLException, DaoException {
        boolean result;
        try (PreparedStatement productStatement = connection.prepareStatement(DELETE_PRODUCT_BY_ID);
             PreparedStatement descriptionStatement = connection.prepareStatement(DELETE_PRODUCT_DESCRIPTION_BY_ID)) {
            inputDataDelete(product, connection, productStatement, descriptionStatement);
            result = productStatement.executeUpdate() == 1 && descriptionStatement.executeUpdate() == 0;
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new DaoException("Failed to delete product or product description " + e);
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    private static void inputDataDelete(Product product, Connection connection, PreparedStatement productStatement, PreparedStatement descriptionStatement) throws SQLException {
        connection.setAutoCommit(false);
        productStatement.setLong(1, product.getId());
        descriptionStatement.setLong(1, product.getDescription().getId());
    }

    private boolean isUpdate(Product product) throws DaoException {
        boolean result;
        try (Connection connection = connectionManager.getConnection()) {
            result = isResultUpdateTransaction(product, connection);
        } catch (SQLException e) {
            throw new DaoException("Failed to update product " + e);
        }
        return result;
    }

    private static boolean isResultUpdateTransaction(Product product, Connection connection) throws SQLException, DaoException {
        boolean result;
        try (PreparedStatement statementProduct = connection.prepareStatement(UPDATE_PRODUCT_BY_ID);
             PreparedStatement statementDescription = connection.prepareStatement(UPDATE_PRODUCT_DESCRIPTION_BY_ID)) {
            connection.setAutoCommit(false);
            inputDataUpdate(product, statementProduct, statementDescription);
            result = (statementProduct.executeUpdate() == 1) || (statementDescription.executeUpdate() == 1);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new DaoException("Failed to update product " + e);
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }

    private static void inputDataUpdate(Product product, PreparedStatement statementProduct, PreparedStatement statementDescription) throws SQLException {
        statementProduct.setString(1, product.getProductName());
        statementProduct.setDouble(2, product.getProductPrice());
        statementProduct.setInt(3, product.getAmount());
        statementProduct.setLong(4, product.getId());
        statementDescription.setString(1, product.getDescription().getCountryOfOrigin());
        statementDescription.setString(2, product.getDescription().getType());
        statementDescription.setString(3, product.getDescription().getBrand());
        statementDescription.setDate(4, Date.valueOf(product.getDescription().getIssueDate()));
        statementDescription.setLong(5, product.getDescription().getId());
    }
}
