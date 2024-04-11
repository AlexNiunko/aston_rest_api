package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.arguments.UserArguments;
import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.exception.CommandException;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UpdateProfileCommandTest {

    private static PostgreSQLContainer container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:10-alpine")
            .withUsername("postgres")
            .withPassword("pass")
            .withInitScript("DB-migration.sql")
            .withDatabaseName("tool_box_test");
    private static HikariDataSource dataSource = new HikariDataSource();
    private static UpdateProfileCommand command;

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
        command = new UpdateProfileCommand(dataSource);
    }

    private HttpServletRequest request;
    private HttpSession session;

    private UserDto userDto = new UserDto.UserDtoBuilder(1)
            .setLogin("stefan@tut.by")
            .setPassword("asrg346ed")
            .setName("Stefan")
            .setSurname("Batoyi")
            .setIsAdmin(1)
            .build();

    @BeforeEach
    void prepare() {
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
    }

    @Test
    @Order(1)
    void successfulUpdateUser() throws CommandException {
        Mockito.doReturn("sfn@tut.by").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346ed1234").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn("Stefanoowefos").when(request).getParameter(UserArguments.NAME);
        Mockito.doReturn("Batoyiuswef").when(request).getParameter(UserArguments.SURNAME);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER);
        Router router = command.execute(request);
        Assertions.assertEquals(Pages.USER_PAGE, router.getPage());
    }

    @Test
    @Order(2)
    void unsuccessfulUpdateUser() throws CommandException {
        Mockito.doReturn("stefan@tut.by").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346ed235").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn("12345").when(request).getParameter(UserArguments.NAME);
        Mockito.doReturn("Batoyi").when(request).getParameter(UserArguments.SURNAME);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.USER, userDto);
        Router router = command.execute(request);
        Assertions.assertEquals(Pages.UPDATE_PROFILE_PAGE, router.getPage());
    }

}