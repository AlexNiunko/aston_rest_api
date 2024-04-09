package com.aston_rest_api.dao.daoimpl;

import com.aston_rest_api.dao.BaseDao;
import com.aston_rest_api.dao.SaleDao;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.dao.mapper.impl.SaleResultSetMapperImpl;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.Sale;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleDaoImpl extends BaseDao<Sale> implements SaleDao {

    public static final String INSERT_SALE =
            "INSERT INTO tool_box.sales (id_sale,buyer_id,product_id,date_of_sale,amount_of_sale) VALUES (?,?,?,?,?)";
    public static final String UPDATE_AMOUNT_OF_PRODUCT =
            "UPDATE tool_box.products SET amount_of_product=?  WHERE id_product=?";
    public static final String DELETE_SALE_BY_ID =
            "DELETE FROM tool_box.sales WHERE id_sale=?";
    public static final String FIND_ALL_SALES =
            """
                    SELECT * FROM tool_box.sales s JOIN tool_box.products p 
                    ON s.product_id=p.id_product JOIN tool_box.product_descriptions pd
                    ON pd.product_id=p.id_product 
                    """;
    public static final String UPDATE_SALE_BY_ID =
            "UPDATE tool_box.sales SET date_of_sale=?,amount_of_sale=?  WHERE id_sale=?";
    public static final String FIND_SALES_BY_DATE=
            """
                    SELECT * FROM tool_box.sales s JOIN tool_box.products p 
                    ON s.product_id=p.id_product JOIN tool_box.product_descriptions pd
                    ON pd.product_id=p.id_product WHERE s.date_of_sale=?
                   
                     """;
    public static final String FIND_SALES_BY_PRODUCT=
            """
                    SELECT * FROM tool_box.sales s JOIN tool_box.products p 
                    ON s.product_id=p.id_product JOIN tool_box.product_descriptions pd
                    ON pd.product_id=p.id_product WHERE s.buyer_id=?
                    """;


    private ConnectionManagerImpl connectionManager;
    private ResultSetMapper resultSetMapper=SaleResultSetMapperImpl.getInstance();

    public SaleDaoImpl(ConnectionManagerImpl connectionManager ) {
        this.connectionManager = connectionManager;
    }


    @Override
    public boolean insert(Sale sale) throws DaoException {
        boolean result = false;
        if (sale==null){
            return result;
        }
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statementSale = connection.prepareStatement(INSERT_SALE);
                 PreparedStatement statementProduct = connection.prepareStatement(UPDATE_AMOUNT_OF_PRODUCT)) {
                connection.setAutoCommit(false);
                statementSale.setLong(1, sale.getId());
                statementSale.setLong(2, sale.getBuyerId());
                statementSale.setLong(3, sale.getProductId());
                statementSale.setDate(4, Date.valueOf(sale.getDateOfSale()));
                statementSale.setInt(5, sale.getAmountSale());
                statementProduct.setInt(1, sale.getAmountSale());
                statementProduct.setLong(2, sale.getProductId());
                result = statementSale.executeUpdate() == 1 && statementProduct.executeUpdate() == 1;
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException("Failed to insert a new sale "+e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to add new sale "+e);
        }
        return result;
    }

    @Override
    public boolean delete(Sale sale) throws DaoException {
        boolean result = false;
        if (sale==null){
            return result;
        }
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SALE_BY_ID)) {
            statement.setLong(1, sale.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete sale "+e);
        }
        return result;
    }

    @Override
    public List<Sale> findAll() throws DaoException {
        List<Sale> sales = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SALES);
             ResultSet resultSet = statement.executeQuery()) {
            sales = resultSetMapper.mapListItems(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Failed to find All sales "+e);
        }
        return sales;
    }

    @Override
    public boolean update(Sale sale) throws DaoException {
        boolean result=false;
        if (sale==null){
            return result;
        }
        try(Connection connection= connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(UPDATE_SALE_BY_ID)){
            statement.setDate(1, Date.valueOf(sale.getDateOfSale()));
            statement.setInt(2,sale.getAmountSale());
            statement.setLong(3,sale.getId());
            result=statement.executeUpdate()==1;
        }catch (SQLException e){
            throw new DaoException("Failed to update "+e);
        }
        return result;
    }

    @Override
    public List<Sale> findSalesByDate(LocalDate dateTime) throws DaoException {
        List<Sale>sales=new ArrayList<>();
        if (dateTime==null){
            return sales;
        }
        try(Connection connection= connectionManager.getConnection();
        PreparedStatement statement=connection.prepareStatement(FIND_SALES_BY_DATE)){
            statement.setDate(1, Date.valueOf(dateTime.toString()));
            try(ResultSet resultSet= statement.executeQuery()){
                sales=resultSetMapper.mapListItems(resultSet);
            }
        }catch (SQLException e){
            throw new DaoException("Failed to find sales by date "+e);
        }
        return sales;
    }

    @Override
    public List<Sale> findSalesByProduct(Product product) throws DaoException {
        List<Sale>sales=new ArrayList<>();
        if (product==null){
            return sales;
        }
        try(Connection connection= connectionManager.getConnection();
            PreparedStatement statement=connection.prepareStatement(FIND_SALES_BY_PRODUCT)){
            statement.setLong(1,product.getId());
            try(ResultSet resultSet= statement.executeQuery()){
                sales=resultSetMapper.mapListItems(resultSet);
            }
        }catch (SQLException e){
            throw new DaoException("Failed to find sale product "+e);
        }
        return sales;
    }
}
