package com.aston_rest_api.dao.mapper.impl;

import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.arguments.ProductDescriptionArguments;
import com.aston_rest_api.controller.arguments.SaleArguments;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.Sale;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
            Product product=new Product.ProductBuilder(resultSet.getLong(ProductArguments.ID_PRODUCT))
                    .setProductName(resultSet.getString(ProductArguments.PRODUCT_NAME))
                    .setProductPrice(resultSet.getDouble(ProductArguments.PRODUCT_PRICE))
                    .setAmount(resultSet.getInt(ProductArguments.AMOUNT_OF_PRODUCT))
                    .build();
            ProductDescription description=new ProductDescription.ProductDescriptionBuilder(resultSet.getLong(ProductDescriptionArguments.ID_DESCRIPTION))
                    .setProductId(resultSet.getLong(ProductDescriptionArguments.PRODUCT_ID))
                    .setCountryOfOrigin(resultSet.getString(ProductDescriptionArguments.COUNTRY_OF_ORIGIN))
                    .setType(resultSet.getString(ProductDescriptionArguments.TYPE_OF_PRODUCT))
                    .setBrand(resultSet.getString(ProductDescriptionArguments.BRAND_OF_PRODUCT))
                    .setIssueDate(resultSet.getObject(ProductDescriptionArguments.ISSUE_DATE, LocalDate.class))
                    .build();
            product.setDescription(description);
          optionalProduct=Optional.ofNullable(product);
          if (optionalProduct.isPresent()){
              sales.put(idSale, optionalProduct.get());
          }
        }
        return sales;
    }
    private Optional<Sale> getOptionalSale(ResultSet resultSet) throws SQLException {
        Sale sale=new Sale.SaleBuilder(resultSet.getLong(SaleArguments.ID_SALE))
                .setBuyerId(resultSet.getLong(SaleArguments.BUYER_id))
                .setProductId(resultSet.getLong(SaleArguments.PRODUCT_ID))
                .setDateOfSale(resultSet.getObject(SaleArguments.DATE_OF_SALE, LocalDate.class))
                .setAmountSale(resultSet.getInt(SaleArguments.AMOUNT_OF_SALE))
                .build();
        Product product=new Product
                .ProductBuilder(resultSet.getLong(ProductArguments.ID_PRODUCT))
                .setProductName(resultSet.getString(ProductArguments.PRODUCT_NAME))
                .setProductPrice(resultSet.getDouble(ProductArguments.PRODUCT_PRICE))
                .setAmount(resultSet.getInt(ProductArguments.AMOUNT_OF_PRODUCT))
                .build();
        ProductDescription description=new ProductDescription
                .ProductDescriptionBuilder(resultSet.getLong(ProductDescriptionArguments.ID_DESCRIPTION))
                .setProductId(resultSet.getLong(ProductDescriptionArguments.PRODUCT_ID))
                .setCountryOfOrigin(resultSet.getString(ProductDescriptionArguments.COUNTRY_OF_ORIGIN))
                .setType(resultSet.getString(ProductDescriptionArguments.TYPE_OF_PRODUCT))
                .setBrand(resultSet.getString(ProductDescriptionArguments.BRAND_OF_PRODUCT))
                .setIssueDate(resultSet.getObject(ProductDescriptionArguments.ISSUE_DATE, LocalDate.class))
                .build();
        product.setDescription(description);
        sale.setProductOfSale(product);

        return Optional.ofNullable(sale);
    }

}
