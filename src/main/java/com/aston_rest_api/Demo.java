package com.aston_rest_api;

import com.aston_rest_api.controller.ProductArguments;
import com.aston_rest_api.controller.ProductDescriptionArguments;
import com.aston_rest_api.db.Configuration;
import com.aston_rest_api.db.ConnectionManager;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Demo {
    private static final String PROPERTIES_PATH="datasource.properties";
    public static void main(String[] args) throws SQLException {
       HashMap<Integer,Integer>map1=new HashMap<>();
       HashMap<Integer,Integer>map2=new HashMap<>();
       map1.put(1,1);
       map1.put(2,2);
       map2.put(1,1);
       map2.put(2,2);
        System.out.println(map1.equals(map2));

//        ProductDescription description;
//        Product product;
//        String findAllProducts=
//                """
//                SELECT * FROM  products p LEFT OUTER JOIN product_descriptions pd ON p.id_product=pd.product_id
//
//                """;
//        String insert="INSERT INTO tool_box.users (user_id,login,password,name,surname,users_role)VALUES(?,?,?,?,?,?) ";
//        List<Product> productList=new ArrayList<>();
//        User user=new User.UserBuilder(123).setLogin("alex@tut").setPassword("12345").setName("Alexandr").setSurname("Niunko")
//                .setUsersRole(2).build();
//
//        ConnectionManager manager=ConnectionManagerImpl.getInstance(Configuration.dataSource);
//        try(Connection connection= manager.getConnection();
//        PreparedStatement statement= connection.prepareStatement(insert)){
//            statement.setLong(1,user.getId());
//            statement.setString(2,user.getLogin());
//            statement.setString(3,user.getPassword());
//            statement.setString(4,user.getName());
//            statement.setString(5, user.getSurname());
//            statement.setInt(6,user.getUsersRole());
//            int result= statement.executeUpdate();
//            System.out.println(result);
//        }catch (SQLException e){
//            System.out.println("Something wrong"+e);
//
//        }
//     try(Connection connection=manager.getConnection();
//     PreparedStatement statement= connection.prepareStatement(findAllProducts);
//     ResultSet resultSet= statement.executeQuery()){
//         while (resultSet.next()){
//             product=new Product.ProductBuilder(resultSet.getLong(ProductArguments.ID_PRODUCT))
//                     .setProductName(resultSet.getString(ProductArguments.PRODUCT_NAME))
//                     .setProductPrice(resultSet.getDouble(ProductArguments.PRODUCT_PRICE))
//                     .setAmount(resultSet.getInt(ProductArguments.AMOUNT_OF_PRODUCT))
//                     .build();
//             description=new ProductDescription.ProductDescriptionBuilder(resultSet.getLong(ProductDescriptionArguments.ID_DESCRIPTION))
//                     .setProductId(resultSet.getLong(ProductDescriptionArguments.PRODUCT_ID))
//                     .setCountryOfOrigin(resultSet.getString(ProductDescriptionArguments.COUNTRY_OF_ORIGIN))
//                     .setType(resultSet.getString(ProductDescriptionArguments.TYPE_OF_PRODUCT))
//                     .setBrand(resultSet.getString(ProductDescriptionArguments.BRAND_OF_PRODUCT))
//                     .setIssueDate(resultSet.getObject(ProductDescriptionArguments.ISSUE_DATE, LocalDateTime.class))
//                     .build();
//             product.setDescription(description);
//             productList.add(product);
//         }
//     }catch (SQLException e){
//
//     }
//        System.out.println(productList.size());
//        System.out.println(productList);




    }
}
