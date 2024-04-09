package com.aston_rest_api.dao.mapper.impl;

import com.aston_rest_api.controller.ProductArguments;
import com.aston_rest_api.controller.ProductDescriptionArguments;
import com.aston_rest_api.controller.SaleArguments;
import com.aston_rest_api.controller.UserArguments;
import com.aston_rest_api.dao.mapper.ResultSetMapper;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class UserResultSetMapperImpl implements ResultSetMapper<User,Product> {

    private static UserResultSetMapperImpl instance=new UserResultSetMapperImpl();

    private UserResultSetMapperImpl() {
    }

    public static ResultSetMapper getInstance() {
        return new UserResultSetMapperImpl();
    }

    @Override
    public Map<Long, Product> mapItemEntities(ResultSet resultSet) throws SQLException {
        Map<Long, Product> purchases = new HashMap<>();
        Product product;
        ProductDescription productDescription;
        while (resultSet.next()) {
            long idSale = resultSet.getInt(SaleArguments.ID_SALE);
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
            purchases.put(idSale, product);
        }
        return purchases;
    }

    @Override
    public Optional<User> mapItem(ResultSet resultSet) throws SQLException {
        Optional<User> optionalUser = Optional.empty();
        Map<Long, Product> purchases = new HashMap<>();
           if (resultSet.next()) {
            optionalUser = getOptionalUser(resultSet, purchases);
        }
        return optionalUser;
    }

    @Override
    public List<User> mapListItems(ResultSet resultSet) throws SQLException {
        List<User>userList=new ArrayList<>();
        Optional<User> optionalUser = Optional.empty();
        Map<Long, Product> purchases = new HashMap<>();
        while (resultSet.next()){
            optionalUser=getOptionalUser(resultSet,purchases);
            optionalUser.ifPresent(userList::add);
        }
            return userList;
    }


    private static Optional<User> getOptionalUser(ResultSet resultSet, Map<Long, Product> purchases) throws SQLException {
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
