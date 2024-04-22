package com.astonrest.dao.mapper.impl;

import com.astonrest.controller.arguments.ProductArguments;
import com.astonrest.controller.arguments.ProductDescriptionArguments;
import com.astonrest.controller.arguments.SaleArguments;
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

public class UserResultSetMapperImpl implements ResultSetMapper<User>, ListResultSetMapper<Product> {

    private static UserResultSetMapperImpl instance = new UserResultSetMapperImpl();

    private UserResultSetMapperImpl() {
    }

    public static ResultSetMapper<User> getInstance() {
        return instance;
    }

    @Override
    public List<Product> mapItemEntities(ResultSet resultSet) throws SQLException {
        List<Product> purchases = new ArrayList<>();
        Product product;
        ProductDescription productDescription;
        while (resultSet.next()) {
            product = new Product.ProductBuilder(resultSet.getInt(ProductArguments.ID_PRODUCT))
                    .setProductName(resultSet.getString(ProductArguments.PRODUCT_NAME))
                    .setProductPrice(resultSet.getDouble(ProductArguments.PRODUCT_PRICE))
                    .setAmount(resultSet.getInt(SaleArguments.AMOUNT_OF_SALE))
                    .build();
            productDescription = new ProductDescription.ProductDescriptionBuilder(resultSet.getInt(ProductDescriptionArguments.ID_DESCRIPTION))
                    .setProductId(resultSet.getInt(ProductDescriptionArguments.PRODUCT_ID))
                    .setCountryOfOrigin(resultSet.getString(ProductDescriptionArguments.COUNTRY_OF_ORIGIN))
                    .setType(resultSet.getString(ProductDescriptionArguments.TYPE_OF_PRODUCT))
                    .setBrand(resultSet.getString(ProductDescriptionArguments.BRAND_OF_PRODUCT))
                    .setIssueDate(resultSet.getObject(ProductDescriptionArguments.ISSUE_DATE, LocalDate.class))
                    .build();
            product.setDescription(productDescription);
            purchases.add(product);
        }
        return purchases;
    }

    @Override
    public Optional<User> mapItem(ResultSet resultSet) throws SQLException {
        Optional<User> optionalUser = Optional.empty();
        List<Product> purchases = new ArrayList<>();
        if (resultSet.next()) {
            optionalUser = getOptionalUser(resultSet, purchases);
        }
        return optionalUser;
    }

    @Override
    public List<User> mapListItems(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();
        Optional<User> optionalUser;
        List<Product> purchases = new ArrayList<>();
        while (resultSet.next()) {
            optionalUser = getOptionalUser(resultSet, purchases);
            optionalUser.ifPresent(userList::add);
        }
        return userList;
    }


    private static Optional<User> getOptionalUser(ResultSet resultSet, List<Product> purchases) throws SQLException {
        Optional<User> optionalUser;
        User user = new User.UserBuilder(resultSet.getInt(UserArguments.USER_ID))
                .setLogin(resultSet.getString(UserArguments.LOGIN))
                .setPassword(resultSet.getString(UserArguments.PASSWORD))
                .setName(resultSet.getString(UserArguments.NAME))
                .setSurname(resultSet.getString(UserArguments.SURNAME))
                .setUsersRole(resultSet.getInt(UserArguments.USERS_ROLE))
                .setPurchases(purchases)
                .build();
        optionalUser = Optional.ofNullable(user);
        return optionalUser;
    }


}
