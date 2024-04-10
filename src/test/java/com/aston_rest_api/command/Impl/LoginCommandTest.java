package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.arguments.UserArguments;
import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.model.User;
import com.aston_rest_api.service.UserService;
import com.aston_rest_api.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LoginCommandTest {
    LoginCommand command=new LoginCommand();
    UserService userService;
     HttpServletRequest request;
     HttpSession session;

    private UserDto userDto=new UserDto.UserDtoBuilder(1)
            .setLogin("michai@gmail.com")
            .setPassword("123")
            .setName("Michail")
            .setSurname("Radzivil")
            .setIsAdmin(1)
            .build();
    @BeforeEach
    void prepare(){
        request=Mockito.mock(HttpServletRequest.class);
        session=Mockito.mock(HttpSession.class);
        userService=Mockito.mock(UserServiceImpl.class);
    }

    @Test
    void shouldSuccessfullLogin() throws CommandException {
        Mockito.doReturn("michai@gmail.com").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.USER,userDto);
        Router router= command.execute(request);
        Assertions.assertEquals(Pages.USER_PAGE,router.getPage());
    }
    @Test
    void shouldUnuccessfulLogin() throws CommandException {
        Mockito.doReturn("michai@gmail.com").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346fr").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.USER,userDto);
        Router router= command.execute(request);
        Assertions.assertEquals(Pages.INDEX_PAGE,router.getPage());
    }
    @Test
    void shouldInvalidDataLogin() throws CommandException {
        Mockito.doReturn("com").when(request).getParameter(UserArguments.LOGIN);
        Mockito.doReturn("asrg346fr").when(request).getParameter(UserArguments.PASSWORD);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.USER,userDto);
        Router router= command.execute(request);
        Assertions.assertEquals(Pages.INDEX_PAGE,router.getPage());
    }
}