package com.aston_rest_api.dao.daoimpl;

import com.aston_rest_api.dao.BaseDao;
import com.aston_rest_api.dao.ProductDao;
import com.aston_rest_api.dao.mapper.ListResultSetMapper;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.dao.mapper.impl.ProductResultSetMapperImpl;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductDaoImpl extends BaseDao<Product> implements ProductDao {

    public static final String INSERT_PRODUCT =
            """
                    INSERT INTO tool_box.products 
                    (id_product,product_name,product_price,amount_of_product)
                    VALUES(?,?,?,?)
                    """;
    public static final String INSERT_PRODUCT_DESCRIPTION =
            """
                    INSERT INTO tool_box.product_descriptions 
                    (id_description,product_id,country_of_origin,type_of_product,brand_of_product,issue_date)
                    VALUES(?,?,?,?,?,?)
                    """;
    public static final String DELETE_PRODUCT_BY_ID =
            "DELETE FROM tool_box.products WHERE id_product=?";
    public static final String DELETE_PRODUCT_DESCRIPTION_BY_ID =
            "DELETE FROM tool_box.product_descriptions WHERE id_description=?";
    public static final String FIND_ALL_PRODUCTS =
            "SELECT * FROM  tool_box.products p  JOIN tool_box.product_descriptions pd ON p.id_product=pd.product_id";
    public static final String UPDATE_PRODUCT_BY_ID =
            """
                    UPDATE tool_box.products SET product_name=?,product_price=?,amount_of_product=?
                    WHERE id_product=?
                    """;
    public static final String UPDATE_PRODUCT_DESCRIPTION_BY_ID=
            """
                    UPDATE tool_box.product_descriptions SET country_of_origin=?,type_of_product=?,brand_of_product=?,issue_date=?
                     WHERE id_description=?
                    """;
    public static final String FIND_PRODUCT_BY_ID=
            """
                    SELECT * FROM tool_box.products p JOIN tool_box.product_descriptions pd 
                    ON p.id_product=pd.product_id 
                    WHERE p.id_product=?
                    """;
    public static final String FIND_ALL_PRODUCT_BUYERS=
            """
                    SELECT * FROM tool_box.products p JOIN tool_box.sales s ON p.id_product=s.product_id
                    JOIN tool_box.users u ON u.user_id=s.buyer_id
                    WHERE p.id_product=? 
                    """;

    private ConnectionManagerImpl connectionManager;
    private ResultSetMapper resultSetMapper=ProductResultSetMapperImpl.getInstance();;


    public ProductDaoImpl(ConnectionManagerImpl connectionManager) {
        this.connectionManager = connectionManager;
    }


    @Override
    public boolean insert(Product product) throws DaoException {
        boolean result = false;
        if (product==null){
            return result;
        }
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement productStatement = connection.prepareStatement(INSERT_PRODUCT);
                 PreparedStatement descriptionStatement = connection.prepareStatement(INSERT_PRODUCT_DESCRIPTION)) {
                connection.setAutoCommit(false);
                productStatement.setLong(1, product.getId());
                productStatement.setString(2, product.getProductName());
                productStatement.setDouble(3, product.getProductPrice());
                productStatement.setInt(4, product.getAmount());
                descriptionStatement.setLong(1, product.getDescription().getId());
                descriptionStatement.setLong(2,product.getDescription().getProductID());
                descriptionStatement.setString(3, product.getDescription().getCountryOfOrigin());
                descriptionStatement.setString(4, product.getDescription().getType());
                descriptionStatement.setString(5, product.getDescription().getBrand());
                descriptionStatement.setDate(6, Date.valueOf(product.getDescription().getIssueDate()));
                result = (productStatement.executeUpdate() == 1 && descriptionStatement.executeUpdate() == 1);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Failed to insert new product "+e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to insert product "+e);
        }
        return result;
    }

    @Override
    public boolean delete(Product product) throws DaoException {
        boolean result = false;
        if (product==null){
            return result;
        }
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement productStatement = connection.prepareStatement(DELETE_PRODUCT_BY_ID);
                 PreparedStatement descriptionStatement = connection.prepareStatement(DELETE_PRODUCT_DESCRIPTION_BY_ID)) {
                connection.setAutoCommit(false);
                productStatement.setLong(1, product.getId());
                descriptionStatement.setLong(1, product.getDescription().getId());
                result = productStatement.executeUpdate() == 1 && descriptionStatement.executeUpdate() == 0;
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Failed to delete product or product description "+e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed delete product "+e);
        }
        return result;
    }

    @Override
    public List<Product> findAll() throws DaoException {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PRODUCTS);
             ResultSet resultSet = statement.executeQuery()) {
            productList = resultSetMapper.mapListItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Failed to find all products "+e);
        }
        return productList;
    }

    @Override
    public boolean update(Product product) throws DaoException {
        boolean result=false;
        if (product==null){
            return result;
        }
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
               statementDescription.setDate(4, Date.valueOf(product.getDescription().getIssueDate()));
               statementDescription.setLong(5,product.getDescription().getId());
               result=(statementProduct.executeUpdate()==1) || (statementDescription.executeUpdate()==1) ;
               connection.commit();
            }catch (SQLException e){
                connection.rollback();
                throw new DaoException("Failed to update product "+e);
            }
            finally {
                connection.setAutoCommit(true);
            }
        }catch (SQLException e){
            throw new DaoException("Failed to update product "+e);
        }
        return result;
    }


    @Override
    public Optional<Product> findProductById(long idProduct) throws DaoException {
        Optional<Product>optionalProduct=Optional.empty();
        try(Connection connection= connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(FIND_PRODUCT_BY_ID)) {
            statement.setLong(1,idProduct);
            try(ResultSet resultSet= statement.executeQuery()) {
             optionalProduct=resultSetMapper.mapItem(resultSet);
            }
        }catch (SQLException e){
            throw new DaoException("Failed to find product by id "+e);
        }
        return optionalProduct;
    }

    @Override
    public Optional<Product> findProductBuyers(Product product) throws DaoException {
        Optional<Product>optionalProduct=Optional.empty();
        if (product==null){
            return optionalProduct;
        }
        List<User>buyers;
        long productId= product.getId();
        try(Connection connection= connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(FIND_ALL_PRODUCT_BUYERS) ){
            statement.setLong(1,product.getId());
            try(ResultSet resultSet= statement.executeQuery()){
                ListResultSetMapper<User>listResultSetMapper=(ListResultSetMapper<User>)resultSetMapper;
                buyers=listResultSetMapper.mapItemEntities(resultSet);
                product.setBuyers(buyers);
            }
        }catch (SQLException e){
            throw new DaoException("Failed to find product buyers "+e);
        }
        product.setBuyers(buyers);
        optionalProduct=Optional.ofNullable(product);
        return optionalProduct;
    }
}
