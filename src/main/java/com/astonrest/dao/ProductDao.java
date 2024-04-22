package com.astonrest.dao;

import com.astonrest.exception.DaoException;
import com.astonrest.model.Product;

import java.util.Optional;

public interface ProductDao {
 Optional<Product>findProductById(long id) throws DaoException;
 Optional<Product> findProductBuyers(Product product) throws DaoException;
 Optional<Product>findAllProductOrders(Product product) throws DaoException;
}
