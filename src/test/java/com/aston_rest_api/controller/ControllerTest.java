package com.aston_rest_api.controller;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Pages;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Controller controller;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void prepare(){
        request=Mockito.mock(HttpServletRequest.class);
        response=Mockito.mock(HttpServletResponse.class);
        dispatcher=Mockito.mock(RequestDispatcher.class);
        controller=new Controller();

    }
    @Test
    void doGet() throws ServletException, IOException {
        Mockito.doReturn("default").when(request).getParameter(Attributes.COMMAND);
        Mockito.doReturn(dispatcher).when(request).getRequestDispatcher(Pages.INDEX_PAGE);
        Mockito.doNothing().when(dispatcher).forward(request,response);
        controller.doGet(request,response);
        Mockito.verify(dispatcher,Mockito.times(1)).forward(request,response);
    }

    @Test
    void doPost() throws ServletException, IOException {
        Mockito.doReturn("test_command").when(request).getParameter(Attributes.COMMAND);
        Mockito.doNothing().when(response).sendRedirect(Pages.INDEX_PAGE);
        controller.doPost(request,response);
        Mockito.verify(response,Mockito.times(1)).sendRedirect(Pages.INDEX_PAGE);
    }
    @Test
    void throwExceptionDoPost() throws ServletException, IOException {
        Mockito.doThrow(IllegalArgumentException.class).when(request).getParameter(Attributes.COMMAND);
        Assertions.assertThrows(ServletException.class,()->controller.doPost(request,response));
    }
}