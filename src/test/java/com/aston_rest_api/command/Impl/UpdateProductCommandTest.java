package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Pages;
import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.arguments.ProductDescriptionArguments;
import com.aston_rest_api.controller.dto.ProductDescriptionDto;
import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.exception.CommandException;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UpdateProductCommandTest {

    private static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:10-alpine")
            .withUsername("postgres")
            .withPassword("pass")
            .withInitScript("DB-migration.sql")
            .withDatabaseName("tool_box_test");
    private static HikariDataSource dataSource = new HikariDataSource();
    private static UpdateProductCommand command;

    @BeforeAll
    static void init() throws SQLException {
        container.start();
        dataSource.setDriverClassName(container.getDriverClassName());
        dataSource.setJdbcUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(1000);
        dataSource.setAutoCommit(true);
        dataSource.setLoginTimeout(10);
        command = new UpdateProductCommand(dataSource);
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
    void successfulUpdateProduct() throws CommandException {
        Mockito.doReturn("1").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn(productDto.getProductName()).when(request).getParameter(ProductArguments.PRODUCT_NAME);
        Mockito.doReturn(productDto.getProductPrice()).when(request).getParameter(ProductArguments.PRODUCT_PRICE);
        Mockito.doReturn(productDto.getAmount()).when(request).getParameter(ProductArguments.AMOUNT_OF_PRODUCT);
        Mockito.doReturn(description.getCountryOfOrigin()).when(request).getParameter(ProductDescriptionArguments.COUNTRY_OF_ORIGIN);
        Mockito.doReturn(description.getType()).when(request).getParameter(ProductDescriptionArguments.TYPE_OF_PRODUCT);
        Mockito.doReturn(description.getBrand()).when(request).getParameter(ProductDescriptionArguments.BRAND_OF_PRODUCT);
        Mockito.doReturn(description.getIssueDate()).when(request).getParameter(ProductDescriptionArguments.ISSUE_DATE);
        Assertions.assertEquals(Pages.ADMIN_PAGE,command.execute(request).getPage());
    }
    @Test
    @Order(2)
    void unsuccessUpdateProduct() throws CommandException {
        Mockito.doReturn("1").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn(productDto.getProductName()).when(request).getParameter(ProductArguments.PRODUCT_NAME);
        Mockito.doReturn(productDto.getProductPrice()).when(request).getParameter(ProductArguments.PRODUCT_PRICE);
        Mockito.doReturn("qwer").when(request).getParameter(ProductArguments.AMOUNT_OF_PRODUCT);
        Mockito.doReturn(description.getCountryOfOrigin()).when(request).getParameter(ProductDescriptionArguments.COUNTRY_OF_ORIGIN);
        Mockito.doReturn(description.getType()).when(request).getParameter(ProductDescriptionArguments.TYPE_OF_PRODUCT);
        Mockito.doReturn(description.getBrand()).when(request).getParameter(ProductDescriptionArguments.BRAND_OF_PRODUCT);
        Mockito.doReturn(description.getIssueDate()).when(request).getParameter(ProductDescriptionArguments.ISSUE_DATE);
        Assertions.assertEquals(Pages.UPDATE_PRODUCT_PAGE,command.execute(request).getPage());
    }


}