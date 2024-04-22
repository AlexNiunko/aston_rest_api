package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Pages;
import com.astonrest.controller.dto.SaleDto;
import com.astonrest.exception.CommandException;
import com.astonrest.service.SaleService;
import com.astonrest.service.impl.SaleServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ShowAllSalesCommandTest {
    private static ShowAllSalesCommand command;
    private static SaleService saleService;

    @BeforeAll
    static void init() throws SQLException {
        saleService=Mockito.mock(SaleServiceImpl.class);
        command = new ShowAllSalesCommand(saleService);
    }
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    void prepare(){
        request= Mockito.mock(HttpServletRequest.class);
        session=Mockito.mock(HttpSession.class);
    }
    @Test
    void shouldShowAllSales() throws CommandException {
        List<SaleDto>saleDtoList=new ArrayList<>();
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doNothing().when(session).setAttribute(Attributes.SALES.toString().toLowerCase(),saleDtoList);
        Assertions.assertEquals(Pages.SALES_PAGE.getValue(),command.execute(request).getPage());
    }

}