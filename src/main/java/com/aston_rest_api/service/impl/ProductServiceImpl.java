package com.aston_rest_api.service.impl;

import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;
import com.aston_rest_api.service.ProductService;

import java.util.*;

public class ProductServiceImpl implements ProductService {

    private ProductDaoImpl productDao;

    public ProductServiceImpl(ProductDaoImpl productDao) {
        this.productDao = productDao;
    }

    @Override
    public boolean addNewProduct(Product product) throws ServiceException {
        boolean result = false;
        if (product == null) {
            return result;
        }
        try {
            result = productDao.insert(product);
        } catch (DaoException e) {
            throw new ServiceException("Failed to add new prodduct " + e);
        }
        return result;
    }

    @Override
    public boolean deleteProduct(Product product) throws ServiceException {
        boolean result = false;
        if (product == null) {
            return result;
        }
        try {
            result = productDao.delete(product);
        } catch (DaoException e) {
            throw new ServiceException("Failed to add new prodduct " + e);
        }
        return result;
    }

    @Override
    public boolean updateProduct(Product product) throws ServiceException {
        boolean result = false;
        if (product == null) {
            return result;
        }
        try {
            result = productDao.delete(product);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete new product " + e);
        }
        return result;
    }

    @Override
    public List<Product> selectAllProducts() throws ServiceException {
        List<Product> productList = new ArrayList<>();
        try {
            productList = productDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete new product " + e);
        }
        return productList;
    }

    @Override
    public Optional<Product> findProductBuId(Product product) throws ServiceException {
        Optional<Product> optionalProduct = Optional.empty();
        if (product == null) {
            return optionalProduct;
        }
        try {
            optionalProduct = productDao.findProductById(product);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find product with Id" + e);
        }
        return optionalProduct;
    }

    @Override
    public Map<Long, User> findProductBuyers(Product product) throws ServiceException {
        Map<Long, User> map = new HashMap<>();
        if (product == null) {
            return map;
        }
        try {
            Optional<Product> optionalProduct = productDao.findProductBuyers(product);
            if (optionalProduct.isPresent()) {
                map = optionalProduct.get().getBuyers();
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to find buyers " + e);
        }
        return map;
    }
}
