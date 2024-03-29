package com.aston_rest_api;

import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.controller.dto.SaleDto;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;

import java.sql.*;
import java.time.LocalDateTime;

public class Demo {
    public static void main(String[] args) throws SQLException {
        ConnectionManagerImpl connectionManager = ConnectionManagerImpl.getInstance();
        Connection connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM  users u JOIN sales s on u.user_id=s.buyer_id join products p on s.product_id=p.id_product WHERE u.password=123";
        ResultSet rs = statement.executeQuery(sql);
        SaleDto saleDto = null;
        ResultSetMetaData data=rs.getMetaData();
//        int i= data.getColumnCount();
//        for (int j = 1; j <i ; j++) {
//            System.out.println(data.getColumnName(j));
//        }





    }
}
