package com.astonrest.service;

import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.Sale;

import java.time.LocalDate;
import java.util.List;

public interface SaleService {
    boolean buyProduct(Sale sale) throws ServiceException;
    List<Sale> findAllSales() throws ServiceException;

    boolean deleteSale(Sale sale) throws ServiceException;
    boolean updateSale(Sale sale) throws ServiceException;
    List<Sale> findSalesByDate(LocalDate date) throws ServiceException;
    List<Sale> findSalesByProduct(Product product) throws ServiceException;



}
