package com.aston_rest_api.dao;

import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductDao {
 Optional<Product>findProductById(long id) throws DaoException;
 Optional<Product> findProductBuyers(Product product) throws DaoException;
}
