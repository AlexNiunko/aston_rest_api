package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.dto.ProductDescriptionDto;
import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.model.Sale;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShowProductSalesCommandTest {

    private static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:10-alpine")
            .withUsername("postgres")
            .withPassword("pass")
            .withInitScript("DB-migration.sql")
            .withDatabaseName("tool_box_test");
    private static HikariDataSource dataSource = new HikariDataSource();
    private static ShowProductSalesCommand command;

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
        command = new ShowProductSalesCommand(dataSource);
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
    void shouldFindProductSales() throws CommandException {
        List<Sale>saleList=new ArrayList<>();
        Mockito.doReturn("1").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.PRODUCT_SALES,saleList);
        Assertions.assertEquals(Pages.SHOW_PRODUCT_SALES,command.execute(request).getPage());
    }
    @Test
    void shouldNotFindProductSales() throws CommandException {
        List<Sale>saleList=new ArrayList<>();
        Mockito.doReturn("1987").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.PRODUCT_SALES,saleList);
        Assertions.assertEquals(Pages.ADMIN_PAGE,command.execute(request).getPage());
    }



}