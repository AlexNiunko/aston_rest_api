package com.aston_rest_api.service.impl;

import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.dao.daoimpl.UserDaoImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {
    private static ProductServiceImpl productService;
    private static ProductDaoImpl productDao;
    private  Product product=new Product
            .ProductBuilder(1)
            .setProductName("hammer")
            .setProductPrice(10.25)
            .setAmount(5)
            .build();
    private ProductDescription description=new ProductDescription
            .ProductDescriptionBuilder(1)
            .setProductId(1)
            .setCountryOfOrigin("China")
            .setType("hand tool")
            .setBrand("Expert")
            .setIssueDate(LocalDate.of(2024,2,9))
            .build();
    @BeforeAll
    static void init() {
        productDao = Mockito.mock(ProductDaoImpl.class);
        productService = new ProductServiceImpl(productDao);
    }

    @AfterAll
    static void finish() {
        productDao = null;
        productService = null;
    }

    @Test
    void shouldAddNewProduct() throws DaoException, ServiceException {
        product.setDescription(description);
        Mockito.doReturn(true).when(productDao).insert(product);
        Assertions.assertTrue(productService.addNewProduct(product));
    }
    @Test
    void addNewProductNull() throws DaoException, ServiceException {
        Mockito.doReturn(false).when(productDao).insert(null);
        Assertions.assertFalse(productService.addNewProduct(null));
    }
    @Test
    void throwAddNewProduct() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(productDao).insert(product);
        Assertions.assertThrows(ServiceException.class,()->productService.addNewProduct(product));
    }
    @Test
    void shouldDeleteProduct() throws ServiceException, DaoException {
        Mockito.doReturn(true).when(productDao).delete(product);
        Assertions.assertTrue(productService.deleteProduct(product));
    }
    @Test
    void DeleteProductNull() throws ServiceException, DaoException {
        Mockito.doReturn(false).when(productDao).delete(null);
        Assertions.assertFalse(productService.deleteProduct(null));
    }
    @Test
    void throwDeleteProduct() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(productDao).delete(product);
        Assertions.assertThrows(ServiceException.class,()->productService.deleteProduct(product));
    }

    @Test
    void shouldUpdateProduct() throws DaoException, ServiceException {
        Mockito.doReturn(true).when(productDao).update(product);
        Assertions.assertTrue(productService.updateProduct(product));
    }
    @Test
    void UpdateProductNull() throws DaoException, ServiceException {
        Mockito.doReturn(false).when(productDao).update(null);
        Assertions.assertFalse(productService.updateProduct(null));
    }
    @Test
    void ThrowUpdateProduct() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(productDao).update(product);
        Assertions.assertThrows(ServiceException.class,()->productService.updateProduct(product));
    }

    @Test
    void shouldSelectAllProducts() throws DaoException, ServiceException {
        List<Product> productList=new ArrayList<>();
        productList.add(product);
        Mockito.doReturn(productList).when(productDao).findAll();
        Assertions.assertEquals(productList,productService.selectAllProducts());
    }
    @Test
    void throwSelectAllProducts() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(productDao).findAll();
        Assertions.assertThrows(ServiceException.class,()->productService.selectAllProducts());
    }
    @Test
    void shouldFindProductById() throws ServiceException, DaoException {
        Optional<Product>optionalProduct=Optional.of(product);
        Mockito.doReturn(optionalProduct).when(productDao).findProductById(product);
        Assertions.assertTrue(productService.findProductBuId(product).isPresent());
    }
    @Test
    void findProductByIdNull() throws ServiceException, DaoException {
        Optional<Product>optionalProduct=Optional.empty();
        Mockito.doReturn(optionalProduct).when(productDao).findProductById(null);
        Assertions.assertFalse(productService.findProductBuId(null).isPresent());
    }
    @Test
    void throwProductById() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(productDao).findProductById(product);
        Assertions.assertThrows(ServiceException.class,()->productService.findProductBuId(product));
    }

    @Test
    void shouldFindProductBuyers() throws ServiceException, DaoException {
        HashMap<Long, User>map=new HashMap<>();
        User user = new User
                .UserBuilder(1)
                .setLogin("michai@gmail.com")
                .setPassword("123")
                .setName("Michail")
                .setSurname("Radzivil")
                .setUsersRole(1)
                .build();
        map.put(1L,user);
        product.setBuyers(map);
        Optional<Product>optionalProduct=Optional.of(product);
        Mockito.doReturn(optionalProduct).when(productDao).findProductBuyers(product);
        Assertions.assertEquals(map,productService.findProductBuyers(product));
    }
    @Test
    void shouldFindProductBuyersNull() throws ServiceException, DaoException {
        Optional<Product>optionalProduct=Optional.empty();
        HashMap<Long,User>map=new HashMap<>();
        Mockito.doReturn(optionalProduct).when(productDao).findProductBuyers(null);
        Assertions.assertEquals(map,productService.findProductBuyers(null));
    }
    @Test
    void findProductBuyersThrow() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(productDao).findProductBuyers(product);
        Assertions.assertThrows(ServiceException.class,()->productService.findProductBuyers(product));
    }
}