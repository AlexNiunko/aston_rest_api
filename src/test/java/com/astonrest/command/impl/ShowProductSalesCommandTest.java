package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Pages;
import com.astonrest.controller.arguments.ProductArguments;
import com.astonrest.controller.dto.ProductDescriptionDto;
import com.astonrest.controller.dto.ProductDto;
import com.astonrest.controller.mapper.impl.ProductMapperImpl;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.Sale;
import com.astonrest.service.ProductService;
import com.astonrest.service.SaleService;
import com.astonrest.service.impl.ProductServiceImpl;
import com.astonrest.service.impl.SaleServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ShowProductSalesCommandTest {
    private static ShowProductSalesCommand command;
    private static SaleService saleService;
    private static ProductService productService;

    @BeforeAll
    static void init() throws SQLException {
        saleService=Mockito.mock(SaleServiceImpl.class);
        productService= Mockito.mock(ProductServiceImpl.class);
        command = new ShowProductSalesCommand(saleService,productService);
    }
    private ProductDto productDto = new ProductDto.ProductDtoBuilder(110)
            .setProductName("other")
            .setProductPrice("100.25")
            .setProductAmount("150")
            .build();
    private ProductDescriptionDto description=new ProductDescriptionDto
            .ProductDescriptionBuilder(10)
            .setIdProduct(115)
            .setCountryOfOrigin("Russia")
            .setType("other")
            .setBrand("other")
            .setIssueDate(LocalDate.of(2022,2,9).toString())
            .build();

    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    void prepare() {
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
    }

    @Test
    void shouldFindProductSales() throws CommandException, ServiceException {
        List<Sale>saleList=new ArrayList<>();
        productDto.setDescription(description);
        Product product= ProductMapperImpl.getMapper().map(productDto);
        Optional<Product>optionalProduct=Optional.of(product);
        Mockito.doReturn(String.valueOf(productDto.getProductDtoId())).when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(optionalProduct).when(productService).findProductBuId(product.getId());
        Mockito.doReturn(saleList).when(saleService).findSalesByProduct(product);
        Mockito.doNothing().when(session).setAttribute(Attributes.PRODUCT_SALES.toString().toLowerCase(),saleList);
        Assertions.assertEquals(Pages.SHOW_PRODUCT_SALES.getValue(),command.execute(request).getPage());
    }
    @Test
    void shouldNotFindProductSales() throws CommandException {
        List<Sale>saleList=new ArrayList<>();
        Mockito.doReturn("1987").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.PRODUCT_SALES.toString().toLowerCase(),saleList);
        Assertions.assertEquals(Pages.ADMIN_PAGE.getValue(),command.execute(request).getPage());
    }



}