package com.astonrest.dao.mapper.impl;

import com.astonrest.controller.arguments.SaleArguments;
import com.astonrest.dao.mapper.ResultSetMapper;
import com.astonrest.model.Sale;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class SaleResultSetMapperImpl implements ResultSetMapper<Sale> {

    private static ResultSetMapper<Sale> instance = new SaleResultSetMapperImpl();

    private SaleResultSetMapperImpl() {
    }

    public static ResultSetMapper<Sale> getInstance() {
        return instance;
    }


    @Override
    public Optional<Sale> mapItem(ResultSet resultSet) throws SQLException {
        Optional<Sale> optionalSale = Optional.empty();
        if (resultSet.next()) {
            optionalSale = getOptionalSale(resultSet);
        }
        return optionalSale;
    }

    @Override
    public List<Sale> mapListItems(ResultSet resultSet) throws SQLException {
        List<Sale> saleList = new ArrayList<>();
        Optional<Sale> optionalSale;
        while (resultSet.next()) {
            optionalSale = getOptionalSale(resultSet);
            optionalSale.ifPresent(saleList::add);
        }
        return saleList;
    }

    private Optional<Sale> getOptionalSale(ResultSet resultSet) throws SQLException {
        Sale sale = new Sale.SaleBuilder(resultSet.getLong(SaleArguments.ID_SALE))
                .setBuyerId(resultSet.getLong(SaleArguments.BUYER_ID))
                .setProductId(resultSet.getLong(SaleArguments.PRODUCT_ID))
                .setDateOfSale(resultSet.getObject(SaleArguments.DATE_OF_SALE, LocalDate.class))
                .setAmountSale(resultSet.getInt(SaleArguments.AMOUNT_OF_SALE))
                .build();
        return Optional.ofNullable(sale);
    }

}
