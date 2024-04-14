package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Command;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.controller.mapper.Impl.ProductMapperImpl;
import com.aston_rest_api.controller.mapper.ProductMapper;
import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.service.ProductService;
import com.aston_rest_api.service.impl.ProductServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeleteProductCommand implements Command {
    private HikariDataSource config;

    public DeleteProductCommand(HikariDataSource config) {
        this.config = config;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        ProductDaoImpl productDao = new ProductDaoImpl(ConnectionManagerImpl.getInstance(config));
        ProductService productService = new ProductServiceImpl(productDao);
        long idProduct= Long.parseLong(request.getParameter(ProductArguments.ID_PRODUCT));
        Product product=new Product.ProductBuilder(idProduct).build();
        try{
            Optional<Product>optionalProduct=productService.findProductBuId(product.getId());
            if (optionalProduct.isPresent() && productService.deleteProduct(optionalProduct.get())){
                router.setPage(Pages.ADMIN_PAGE);
                router.setRedirect();
            }else {
                router.setPage(Pages.DELETE_PRODUCT);
            }
        }catch (ServiceException e){
            throw new CommandException("Failed to find all products "+e);
        }
        return router;
    }
}
