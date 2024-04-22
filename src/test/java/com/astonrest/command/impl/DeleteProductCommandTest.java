package com.astonrest.command.impl;

import com.astonrest.command.Pages;
import com.astonrest.controller.arguments.ProductArguments;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.service.ProductService;
import com.astonrest.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

class DeleteProductCommandTest {
    private static DeleteProductCommand command;
    private static ProductService productService;

    @BeforeAll
    static void init() throws SQLException {
        productService=Mockito.mock(ProductServiceImpl.class);
        command = new DeleteProductCommand(productService);
    }
    private HttpServletRequest request;
    @BeforeEach
    void prepare() {
        request = Mockito.mock(HttpServletRequest.class);
    }
    @Test
    void shouldDeleteProduct() throws CommandException, ServiceException {
        Product product=new Product.ProductBuilder(13).build();
        Optional<Product>optionalProduct=Optional.of(product);
        Mockito.doReturn("13").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn(optionalProduct).when(productService).findProductBuId(13L);
        Mockito.doReturn(true).when(productService).deleteProduct(product);
        Assertions.assertEquals(Pages.ADMIN_PAGE.getValue(),command.execute(request).getPage());
    }
    @Test
    void shouldNotDeleteProduct() throws CommandException {
        Mockito.doReturn("108").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Assertions.assertEquals(Pages.DELETE_PRODUCT.getValue(),command.execute(request).getPage());
    }
}