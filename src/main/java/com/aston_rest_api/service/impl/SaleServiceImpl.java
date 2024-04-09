package com.aston_rest_api.service.impl;

import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.dao.daoimpl.SaleDaoImpl;
import com.aston_rest_api.exception.DaoException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.Sale;
import com.aston_rest_api.service.SaleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleServiceImpl implements SaleService {
    private ProductDaoImpl productDao;
    private SaleDaoImpl saleDao;

    public SaleServiceImpl(ProductDaoImpl productDao, SaleDaoImpl saleDao) {
        this.productDao = productDao;
        this.saleDao = saleDao;
    }

    @Override
    public boolean buyProduct(Sale sale) throws ServiceException {
        boolean result=false;
        if (sale==null){
            return result;
        }
        try {
            Optional<Product>optionalProduct=productDao.findProductById(sale.getProductOfSale());
            if (optionalProduct.isPresent() && (optionalProduct.get().getAmount()-sale.getAmountSale())>0){
                result=saleDao.insert(sale);
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to buy product "+e);
        }
        return result;
    }

    @Override
    public List<Sale> findAllSales() throws ServiceException {
        List<Sale>saleList=new ArrayList<>();
        try{
            saleList=saleDao.findAll();
        }catch (DaoException e){
            throw new ServiceException("Failed to find all sales "+e);
        }
        return saleList;
    }

    @Override
    public boolean deleteSale(Sale sale) throws ServiceException {
        boolean result=false;
        if (sale==null){
            return result;
        }
        try{
            result=saleDao.delete(sale);
        }catch (DaoException e){
            throw new ServiceException("Failed to delete sale "+e);
        }
        return result;
    }

    @Override
    public boolean updateSale(Sale sale) throws ServiceException {
        boolean result=false;
        if (sale==null){
            return result;
        }
        try{
            result=saleDao.update(sale);
        }catch (DaoException e){
            throw new ServiceException("Failed to delete sale "+e);
        }
        return result;
    }

    @Override
    public List<Sale> findSalesByDate(LocalDate date) throws ServiceException {
        List<Sale>saleList=new ArrayList<>();
        if (date==null){
            return saleList;
        }
        try{
            saleList=saleDao.findSalesByDate(date);
        }catch (DaoException e){
            throw new ServiceException("Failed to find sales by date "+e);
        }
        return saleList;
    }

    @Override
    public List<Sale> findSalesByProduct(Product product) throws ServiceException {
        List<Sale>saleList=new ArrayList<>();
        if (product==null){
            return saleList;
        }
        try{
            saleList=saleDao.findSalesByProduct(product);
        }catch (DaoException e){
            throw new ServiceException("Failed to find sales by date "+e);
        }
        return saleList;
    }
}
