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
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UpdateProfileCommandTest {
    private static UpdateProfileCommand command;
    private static UserService userService;

    @BeforeAll
    static void init() throws SQLException {
        userService=Mockito.mock(UserServiceImpl.class);
        command = new UpdateProfileCommand(userService);
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
    void successfulUpdateUser() throws CommandException, ServiceException {
        Mockito.doReturn(userDto.getLogin()).when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn(userDto.getPassword()).when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn(userDto.getName()).when(request).getParameter(UserArguments.NAME);
        Mockito.doReturn(userDto.getSurname()).when(request).getParameter(UserArguments.SURNAME);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER.toString().toLowerCase());
        User user= UserMapperImpl.getMapper().map(userDto);
        Mockito.doReturn(true).when(userService).updateUser(user);
        Router router = command.execute(request);
        Assertions.assertEquals(Pages.USER_PAGE.getValue(), router.getPage());
    }

    @Test
    @Order(2)
    void unsuccessfulUpdateUser() throws CommandException {
        Mockito.doReturn("stefan@tut.by").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346ed235").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn("12345").when(request).getParameter(UserArguments.NAME);
        Mockito.doReturn("Batoyi").when(request).getParameter(UserArguments.SURNAME);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.USER.toString().toLowerCase(), userDto);
        Router router = command.execute(request);
        Assertions.assertEquals(Pages.UPDATE_PROFILE_PAGE.getValue(), router.getPage());
    }
}