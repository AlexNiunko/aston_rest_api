package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.arguments.UserArguments;
import com.astonrest.controller.dto.UserDto;
import com.astonrest.controller.mapper.impl.UserMapperImpl;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.User;
import com.astonrest.service.UserService;
import com.astonrest.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

class LoginCommandTest {
    private static LoginCommand command;
    private static UserService userService;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeAll
    static void init() throws SQLException {
        userService=Mockito.mock(UserServiceImpl.class);
        command = new LoginCommand(userService);
    }

    private static UserDto userDto = new UserDto.UserDtoBuilder("michai@gmail.com".length()*"asrg346".length())
            .setName("")
            .setSurname("")
            .setLogin("michai@gmail.com")
            .setPassword("asrg346")
            .build();

    @BeforeEach
    void prepare() {
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
    }

    @Test
    void shouldSuccessfullLogin() throws CommandException, ServiceException {
        User user= UserMapperImpl.getMapper().map(userDto);
        Optional<User>optionalUser=Optional.of(user);
        Mockito.doReturn("michai@gmail.com").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(optionalUser).when(userService).authenticate(user);
        Mockito.doNothing().when(session).setAttribute(Attributes.USER.toString().toLowerCase(), userDto);
        Router router = command.execute(request);
        Assertions.assertEquals(Pages.USER_PAGE.getValue(), router.getPage());
    }

    @Test
    void shouldUnuccessfulLogin() throws CommandException {
        Mockito.doReturn("michai@gmail.com").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346fr").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.USER.toString().toLowerCase(), userDto);
        Router router = command.execute(request);
        Assertions.assertEquals(Pages.INDEX_PAGE.getValue(), router.getPage());
    }

    @Test
    void shouldInvalidDataLogin() throws CommandException {
        Mockito.doReturn("com").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346fr").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.USER.toString().toLowerCase(), userDto);
        Router router = command.execute(request);
        Assertions.assertEquals(Pages.INDEX_PAGE.getValue(), router.getPage());
    }
}