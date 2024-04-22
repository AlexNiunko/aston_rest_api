package com.astonrest.controller;

import com.astonrest.command.Attributes;
import com.astonrest.command.Pages;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Enumeration;

class ControllerTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Controller controller;
    private RequestDispatcher dispatcher;
    ServletConfig config=new ServletConfig() {
        @Override
        public String getServletName() {
            return null;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public String getInitParameter(String s) {
            return null;
        }

        @Override
        public Enumeration<String> getInitParameterNames() {
            return null;
        }
    };

    @BeforeEach
    void prepare(){
        request=Mockito.mock(HttpServletRequest.class);
        response=Mockito.mock(HttpServletResponse.class);
        dispatcher=Mockito.mock(RequestDispatcher.class);
        controller=new Controller(config);

    }
    @Test
    void doGet() throws ServletException, IOException {
        Mockito.doReturn("default").when(request).getParameter(Attributes.COMMAND.toString().toLowerCase());
        Mockito.doReturn(dispatcher).when(request).getRequestDispatcher(Pages.INDEX_PAGE.getValue());
        Mockito.doNothing().when(dispatcher).forward(request,response);
        controller.doGet(request,response);
        Mockito.verify(dispatcher,Mockito.times(1)).forward(request,response);
    }

    @Test
    void doPost() throws ServletException, IOException {
        Mockito.doReturn("test_command").when(request).getParameter(Attributes.COMMAND.toString().toLowerCase());
        Mockito.doNothing().when(response).sendRedirect(Pages.INDEX_PAGE.getValue());
        controller.doPost(request,response);
        Mockito.verify(response,Mockito.times(1)).sendRedirect(Pages.INDEX_PAGE.getValue());
    }
    @Test
    void throwExceptionDoPost() throws ServletException, IOException {
        Mockito.doThrow(IllegalArgumentException.class).when(request).getParameter(Attributes.COMMAND.toString().toLowerCase());
        Assertions.assertThrows(ServletException.class,()->controller.doPost(request,response));
    }
}