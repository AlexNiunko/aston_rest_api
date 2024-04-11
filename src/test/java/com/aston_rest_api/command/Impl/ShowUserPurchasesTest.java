package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.arguments.UserArguments;
import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.model.User;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ShowUserPurchasesTest {
    private static ShowUserPurchases command;
    private static HikariDataSource dataSource = new HikariDataSource();
    private static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:10-alpine")
            .withUsername("postgres")
            .withPassword("pass")
            .withInitScript("DB-migration.sql")
            .withDatabaseName("tool_box_test");

    private HttpServletRequest request;
    private HttpSession session;

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
        command = new ShowUserPurchases(dataSource);
    }

    private UserDto userDto=new UserDto.UserDtoBuilder(1)
            .setLogin("michai@gmail.com")
            .setPassword("asrg346")
            .setName("Michail")
            .setSurname("Radzivil")
            .build();

    @BeforeEach
    void prepare() {
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
    }

    @Test
    void shouldSuccessFindPurchases() throws CommandException {
        List<ProductDto>purchases=new ArrayList<>();
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER);
        Mockito.doNothing().when(session).setAttribute(Attributes.USER_PURCHASES,purchases);
        Router router = command.execute(request);
        Assertions.assertEquals(Pages.PURCHASES_PAGE,router.getPage());
    }
}