package com.astonrest.dao.mapper.impl;

import com.astonrest.controller.arguments.ProductArguments;
import com.astonrest.controller.arguments.ProductDescriptionArguments;
import com.astonrest.controller.arguments.UserArguments;
import com.astonrest.dao.mapper.ListResultSetMapper;
import com.astonrest.dao.mapper.ResultSetMapper;
import com.astonrest.model.Product;
import com.astonrest.model.ProductDescription;
import com.astonrest.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ProductResultSetMapperImpl implements ResultSetMapper<Product>, ListResultSetMapper<User> {
    private static ProductResultSetMapperImpl instance = new ProductResultSetMapperImpl();

    private ProductResultSetMapperImpl() {
    }

    public static ResultSetMapper getInstance() {
        return instance;
    }


    @Override
    public Optional<Product> mapItem(ResultSet resultSet) throws SQLException {
        Optional<Product> optionalProduct = Optional.empty();
        List<User> buyers = new ArrayList<>();
        if (resultSet.next()) {
            optionalProduct = getOptionalProduct(resultSet, buyers);
        }
        return optionalProduct;
    }

    @Override
    public List<Product> mapListItems(ResultSet resultSet) throws SQLException {
        List<Product> productList = new ArrayList<>();
        Optional<Product> optionalProduct;
        List<User> buyers = new ArrayList<>();
        while (resultSet.next()) {
            optionalProduct = getOptionalProduct(resultSet, buyers);
            optionalProduct.ifPresent(productList::add);
        }
        return productList;
    }

    @Override
    public List<User> mapItemEntities(ResultSet resultSet) throws SQLException {
        List<User> buyers = new ArrayList<>();
        User user;
        while (resultSet.next()) {
            user = new User.UserBuilder(resultSet.getLong(UserArguments.USER_ID))
                    .setLogin(resultSet.getString(UserArguments.LOGIN))
                    .setPassword(resultSet.getString(UserArguments.PASSWORD))
                    .setName(resultSet.getString(UserArguments.NAME))
                    .setSurname(resultSet.getString(UserArguments.SURNAME))
                    .setUsersRole(resultSet.getInt(UserArguments.USERS_ROLE))
                    .build();
            buyers.add(user);
        }
        return buyers;
    }


    private Optional<Product> getOptionalProduct(ResultSet resultSet, List<User> buyers) throws SQLException {
        Optional<Product> optionalUser;
        Product product = new Product.ProductBuilder(resultSet.getLong(ProductArguments.ID_PRODUCT))
                .setProductName(resultSet.getString(ProductArguments.PRODUCT_NAME))
                .setProductPrice(resultSet.getDouble(ProductArguments.PRODUCT_PRICE))
                .setAmount(resultSet.getInt(ProductArguments.AMOUNT_OF_PRODUCT))
                .setBuyers(buyers)
                .build();
        ProductDescription description = new ProductDescription.ProductDescriptionBuilder(resultSet.getLong(ProductDescriptionArguments.ID_DESCRIPTION))
                .setProductId(resultSet.getLong(ProductDescriptionArguments.PRODUCT_ID))
                .setCountryOfOrigin(resultSet.getString(ProductDescriptionArguments.COUNTRY_OF_ORIGIN))
                .setType(resultSet.getString(ProductDescriptionArguments.TYPE_OF_PRODUCT))
                .setBrand(resultSet.getString(ProductDescriptionArguments.BRAND_OF_PRODUCT))
                .setIssueDate(resultSet.getObject(ProductDescriptionArguments.ISSUE_DATE, LocalDate.class))
                .build();
        product.setDescription(description);
        optionalUser = Optional.ofNullable(product);
        return optionalUser;
    }

}
