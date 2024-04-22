package com.astonrest.command.impl;

import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.arguments.ProductArguments;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class DeleteProductCommand implements Command {
    private ProductService productService;

    public DeleteProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        long idProduct = Long.parseLong(request.getParameter(ProductArguments.ID_PRODUCT));
        Product product = new Product.ProductBuilder(idProduct).build();
        try {
            Optional<Product> optionalProduct = productService.findProductBuId(product.getId());
            if (optionalProduct.isPresent() && productService.deleteProduct(optionalProduct.get())) {
                router.setPage(Pages.ADMIN_PAGE.getValue());
                router.setRedirect();
            } else {
                router.setPage(Pages.DELETE_PRODUCT.getValue());
            }
        } catch (ServiceException e) {
            throw new CommandException("Failed to find all products " + e);
        }
        return router;
    }
}
