package com.aston_rest_api.dao.mapper.impl;

import com.aston_rest_api.controller.ProductArguments;
import com.aston_rest_api.controller.ProductDescriptionArguments;
import com.aston_rest_api.controller.SaleArguments;
import com.aston_rest_api.controller.UserArguments;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.model.AbstractEntity;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class ProductResultSetMapperImpl implements ResultSetMapper<Product,User> {
    private static ProductResultSetMapperImpl instance=new ProductResultSetMapperImpl();

    private ProductResultSetMapperImpl() {
    }

    public static ResultSetMapper getInstance() {
        return new ProductResultSetMapperImpl();
    }


    @Override
    public Optional<Product> mapItem(ResultSet resultSet) throws SQLException {
        Optional<Product> optionalProduct=Optional.empty();
        Map<Long,User>buyers=new HashMap<>();
        if (resultSet.next()) {
            optionalProduct=getOptionalProduct(resultSet,buyers);
        }
        return optionalProduct;
    }

    @Override
    public List<Product> mapListItems(ResultSet resultSet) throws SQLException {
        List<Product>productList=new ArrayList<>();
        Optional<Product>optionalProduct=Optional.empty();
        Map<Long,User>buyers=new HashMap<>();
        while (resultSet.next()){
            optionalProduct=getOptionalProduct(resultSet,buyers);
            optionalProduct.ifPresent(productList::add);
        }
        return productList;
    }

    @Override
    public Map<Long, User> mapItemEntities(ResultSet resultSet) throws SQLException {
        Map<Long,User>buyers=new HashMap<>();
        User user;
        while (resultSet.next()){
            long idSale=resultSet.getInt(SaleArguments.ID_SALE);
            user=new User.UserBuilder(resultSet.getLong(UserArguments.USER_ID))
                    .setLogin(resultSet.getString(UserArguments.LOGIN))
                    .setPassword(resultSet.getString(UserArguments.PASSWORD))
                    .setName(resultSet.getString(UserArguments.NAME))
                    .setSurname(resultSet.getString(UserArguments.SURNAME))
                    .setUsersRole(resultSet.getInt(UserArguments.USERS_ROLE))
                    .build();
            buyers.put(idSale,user);
        }
        return buyers;
    }

    private  Optional<Product> getOptionalProduct(ResultSet resultSet, Map<Long, User> buyers) throws SQLException {
        Optional<Product> optionalUser;
        Product product=new Product.ProductBuilder(resultSet.getLong(ProductArguments.ID_PRODUCT))
                .setProductName(resultSet.getString(ProductArguments.PRODUCT_NAME))
                .setProductPrice(resultSet.getDouble(ProductArguments.PRODUCT_PRICE))
                .setAmount(resultSet.getInt(ProductArguments.AMOUNT_OF_PRODUCT))
                .setBuyers(buyers)
                .build();
        ProductDescription description=new ProductDescription.ProductDescriptionBuilder(resultSet.getLong(ProductDescriptionArguments.ID_DESCRIPTION))
                .setProductId(resultSet.getLong(ProductDescriptionArguments.PRODUCT_ID))
                .setCountryOfOrigin(resultSet.getString(ProductDescriptionArguments.COUNTRY_OF_ORIGIN))
                .setType(resultSet.getString(ProductDescriptionArguments.TYPE_OF_PRODUCT))
                .setBrand(resultSet.getString(ProductDescriptionArguments.BRAND_OF_PRODUCT))
                .setIssueDate(resultSet.getObject(ProductDescriptionArguments.ISSUE_DATE, LocalDateTime.class))
                .build();
        product.setDescription(description);
        optionalUser = Optional.ofNullable(product);
        return optionalUser;
    }

}
