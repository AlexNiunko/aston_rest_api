package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.dto.UserDto;
import com.astonrest.controller.mapper.impl.UserMapperImpl;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.User;
import com.astonrest.service.UserService;
import com.astonrest.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ShowUserPurchasesTest {
    private static ShowUserPurchases command;
    private static UserService userService;


    private HttpServletRequest request;
    private HttpSession session;

    @BeforeAll
    static void init() throws SQLException {
        userService=Mockito.mock(UserServiceImpl.class);
        command = new ShowUserPurchases(userService);
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
    void shouldSuccessFindPurchases() throws CommandException, ServiceException {
        User user= UserMapperImpl.getMapper().map(userDto);
        List<Product>purchases=new ArrayList<>();
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER.toString().toLowerCase());
        Mockito.doReturn(purchases).when(userService).selectUserPurchases(user);
        Mockito.doNothing().when(session).setAttribute(Attributes.USER_PURCHASES.toString().toLowerCase(),purchases);
        Router router = command.execute(request);
        Assertions.assertEquals(Pages.PURCHASES_PAGE.getValue(),router.getPage());
    }
}