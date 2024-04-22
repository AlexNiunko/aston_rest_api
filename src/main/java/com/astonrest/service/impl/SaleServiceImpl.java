package com.astonrest.service.impl;

import com.astonrest.dao.BaseDao;
import com.astonrest.dao.ProductDao;
import com.astonrest.dao.SaleDao;
import com.astonrest.exception.DaoException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.Sale;
import com.astonrest.service.SaleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleServiceImpl implements SaleService {
    private BaseDao productBaseDao;
    private BaseDao saleBaseDao;
    private ProductDao productDao;
    private SaleDao saleDao;
    private static SaleService saleService;

    private SaleServiceImpl(ProductDao productDao, SaleDao saleDao) {
        this.productDao=productDao;
        this.saleDao= saleDao;
        this.productBaseDao = (BaseDao) this.productDao;
        this.saleBaseDao = (BaseDao) this.saleDao;

    }
    public static SaleService getSaleService(ProductDao productDao, SaleDao saleDao){
        if (saleService==null){
            saleService=new SaleServiceImpl(productDao,saleDao);
        }
        return saleService;
    }

    @Override
    public boolean buyProduct(Sale sale) throws ServiceException {
        boolean result=false;
        if (sale==null){
            return result;
        }
        ProductDao dao=(ProductDao) productBaseDao;
        try {
            Optional<Product>optionalProduct=dao.findProductById(sale.getProductId());
            if (optionalProduct.isPresent() && (optionalProduct.get().getAmount()-sale.getAmountSale())>0){
                result= saleBaseDao.insert(sale);
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
            saleList= saleBaseDao.findAll();
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
            result= saleBaseDao.delete(sale);
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
            result= saleBaseDao.update(sale);
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
