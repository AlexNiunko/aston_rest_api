package com.astonrest.service.impl;

import com.astonrest.dao.BaseDao;
import com.astonrest.dao.ProductDao;
import com.astonrest.dao.impl.ProductDaoImpl;
import com.astonrest.exception.DaoException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.ProductDescription;
import com.astonrest.model.User;
import com.astonrest.service.ProductService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ProductServiceImplTest {
    private static ProductService productService;
    private static ProductDao productDao;
    private static BaseDao baseDao;
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
        productService = ProductServiceImpl.getProductService(productDao);
        baseDao=(BaseDao) productDao;
    }

    @AfterAll
    static void finish() {
        productDao = null;
        productService = null;
    }

    @Test
    void shouldAddNewProduct() throws DaoException, ServiceException, SQLException {
        product.setDescription(description);
        Mockito.doReturn(true).when(baseDao).insert(product);
        Assertions.assertTrue(productService.addNewProduct(product));
    }
    @Test
    void addNewProductNull() throws DaoException, ServiceException {
        Mockito.doReturn(false).when(baseDao).insert(null);
        Assertions.assertFalse(productService.addNewProduct(null));
    }
    @Test
    void throwAddNewProduct() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(baseDao).insert(product);
        Assertions.assertThrows(ServiceException.class,()->productService.addNewProduct(product));
    }
    @Test
    void shouldDeleteProduct() throws ServiceException, DaoException {
        Mockito.doReturn(true).when(baseDao).delete(product);
        Assertions.assertTrue(productService.deleteProduct(product));
    }
    @Test
    void DeleteProductNull() throws ServiceException, DaoException {
        Mockito.doReturn(false).when(baseDao).delete(null);
        Assertions.assertFalse(productService.deleteProduct(null));
    }
    @Test
    void throwDeleteProduct() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(baseDao).delete(product);
        Assertions.assertThrows(ServiceException.class,()->productService.deleteProduct(product));
    }

    @Test
    void shouldUpdateProduct() throws DaoException, ServiceException {
        Mockito.doReturn(true).when(baseDao).update(product);
        Assertions.assertTrue(productService.updateProduct(product));
    }
    @Test
    void UpdateProductNull() throws DaoException, ServiceException {
        Mockito.doReturn(false).when(baseDao).update(null);
        Assertions.assertFalse(productService.updateProduct(null));
    }
    @Test
    void ThrowUpdateProduct() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(baseDao).update(product);
        Assertions.assertThrows(ServiceException.class,()->productService.updateProduct(product));
    }

    @Test
    void shouldSelectAllProducts() throws DaoException, ServiceException {
        List<Product> productList=new ArrayList<>();
        productList.add(product);
        Mockito.doReturn(productList).when(baseDao).findAll();
        Assertions.assertEquals(productList,productService.selectAllProducts());
    }
    @Test
    void throwSelectAllProducts() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(baseDao).findAll();
        Assertions.assertThrows(ServiceException.class,()->productService.selectAllProducts());
    }
    @Test
    void shouldFindProductById() throws ServiceException, DaoException {
        Optional<Product>optionalProduct=Optional.of(product);
        Mockito.doReturn(optionalProduct).when(productDao).findProductById(product.getId());
        Assertions.assertTrue(productService.findProductBuId(product.getId()).isPresent());
    }
    @Test
    void findProductByIdNull() throws ServiceException, DaoException {
        Optional<Product>optionalProduct=Optional.empty();
        Mockito.doReturn(optionalProduct).when(productDao).findProductById(0);
        Assertions.assertFalse(productService.findProductBuId(0).isPresent());
    }
    @Test
    void throwProductById() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(productDao).findProductById(product.getId());
        Assertions.assertThrows(ServiceException.class,()->productService.findProductBuId(product.getId()));
    }

    @Test
    void shouldFindProductBuyers() throws ServiceException, DaoException {
        List<User>buyers=new ArrayList<>();
        User user = new User
                .UserBuilder(1)
                .setLogin("michai@gmail.com")
                .setPassword("123")
                .setName("Michail")
                .setSurname("Radzivil")
                .setUsersRole(1)
                .build();
        buyers.add(user);
        product.setBuyers(buyers);
        Optional<Product>optionalProduct=Optional.of(product);
        Mockito.doReturn(optionalProduct).when(productDao).findProductBuyers(product);
        Assertions.assertEquals(buyers,productService.findProductBuyers(product));
    }
    @Test
    void shouldFindProductBuyersNull() throws ServiceException, DaoException {
        Optional<Product>optionalProduct=Optional.empty();
        List<User>list=new ArrayList<>();
        Mockito.doReturn(optionalProduct).when(productDao).findProductBuyers(null);
        Assertions.assertEquals(list,productService.findProductBuyers(null));
    }
    @Test
    void findProductBuyersThrow() throws ServiceException, DaoException {
        Mockito.doThrow(DaoException.class).when(productDao).findProductBuyers(product);
        Assertions.assertThrows(ServiceException.class,()->productService.findProductBuyers(product));
    }
}