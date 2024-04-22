package com.astonrest.dao;

import com.astonrest.exception.DaoException;
import com.astonrest.model.Product;
import com.astonrest.model.Sale;

import java.time.LocalDate;
import java.util.List;

public interface SaleDao {
    List<Sale>findSalesByDate(LocalDate date) throws DaoException;
    List<Sale>findSalesByProduct(Product product) throws DaoException;

}
