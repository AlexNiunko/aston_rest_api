package com.aston_rest_api.service.impl;

import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.dao.daoimpl.SaleDaoImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;
import com.aston_rest_api.model.Sale;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SaleServiceImplTest {
    private static SaleServiceImpl saleService;
    private static ProductDaoImpl productDao;
    private static SaleDaoImpl saleDao;
    private Product product=new Product
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
    private Sale sale=new Sale
            .SaleBuilder(1)
            .setBuyerId(1)
            .setProductId(1)
            .setDateOfSale(LocalDate.of(2024,4,8))
            .setAmountSale(1)
            .build();
    @BeforeAll
    static void init() {
        productDao = Mockito.mock(ProductDaoImpl.class);
        saleDao=Mockito.mock(SaleDaoImpl.class);
        saleService = new SaleServiceImpl(productDao,saleDao);
    }

    @AfterAll
    static void finish() {
        productDao = null;
        saleDao=null;
        saleService = null;
    }

    @Test
    void shouldBuyProduct() throws DaoException, ServiceException {
        product.setDescription(description);
        Optional<Product>productOptional=Optional.of(product);
        Mockito.doReturn(productOptional).when(productDao).findProductById(sale.getProductOfSale());
        Mockito.doReturn(true).when(saleDao).insert(sale);
        Assertions.assertTrue(saleService.buyProduct(sale));
    }
    @Test
    void shouldBuyProductNull() throws DaoException, ServiceException {
        Mockito.doReturn(false).when(saleDao).insert(null);
        Assertions.assertFalse(saleService.buyProduct(null));
    }
    @Test
    void shouldBuyProductThrow() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(saleDao).insert(sale);
        Mockito.doThrow(DaoException.class).when(productDao).findProductById(sale.getProductOfSale());
        Assertions.assertThrows(ServiceException.class,()->saleService.buyProduct(sale));
    }
    @Test
    void shouldFindAllSales() throws DaoException, ServiceException {
        List<Sale>saleList=new ArrayList<>();
        saleList.add(sale);
        Mockito.doReturn(saleList).when(saleDao).findAll();
        Assertions.assertEquals(saleList,saleService.findAllSales());
    }
    @Test
    void shouldFindAllSalesThrow() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(saleDao).findAll();
        Assertions.assertThrows(ServiceException.class,()->saleService.findAllSales());
    }

    @Test
    void shouldDeleteSale() throws DaoException, ServiceException {
        Mockito.doReturn(true).when(saleDao).delete(sale);
        Assertions.assertTrue(saleService.deleteSale(sale));
    }
    @Test
    void shouldDeleteSaleNull() throws DaoException, ServiceException {
        Mockito.doReturn(false).when(saleDao).delete(null);
        Assertions.assertFalse(saleService.deleteSale(null));
    }
    @Test
    void shouldDeleteSaleThrow() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(saleDao).delete(sale);
        Assertions.assertThrows(ServiceException.class,()->saleService.deleteSale(sale));
    }

    @Test
    void shouldUpdateSale() throws DaoException, ServiceException {
        Mockito.doReturn(true).when(saleDao).update(sale);
        Assertions.assertTrue(saleService.updateSale(sale));
    }
    @Test
    void shouldUpdateSaleNull() throws DaoException, ServiceException {
        Mockito.doReturn(false).when(saleDao).update(null);
        Assertions.assertFalse(saleService.updateSale(null));
    }
    @Test
    void shouldUpdateSaleThrow() throws DaoException, ServiceException {
       Mockito.doThrow(DaoException.class).when(saleDao).update(sale);
       Assertions.assertThrows(ServiceException.class,()->saleService.updateSale(sale));
    }

    @Test
    void shouldFindSalesByDate() throws DaoException, ServiceException {
        List<Sale>sales=new ArrayList<>();
        sales.add(sale);
        Mockito.doReturn(sales).when(saleDao).findSalesByDate(sale.getDateOfSale());
        Assertions.assertEquals(sales,saleService.findSalesByDate(sale.getDateOfSale()));
    }
    @Test
    void shouldFindSalesByDateNull() throws DaoException, ServiceException {
        List<Sale>sales=new ArrayList<>();
        Mockito.doReturn(sales).when(saleDao).findSalesByDate(null);
        Assertions.assertEquals(sales,saleService.findSalesByDate(null));
    }
    @Test
    void shouldFindSalesByDateThrow() throws DaoException, ServiceException {
        Mockito.doThrow(DaoException.class).when(saleDao).findSalesByDate(sale.getDateOfSale());
        Assertions.assertThrows(ServiceException.class,()->saleService.findSalesByDate(sale.getDateOfSale()));
    }

    @Test
    void shouldFindSalesByProduct() throws DaoException, ServiceException {
        List<Sale>sales=new ArrayList<>();
        sales.add(sale);
        Mockito.doReturn(sales).when(saleDao).findSalesByProduct(product);
        Assertions.assertEquals(sales,saleService.findSalesByProduct(product));
    }
    @Test
    void shouldFindSalesByProductNull() throws DaoException, ServiceException {
        List<Sale>sales=new ArrayList<>();
        Mockito.doReturn(sales).when(saleDao).findSalesByProduct(null);
        Assertions.assertEquals(sales,saleService.findSalesByProduct(null));
    }
    @Test
    void shouldFindSalesByProductThrow() throws DaoException, ServiceException {
       Mockito.doThrow(DaoException.class).when(saleDao).findSalesByProduct(product);
       Assertions.assertThrows(ServiceException.class,()->saleService.findSalesByProduct(product));
    }
}