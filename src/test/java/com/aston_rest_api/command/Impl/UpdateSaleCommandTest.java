package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.controller.arguments.SaleArguments;
import com.aston_rest_api.controller.dto.SaleDto;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UpdateSaleCommandTest {
    private static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:10-alpine")
            .withUsername("postgres")
            .withPassword("pass")
            .withInitScript("DB-migration.sql")
            .withDatabaseName("tool_box_test");
    private static HikariDataSource dataSource = new HikariDataSource();
    private static UpdateSaleCommand command;

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
        command = new UpdateSaleCommand(dataSource);
    }
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    void prepare(){
        request= Mockito.mock(HttpServletRequest.class);
        session=Mockito.mock(HttpSession.class);
    }
    @Test
    void shouldUpdateSale() throws CommandException {
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn("2").when(request).getParameter(SaleArguments.ID_SALE);
        Mockito.doReturn("2024-03-25").when(request).getParameter(SaleArguments.DATE_OF_SALE);
        Mockito.doReturn("3").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Assertions.assertEquals(Pages.ADMIN_PAGE,command.execute(request).getPage());
    }
    @Test
    void shouldNotUpdateSale() throws CommandException {
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn("2").when(request).getParameter(SaleArguments.ID_SALE);
        Mockito.doReturn("202srh").when(request).getParameter(SaleArguments.DATE_OF_SALE);
        Mockito.doReturn("3").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Assertions.assertEquals(Pages.UPDATE_SALE,command.execute(request).getPage());
    }

}