package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.arguments.SaleArguments;
import com.aston_rest_api.controller.dto.UserDto;
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

import static org.junit.jupiter.api.Assertions.*;

class BuyProductCommandTest {
    private static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:10-alpine")
            .withUsername("postgres")
            .withPassword("pass")
            .withInitScript("DB-migration.sql")
            .withDatabaseName("tool_box_test");
    private static HikariDataSource dataSource = new HikariDataSource();
    private static BuyProductCommand command;

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
        command = new BuyProductCommand(dataSource);
    }
    private UserDto userDto = new UserDto.UserDtoBuilder(1)
            .setLogin("michai@gmail.com")
            .setPassword("123")
            .setName("Michail")
            .setSurname("Radzivil")
            .setIsAdmin(1)
            .build();

    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    void prepare(){
        request= Mockito.mock(HttpServletRequest.class);
        session=Mockito.mock(HttpSession.class);
    }
    @Test
    void shouldBuyProduct() throws CommandException {
        Mockito.doReturn("3").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn("1").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER);
        Assertions.assertEquals(Pages.USER_PAGE,command.execute(request).getPage());
    }
    @Test
    void shouldNotBuyProduct() throws CommandException {
        Mockito.doReturn("143").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn("1").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER);
        Assertions.assertEquals(Pages.BUY_PRODUCT,command.execute(request).getPage());
    }
    @Test
    void invalidInpuDataBuyProduct() throws CommandException {
        Mockito.doReturn("143").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn("dgg").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER);
        Assertions.assertEquals(Pages.BUY_PRODUCT,command.execute(request).getPage());
    }
    @Test
    void invalidAmountBuyProduct() throws CommandException {
        Mockito.doReturn("1").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn("1000").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER);
        Assertions.assertEquals(Pages.BUY_PRODUCT,command.execute(request).getPage());
    }

}