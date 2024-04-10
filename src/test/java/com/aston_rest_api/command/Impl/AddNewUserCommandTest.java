package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.arguments.UserArguments;
import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.service.UserService;
import com.aston_rest_api.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddNewUserCommandTest {
    AddNewUserCommand command=new AddNewUserCommand();
    UserService userService;
    HttpServletRequest request;
    HttpSession session;

    private UserDto userDto=new UserDto.UserDtoBuilder(1)
            .setLogin("stefan@tut.by")
            .setPassword("asrg346ed")
            .setName("Stefan")
            .setSurname("Batoyi")
            .setIsAdmin(1)
            .build();
    @BeforeEach
    void prepare(){
        request= Mockito.mock(HttpServletRequest.class);
        session=Mockito.mock(HttpSession.class);
        userService=Mockito.mock(UserServiceImpl.class);
    }

    @Test
    @Order(1)
    void successfulAddNewUser() throws CommandException {
        Mockito.doReturn("stefan@tut.by").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346ed").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn("Stefan").when(request).getParameter(UserArguments.NAME);
        Mockito.doReturn("Batoyi").when(request).getParameter(UserArguments.SURNAME);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.USER,userDto);
        Router router= command.execute(request);
        Assertions.assertEquals(Pages.USER_PAGE,router.getPage());
    }
    @Test
    @Order(2)
    void unsuccessfulAddNewUser() throws CommandException {
        Mockito.doReturn("stefan@tut.by").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346ed").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn("12345").when(request).getParameter(UserArguments.NAME);
        Mockito.doReturn("Batoyi").when(request).getParameter(UserArguments.SURNAME);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.USER,userDto);
        Router router= command.execute(request);
        Assertions.assertEquals(Pages.INDEX_PAGE,router.getPage());
    }
    @Test
    void throwAddNewUser() throws CommandException {
        Mockito.doReturn("stefan@tut.by").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346ed").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn("12345").when(request).getParameter(UserArguments.NAME);
        Mockito.doReturn("Batoyi").when(request).getParameter(UserArguments.SURNAME);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.USER,userDto);
        Router router= command.execute(request);
        Assertions.assertEquals(Pages.INDEX_PAGE,router.getPage());
    }


}