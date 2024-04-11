package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.controller.dto.ProductDescriptionDto;
import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.exception.CommandException;
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

class ShowAllProductsCommandTest {

    private static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:10-alpine")
            .withUsername("postgres")
            .withPassword("pass")
            .withInitScript("DB-migration.sql")
            .withDatabaseName("tool_box_test");
    private static HikariDataSource dataSource = new HikariDataSource();
    private static ShowAllProductsCommand command;

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
        command = new ShowAllProductsCommand(dataSource);
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
        Mockito.doNothing().when(session).setAttribute(Attributes.PRODUCTS,productDtoList);
        Assertions.assertEquals(Pages.SHOW_ALL_PRODUCTS,command.execute(request).getPage());
    }
}