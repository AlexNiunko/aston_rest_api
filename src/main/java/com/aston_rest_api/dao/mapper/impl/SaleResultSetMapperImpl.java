package com.aston_rest_api.dao.mapper.impl;

import com.aston_rest_api.controller.SaleArguments;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.Sale;
import com.aston_rest_api.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class SaleResultSetMapperImpl implements ResultSetMapper<Sale, Product> {


    private static ResultSetMapper instance=new SaleResultSetMapperImpl();
    private ResultSetMapper productResultSetMapper=ProductResultSetMapperImpl.getInstance();

    private SaleResultSetMapperImpl() {
    }

    public static ResultSetMapper getInstance() {
        return new SaleResultSetMapperImpl();
    }




    @Override
    public Optional<Sale> mapItem(ResultSet resultSet) throws SQLException {
        Optional<Sale>optionalSale=Optional.empty();
        if (resultSet.next()){
            optionalSale=getOptionalSale(resultSet);
        }
        return optionalSale;
    }

    @Override
    public List<Sale> mapListItems(ResultSet resultSet) throws SQLException {
        List<Sale>saleList=new ArrayList<>();
        Optional<Sale>optionalSale=Optional.empty();
        while (resultSet.next()){
            optionalSale=getOptionalSale(resultSet);
            optionalSale.ifPresent(saleList::add);
        }
        return saleList;
    }

    @Override
    public Map<Long, Product> mapItemEntities(ResultSet resultSet) throws SQLException {
        Map<Long,Product>sales=new HashMap<>();
        Optional<Product>optionalProduct=Optional.empty();
        while (resultSet.next()){
            long idSale=resultSet.getLong(SaleArguments.ID_SALE);
            optionalProduct=productResultSetMapper.mapItem(resultSet);
            if (optionalProduct.isPresent()){
                Product product= optionalProduct.get();
                sales.put(idSale,product);
            }
        }
        return sales;
    }
    private Optional<Sale> getOptionalSale(ResultSet resultSet) throws SQLException {
        Sale sale=new Sale.SaleBuilder(resultSet.getLong(SaleArguments.ID_SALE))
                .setBuyerId(resultSet.getLong(SaleArguments.BUYER_id))
                .setProductId(resultSet.getLong(SaleArguments.PRODUCT_ID))
                .setDateOfSale(resultSet.getObject(SaleArguments.DATE_OF_SALE, LocalDateTime.class))
                .setAmountSale(resultSet.getInt(SaleArguments.AMOUNT_OF_SALE))
                .build();
        Optional optionalProduct=productResultSetMapper.mapItem(resultSet);
        if (optionalProduct.isPresent()){
            sale.setProductOfSale((Product) optionalProduct.get());
        }
        return Optional.ofNullable(sale);
    }

}
