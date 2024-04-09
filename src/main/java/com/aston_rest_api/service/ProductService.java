package com.aston_rest_api.service;

import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    boolean addNewProduct(Product product) throws ServiceException;
    boolean deleteProduct(Product product) throws ServiceException;
    boolean updateProduct(Product product) throws ServiceException;
    List<Product> selectAllProducts() throws ServiceException;
    Optional<Product>findProductBuId(Product product) throws ServiceException;
    Map<Long, User>findProductBuyers(Product product) throws ServiceException;





}
