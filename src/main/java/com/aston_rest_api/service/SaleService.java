package com.aston_rest_api.service;

import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.Sale;
import com.aston_rest_api.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface SaleService {
    boolean buyProduct(Sale sale) throws ServiceException;
    List<Sale> findAllSales() throws ServiceException;

    boolean deleteSale(Sale sale) throws ServiceException;
    boolean updateSale(Sale sale) throws ServiceException;
    List<Sale> findSalesByDate(LocalDate date) throws ServiceException;
    List<Sale> findSalesByProduct(Product product) throws ServiceException;



}
