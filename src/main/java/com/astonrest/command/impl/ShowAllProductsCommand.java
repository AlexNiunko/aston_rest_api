package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.dto.ProductDto;
import com.astonrest.controller.mapper.impl.ProductMapperImpl;
import com.astonrest.controller.mapper.ProductMapper;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class ShowAllProductsCommand implements Command {
    private ProductService productService;

    public ShowAllProductsCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        ProductMapper mapper = ProductMapperImpl.getMapper();
        try {
            List<Product> products = productService.selectAllProducts();
            List<ProductDto> productDtos = new ArrayList<>();
            for (Product product : products) {
                productDtos.add(mapper.map(product));
            }
            session.setAttribute(Attributes.PRODUCTS.toString().toLowerCase(), productDtos);
            router.setPage(Pages.SHOW_ALL_PRODUCTS.getValue());
        } catch (ServiceException e) {
            throw new CommandException("Failed to find all products " + e);
        }
        return router;
    }
}
