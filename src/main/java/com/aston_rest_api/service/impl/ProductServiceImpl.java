package com.aston_rest_api.service.impl;

import com.aston_rest_api.dao.BaseDao;
import com.aston_rest_api.dao.ProductDao;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.User;
import com.aston_rest_api.service.ProductService;

import java.util.*;

public class ProductServiceImpl implements ProductService {

    private BaseDao productDao;

    public ProductServiceImpl(BaseDao productDao) {
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
    public Optional<Product> findProductBuId(long idProduct) throws ServiceException {
        Optional<Product> optionalProduct = Optional.empty();
        ProductDao dao = (ProductDao) productDao;
        try {
            optionalProduct = dao.findProductById(idProduct);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find product with Id" + e);
        }
        return optionalProduct;
    }

    @Override
    public List<User> findProductBuyers(Product product) throws ServiceException {
        List<User> list = new ArrayList<>();
        if (product == null) {
            return list;
        }
        ProductDao dao = (ProductDao) productDao;
        try {
            Optional<Product> optionalProduct = dao.findProductBuyers(product);
            if (optionalProduct.isPresent()) {
                list = optionalProduct.get().getBuyers();
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to find buyers " + e);
        }
        return list;
    }
}
