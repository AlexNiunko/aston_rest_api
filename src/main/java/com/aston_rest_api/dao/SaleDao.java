package com.aston_rest_api.dao;

import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.Sale;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleDao {
    List<Sale>findSalesByDate(LocalDateTime dateTime);
    List<Sale>findSalesByProduct(Product product);

}
