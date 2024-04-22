package com.astonrest.service.impl;

import com.astonrest.dao.BaseDao;
import com.astonrest.dao.ProductDao;
import com.astonrest.exception.DaoException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.User;
import com.astonrest.service.ProductService;

import java.util.*;

public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;
    private BaseDao<Product> baseDao;
    private static ProductService productService;

    public static ProductService getProductService(ProductDao productDao) {
        if (productService == null) {
            productService = new ProductServiceImpl(productDao);
        }
        return productService;
    }

    private ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
        this.baseDao=(BaseDao) this.productDao;
    }

    @Override
    public boolean addNewProduct(Product product) throws ServiceException {
        boolean result = false;
        if (product == null) {
            return result;
        }
        try {
            result = baseDao.insert(product);
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
            result = baseDao.delete(product);
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
            result = baseDao.delete(product);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete new product " + e);
        }
        return result;
    }

    @Override
    public List<Product> selectAllProducts() throws ServiceException {
        List<Product> productList = new ArrayList<>();
        try {
            productList = baseDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete new product " + e);
        }
        return productList;
    }

    @Override
    public Optional<Product> findProductBuId(long idProduct) throws ServiceException {
        Optional<Product> optionalProduct = Optional.empty();
        try {
            optionalProduct = productDao.findProductById(idProduct);
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
        try {
            Optional<Product> optionalProduct = productDao.findProductBuyers(product);
            if (optionalProduct.isPresent()) {
                list = optionalProduct.get().getBuyers();
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to find buyers " + e);
        }
        return list;
    }
}
