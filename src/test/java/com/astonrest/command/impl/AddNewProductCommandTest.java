package com.astonrest.command.impl;

import com.astonrest.command.Pages;
import com.astonrest.controller.arguments.ProductArguments;
import com.astonrest.controller.arguments.ProductDescriptionArguments;
import com.astonrest.controller.dto.ProductDescriptionDto;
import com.astonrest.controller.dto.ProductDto;
import com.astonrest.controller.mapper.impl.ProductMapperImpl;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.service.ProductService;
import com.astonrest.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddNewProductCommandTest {


    private static AddNewProductCommand command;
    private static ProductService productService;

    @BeforeAll
    static void init() throws SQLException {
        productService=Mockito.mock(ProductServiceImpl.class);
        command = new AddNewProductCommand(productService);
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
    @BeforeEach
    void prepare() {
        request = Mockito.mock(HttpServletRequest.class);
    }
    @Test
    @Order(1)
    void successfulAddNewProduct() throws CommandException, ServiceException {
        productDto.setDescription(description);
        Product product=ProductMapperImpl.getMapper().map(productDto);
        Mockito.doReturn(productDto.getProductName()).when(request).getParameter(ProductArguments.PRODUCT_NAME);
        Mockito.doReturn(productDto.getProductPrice()).when(request).getParameter(ProductArguments.PRODUCT_PRICE);
        Mockito.doReturn(productDto.getAmount()).when(request).getParameter(ProductArguments.AMOUNT_OF_PRODUCT);
        Mockito.doReturn(description.getCountryOfOrigin()).when(request).getParameter(ProductDescriptionArguments.COUNTRY_OF_ORIGIN);
        Mockito.doReturn(description.getType()).when(request).getParameter(ProductDescriptionArguments.TYPE_OF_PRODUCT);
        Mockito.doReturn(description.getBrand()).when(request).getParameter(ProductDescriptionArguments.BRAND_OF_PRODUCT);
        Mockito.doReturn(description.getIssueDate()).when(request).getParameter(ProductDescriptionArguments.ISSUE_DATE);
        Mockito.doReturn(true).when(productService).addNewProduct(product);
        Assertions.assertEquals(Pages.ADMIN_PAGE.getValue(),command.execute(request).getPage());
    }
    @Test
    @Order(2)
    void unsuccessfulAddNewProduct() throws CommandException {
        Mockito.doReturn(productDto.getProductName()).when(request).getParameter(ProductArguments.PRODUCT_NAME);
        Mockito.doReturn(productDto.getProductPrice()).when(request).getParameter(ProductArguments.PRODUCT_PRICE);
        Mockito.doReturn("qwer").when(request).getParameter(ProductArguments.AMOUNT_OF_PRODUCT);
        Mockito.doReturn(description.getCountryOfOrigin()).when(request).getParameter(ProductDescriptionArguments.COUNTRY_OF_ORIGIN);
        Mockito.doReturn(description.getType()).when(request).getParameter(ProductDescriptionArguments.TYPE_OF_PRODUCT);
        Mockito.doReturn(description.getBrand()).when(request).getParameter(ProductDescriptionArguments.BRAND_OF_PRODUCT);
        Mockito.doReturn(description.getIssueDate()).when(request).getParameter(ProductDescriptionArguments.ISSUE_DATE);
        Assertions.assertEquals(Pages.ADD_NEW_PRODUCT.getValue(),command.execute(request).getPage());
    }
    @Test
    @Order(3)
    void throwAddNewProduct() throws CommandException, ServiceException {
        Mockito.doReturn(productDto.getProductName()).when(request).getParameter(ProductArguments.PRODUCT_NAME);
        Mockito.doReturn(productDto.getProductPrice()).when(request).getParameter(ProductArguments.PRODUCT_PRICE);
        Mockito.doReturn(productDto.getAmount()).when(request).getParameter(ProductArguments.AMOUNT_OF_PRODUCT);
        Mockito.doReturn(description.getCountryOfOrigin()).when(request).getParameter(ProductDescriptionArguments.COUNTRY_OF_ORIGIN);
        Mockito.doReturn(description.getType()).when(request).getParameter(ProductDescriptionArguments.TYPE_OF_PRODUCT);
        Mockito.doReturn(description.getBrand()).when(request).getParameter(ProductDescriptionArguments.BRAND_OF_PRODUCT);
        Mockito.doReturn(description.getIssueDate()).when(request).getParameter(ProductDescriptionArguments.ISSUE_DATE);
        productDto.setDescription(description);
        Product product=ProductMapperImpl.getMapper().map(productDto);
        Mockito.doThrow(ServiceException.class).when(productService).addNewProduct(product);
        Assertions.assertThrows(CommandException.class,()->command.execute(request));
    }

}