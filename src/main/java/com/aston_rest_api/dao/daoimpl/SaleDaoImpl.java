package com.aston_rest_api.dao.daoimpl;

import com.aston_rest_api.dao.BaseDao;
import com.aston_rest_api.dao.SaleDao;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.dao.mapper.impl.SaleResultSetMapperImpl;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaleDaoImpl extends BaseDao<Sale> implements SaleDao {

    private static final String INSERT_SALE =
            "INSERT INTO sales (id_sale,buyer_id,product_id,date_of_sale,amount_of_sale) VALUES (?,?,?,?,?)";
    private static final String UPDATE_AMOUNT_OF_PRODUCT =
            "UPDATE products SET (amount_of_product) VALUES (?) WHERE id_product=?";
    private static final String DELETE_SALE_BY_ID =
            "DELETE FROM sales WHERE id_sale=?";
    private static final String FIND_ALL_SALES =
            """
                    SELECT * FROM sales s JOIN products p 
                    ON s.product_id=p.id_product JOIN product_descriptions pd
                    ON pd.product_id=p.id_product 
                    """;
    private static final String UPDATE_SALE_BY_ID =
            "UPDATE sales SET(date_of_sale,amount_of_sale) VALUES (?,?) WHERE id_sale=?";
    private static final String FIND_SALES_BY_DATE=
            """
                    SELECT * FROM sales s JOIN products p 
                    ON s.product_id=p.id_product JOIN product_descriptions pd
                    ON pd.product_id=p.id_product WHERE s.date_of_sale=?
                   
                     """;
    private static final String FIND_SALES_BY_PRODUCT=
            """
                    SELECT * FROM sales s JOIN products p 
                    ON s.product_id=p.id_product JOIN product_descriptions pd
                    ON pd.product_id=p.id_product WHERE s.buyer_id=?
                    """;


    private ConnectionManagerImpl connectionManager;
    private ResultSetMapper resultSetMapper;

    private static SaleDaoImpl instance = new SaleDaoImpl();


    private SaleDaoImpl() {
        this.connectionManager = ConnectionManagerImpl.getInstance();
        resultSetMapper = SaleResultSetMapperImpl.getInstance();
    }


    @Override
    public boolean insert(Sale sale) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statementSale = connection.prepareStatement(INSERT_SALE);
                 PreparedStatement statementProduct = connection.prepareStatement(UPDATE_AMOUNT_OF_PRODUCT)) {
                connection.setAutoCommit(false);
                statementSale.setLong(1, sale.getId());
                statementSale.setLong(2, sale.getBuyerId());
                statementSale.setLong(3, sale.getProductId());
                statementSale.setString(4, sale.getDateOfSale().toString());
                statementSale.setInt(5, sale.getAmountSale());
                statementProduct.setInt(1, sale.getAmountSale());
                statementProduct.setLong(2, sale.getProductId());
                result = statementSale.executeUpdate() == 1 && statementProduct.executeUpdate() == 1;
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
    public boolean delete(Sale sale) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SALE_BY_ID)) {
            statement.setLong(1, sale.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {

        }
        return result;
    }

    @Override
    public List<Sale> findAll() {
        List<Sale> sales = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SALES);
             ResultSet resultSet = statement.executeQuery()) {
            sales = resultSetMapper.mapListItems(resultSet);
        } catch (SQLException e) {

        }
        return sales;
    }

    @Override
    public boolean update(Sale sale) {
        boolean result=false;
        try(Connection connection= connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(UPDATE_SALE_BY_ID)){
            statement.setString(1,sale.getDateOfSale().toString());
            statement.setInt(2,sale.getAmountSale());
            statement.setLong(3,sale.getId());
            result=statement.executeUpdate()==1;
        }catch (SQLException e){

        }
        return result;
    }

    @Override
    public List<Sale> findSalesByDate(LocalDateTime dateTime) {
        List<Sale>sales=new ArrayList<>();
        try(Connection connection= connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(FIND_SALES_BY_DATE)){
            statement.setString(1,dateTime.toString());
            try(ResultSet resultSet= statement.executeQuery()){
                sales=resultSetMapper.mapListItems(resultSet);
            }
        }catch (SQLException e){

        }
        return sales;
    }

    @Override
    public List<Sale> findSalesByProduct(Product product) {
        List<Sale>sales=new ArrayList<>();
        try(Connection connection= connectionManager.getConnection();
            PreparedStatement statement=connection.prepareStatement(FIND_SALES_BY_PRODUCT)){
            statement.setLong(1,product.getId());
            try(ResultSet resultSet= statement.executeQuery()){
                sales=resultSetMapper.mapListItems(resultSet);
            }
        }catch (SQLException e){

        }
        return sales;
    }
}
