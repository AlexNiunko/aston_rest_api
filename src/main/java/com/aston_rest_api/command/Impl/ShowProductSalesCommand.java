package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Command;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.arguments.ProductDescriptionArguments;
import com.aston_rest_api.controller.mapper.Impl.ProductMapperImpl;
import com.aston_rest_api.controller.mapper.Impl.SaleMapperImpl;
import com.aston_rest_api.controller.mapper.ProductMapper;
import com.aston_rest_api.controller.mapper.SaleMapper;
import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.dao.daoimpl.SaleDaoImpl;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.Sale;
import com.aston_rest_api.service.ProductService;
import com.aston_rest_api.service.SaleService;
import com.aston_rest_api.service.impl.ProductServiceImpl;
import com.aston_rest_api.service.impl.SaleServiceImpl;
import com.aston_rest_api.validator.ParameterValidator;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ShowProductSalesCommand implements Command {
    private HikariDataSource config;



    public ShowProductSalesCommand(HikariDataSource config) {
        this.config = config;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session= request.getSession();
        ProductDaoImpl productDao = new ProductDaoImpl(ConnectionManagerImpl.getInstance(config));
        SaleDaoImpl saleDao=new SaleDaoImpl(ConnectionManagerImpl.getInstance(config));
        ProductService productService = new ProductServiceImpl(productDao);
        SaleService saleService=new SaleServiceImpl(productDao, saleDao);
        ProductMapper productMapper = ProductMapperImpl.getMapper();
        SaleMapper saleMapper= SaleMapperImpl.getMapper();
        ParameterValidator validator = ParameterValidator.getInstance();
        long idProduct= Long.parseLong(request.getParameter(ProductArguments.ID_PRODUCT));
        Product product=new Product.ProductBuilder(idProduct).build();
        try{
            Optional<Product>optionalProduct=productService.findProductBuId(product.getId());
            if (optionalProduct.isPresent()){
                List<Sale>saleList=saleService.findSalesByProduct(optionalProduct.get());
                session.setAttribute(Attributes.PRODUCT_SALES,saleList);
                router.setPage(Pages.SHOW_PRODUCT_SALES);
                router.setRedirect();
            }else {
                router.setPage(Pages.ADMIN_PAGE);
            }
        }catch (ServiceException e){
            throw new CommandException("Failed to show sales "+e);
        }
        return router;
    }
}
