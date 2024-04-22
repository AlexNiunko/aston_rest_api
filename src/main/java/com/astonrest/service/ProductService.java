package com.astonrest.service;

import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.User;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    boolean addNewProduct(Product product) throws ServiceException;
    boolean deleteProduct(Product product) throws ServiceException;
    boolean updateProduct(Product product) throws ServiceException;
    List<Product> selectAllProducts() throws ServiceException;
    Optional<Product>findProductBuId(long idProduct) throws ServiceException;
    List<User>findProductBuyers(Product product) throws ServiceException;





}
