package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.arguments.ProductArguments;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.Sale;
import com.astonrest.service.ProductService;
import com.astonrest.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

public class ShowProductSalesCommand implements Command {
    private SaleService saleService;
    private ProductService productService;

    public ShowProductSalesCommand(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        long idProduct = Long.parseLong(request.getParameter(ProductArguments.ID_PRODUCT));
        Product product = new Product.ProductBuilder(idProduct).build();
        try {
            Optional<Product> optionalProduct = productService.findProductBuId(product.getId());
            if (optionalProduct.isPresent()) {
                List<Sale> saleList = saleService.findSalesByProduct(optionalProduct.get());
                session.setAttribute(Attributes.PRODUCT_SALES.toString().toLowerCase(), saleList);
                router.setPage(Pages.SHOW_PRODUCT_SALES.getValue());
                router.setRedirect();
            } else {
                router.setPage(Pages.ADMIN_PAGE.getValue());
            }
        } catch (ServiceException e) {
            throw new CommandException("Failed to show sales " + e);
        }
        return router;
    }
}
