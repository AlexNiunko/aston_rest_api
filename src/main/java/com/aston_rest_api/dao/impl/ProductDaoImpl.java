package com.aston_rest_api.dao.impl;

import com.aston_rest_api.controller.ProductArguments;
import com.aston_rest_api.dao.BaseDao;
import com.aston_rest_api.dao.ProductDao;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.dao.mapper.impl.ProductResultSetMapperImpl;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductDaoImpl extends BaseDao<Product> implements ProductDao {

    private static final String INSERT_PRODUCT =
            """
                    INSERT INTO products 
                    (id_product,product_name,product_price,amount_of_product)
                    VALUES(?,?,?,?)
                    """;
    private static final String INSERT_PRODUCT_DESCRIPTION =
            """
                    INSERT INTO product_descriptions 
                    (id_description,product_id,country_of_origin,type_of_product,brand_of_product,issue_date)
                    VALUES(?,?,?,?,?,?)
                    """;
    private static final String DELETE_PRODUCT_BY_ID =
            "DELETE FROM products WHERE id_product=?";
    private static final String DELETE_PRODUCT_DESCRIPTION_BY_ID =
            "DELETE FROM product_descriptions WHERE id_description=?";
    private static final String FIND_ALL_PRODUCTS =
            "SELECT * FROM  products p  JOIN product_descriptions pd ON p.id_product=pd.product_id";
    private static final String UPDATE_PRODUCT_BY_ID =
            """
                    UPDATE products SET (product_name,product_price,amount_of_product) VALUES (?,?,?)
                    WHERE id_product=?
                    """;
    private static final String UPDATE_PRODUCT_DESCRIPTION_BY_ID=
            """
                    UPDATE product_descriptions SET(country_of_origin,type_of_product,brand_of_product,issue_date)
                    VALUES (?,?,?,?) WHERE id_description=?
                    """;
    private static final String FIND_PRODUCT_BY_ID=
            """
                    SELECT from products p JOIN product_descriptions pd 
                    ON p.id_product=pd.product_id 
                    WHERE p.id_product=?
                    """;
    private static final String FIND_ALL_PRODUCT_BUYERS=
            """
                    SELECT * FROM products p JOIN sales s ON p.id_product=s.product_id
                    JOIN users u ON u.user_id=s.buyer_id
                    WHERE p.id_product=? 
                    """;

    private ConnectionManagerImpl connectionManager;
    private ResultSetMapper resultSetMapper;

    private static ProductDaoImpl instance = new ProductDaoImpl();


    private ProductDaoImpl() {
        this.connectionManager = ConnectionManagerImpl.getInstance();
        resultSetMapper = ProductResultSetMapperImpl.getInstance();
    }


    @Override
    public boolean insert(Product product) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement productStatement = connection.prepareStatement(INSERT_PRODUCT);
                 PreparedStatement descriptionStatement = connection.prepareStatement(INSERT_PRODUCT_DESCRIPTION)) {
                connection.setAutoCommit(false);
                productStatement.setLong(1, product.getId());
                productStatement.setString(2, product.getProductName());
                productStatement.setDouble(3, product.getProductPrice());
                productStatement.setInt(4, product.getAmount());
                descriptionStatement.setLong(1, product.getDescription().getId());
                descriptionStatement.setLong(2, product.getDescription().getProductID());
                descriptionStatement.setString(3, product.getDescription().getCountryOfOrigin());
                descriptionStatement.setString(4, product.getDescription().getType());
                descriptionStatement.setString(5, product.getDescription().getBrand());
                descriptionStatement.setString(6, product.getDescription().getIssueDate().toString());
                result = (productStatement.executeUpdate() == 1 && descriptionStatement.executeUpdate() == 1);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {

        }
        return result;
    }

    @Override
    public boolean delete(Product product) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement productStatement = connection.prepareStatement(DELETE_PRODUCT_BY_ID);
                 PreparedStatement descriptionStatement = connection.prepareStatement(DELETE_PRODUCT_DESCRIPTION_BY_ID)) {
                connection.setAutoCommit(false);
                productStatement.setLong(1, product.getId());
                descriptionStatement.setLong(1, product.getDescription().getId());
                result = (productStatement.executeUpdate() == 1 && descriptionStatement.executeUpdate() == 1);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {

        }
        return result;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PRODUCTS);
             ResultSet resultSet = statement.executeQuery()) {
            productList = resultSetMapper.mapListItems(resultSet);
        } catch (SQLException e) {

        }
        return productList;
    }

    @Override
    public boolean update(Product product) {
        boolean result=false;
        try(Connection connection= connectionManager.getConnection()){
            try(PreparedStatement statementProduct=connection.prepareStatement(UPDATE_PRODUCT_BY_ID);
               PreparedStatement statementDescription=connection.prepareStatement(UPDATE_PRODUCT_DESCRIPTION_BY_ID)){
                connection.setAutoCommit(false);
               statementProduct.setString(1,product.getProductName());
               statementProduct.setDouble(2,product.getProductPrice());
               statementProduct.setInt(3,product.getAmount());
               statementProduct.setLong(4,product.getId());
               statementDescription.setString(1,product.getDescription().getCountryOfOrigin());
               statementDescription.setString(2,product.getDescription().getType());
               statementDescription.setString(3,product.getDescription().getBrand());
               statementDescription.setString(4,product.getDescription().getIssueDate().toString());
               result=(statementProduct.executeUpdate()==1 && statementDescription.executeUpdate()==1);
               connection.commit();
            }catch (SQLException e){
                connection.rollback();
            }
            finally {
                connection.setAutoCommit(true);
            }
        }catch (SQLException e){

        }
        return result;
    }


    @Override
    public Optional<Product> findProductById(Product product) {
        Optional<Product>optionalProduct=Optional.empty();
        try(Connection connection= connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(FIND_PRODUCT_BY_ID)) {
            statement.setLong(1,product.getId());
            try(ResultSet resultSet= statement.executeQuery()) {
             optionalProduct=resultSetMapper.mapItem(resultSet);
            }
        }catch (SQLException e){

        }
        return optionalProduct;
    }

    @Override
    public Optional<Product> findProductBuyers(Product product) {
        Optional<Product>optionalProduct=Optional.empty();
        Map<Long,User>buyers;
        long productId= product.getId();
        try(Connection connection= connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(FIND_ALL_PRODUCT_BUYERS) ){
            statement.setLong(1,product.getId());
            try(ResultSet resultSet= statement.executeQuery()){
                buyers=resultSetMapper.mapItemEntities(resultSet);
                product.setBuyers(buyers);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        product.setBuyers(buyers);
        optionalProduct=Optional.ofNullable(product);
        return optionalProduct;
    }
}
