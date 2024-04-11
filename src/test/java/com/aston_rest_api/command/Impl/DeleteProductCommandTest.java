package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Pages;
import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.dto.ProductDescriptionDto;
import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.exception.CommandException;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DeleteProductCommandTest {

    private static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:10-alpine")
            .withUsername("postgres")
            .withPassword("pass")
            .withInitScript("DB-migration.sql")
            .withDatabaseName("tool_box_test");
    private static HikariDataSource dataSource = new HikariDataSource();
    private static DeleteProductCommand command;

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
        command = new DeleteProductCommand(dataSource);
    }


    private HttpServletRequest request;
    @BeforeEach
    void prepare() {
        request = Mockito.mock(HttpServletRequest.class);
    }
    @Test
    void shouldDeleteProduct() throws CommandException {
        Mockito.doReturn("3").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Assertions.assertEquals(Pages.ADMIN_PAGE,command.execute(request).getPage());
    }
    @Test
    void shouldNotDeleteProduct() throws CommandException {
        Mockito.doReturn("108").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Assertions.assertEquals(Pages.DELETE_PRODUCT,command.execute(request).getPage());
    }
}