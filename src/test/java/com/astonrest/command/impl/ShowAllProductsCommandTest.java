package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Pages;
import com.astonrest.controller.dto.ProductDto;
import com.astonrest.exception.CommandException;
import com.astonrest.service.ProductService;
import com.astonrest.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ShowAllProductsCommandTest {

    private static ShowAllProductsCommand command;
    private static ProductService productService;

    @BeforeAll
    static void init() throws SQLException {
        productService=Mockito.mock(ProductServiceImpl.class);
        command = new ShowAllProductsCommand(productService);
    }

    private HttpServletRequest request;
    private HttpSession session;
    @BeforeEach
    void prepare() {
        request = Mockito.mock(HttpServletRequest.class);
        session=Mockito.mock(HttpSession.class);
    }
    @Test
    void shouldFindAllProducts() throws CommandException {
        List<ProductDto> productDtoList=new ArrayList<>();
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.PRODUCTS.toString().toLowerCase(),productDtoList);
        Assertions.assertEquals(Pages.SHOW_ALL_PRODUCTS.getValue(),command.execute(request).getPage());
    }
}