package com.aston_rest_api;

import com.aston_rest_api.controller.ProductArguments;
import com.aston_rest_api.controller.ProductDescriptionArguments;
import com.aston_rest_api.db.ConnectionManager;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.Product;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws SQLException {
        ProductDescription description;
        Product product;
        String findAllProducts=
                """
                SELECT * FROM  products p LEFT OUTER JOIN product_descriptions pd ON p.id_product=pd.product_id
                 
                """;
        List<Product> productList=new ArrayList<>();

        ConnectionManager manager=ConnectionManagerImpl.getInstance();

     try(Connection connection=manager.getConnection();
     PreparedStatement statement= connection.prepareStatement(findAllProducts);
     ResultSet resultSet= statement.executeQuery()){
         while (resultSet.next()){
             product=new Product.ProductBuilder(resultSet.getLong(ProductArguments.ID_PRODUCT))
                     .setProductName(resultSet.getString(ProductArguments.PRODUCT_NAME))
                     .setProductPrice(resultSet.getDouble(ProductArguments.PRODUCT_PRICE))
                     .setAmount(resultSet.getInt(ProductArguments.AMOUNT_OF_PRODUCT))
                     .build();
             description=new ProductDescription.ProductDescriptionBuilder(resultSet.getLong(ProductDescriptionArguments.ID_DESCRIPTION))
                     .setProductId(resultSet.getLong(ProductDescriptionArguments.PRODUCT_ID))
                     .setCountryOfOrigin(resultSet.getString(ProductDescriptionArguments.COUNTRY_OF_ORIGIN))
                     .setType(resultSet.getString(ProductDescriptionArguments.TYPE_OF_PRODUCT))
                     .setBrand(resultSet.getString(ProductDescriptionArguments.BRAND_OF_PRODUCT))
                     .setIssueDate(resultSet.getObject(ProductDescriptionArguments.ISSUE_DATE, LocalDateTime.class))
                     .build();
             product.setDescription(description);
             productList.add(product);
         }
     }catch (SQLException e){

     }
        System.out.println(productList.size());
        System.out.println(productList);




    }
}
