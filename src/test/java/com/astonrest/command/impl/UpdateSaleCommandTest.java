package com.astonrest.command.impl;

import com.astonrest.command.Pages;
import com.astonrest.controller.arguments.SaleArguments;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Sale;
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
import java.time.LocalDate;

class UpdateSaleCommandTest {

    private static UpdateSaleCommand command;
    private static SaleService saleService;

    @BeforeAll
    static void init() throws SQLException {
        saleService=Mockito.mock(SaleServiceImpl.class);
        command = new UpdateSaleCommand(saleService);
    }
    private HttpServletRequest request;
    private HttpSession session;
    private Sale sale=new Sale.SaleBuilder(2L)
            .setDateOfSale(LocalDate.of(2024,3,25))
            .setAmountSale(3)
            .build();

    @BeforeEach
    void prepare(){
        request= Mockito.mock(HttpServletRequest.class);
        session=Mockito.mock(HttpSession.class);
    }
    @Test
    void shouldUpdateSale() throws CommandException, ServiceException {
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn("2").when(request).getParameter(SaleArguments.ID_SALE);
        Mockito.doReturn("2024-03-25").when(request).getParameter(SaleArguments.DATE_OF_SALE);
        Mockito.doReturn("3").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Mockito.doReturn(true).when(saleService).updateSale(sale);
        Assertions.assertEquals(Pages.ADMIN_PAGE.getValue(),command.execute(request).getPage());
    }
    @Test
    void shouldNotUpdateSale() throws CommandException {
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn("2").when(request).getParameter(SaleArguments.ID_SALE);
        Mockito.doReturn("202srh").when(request).getParameter(SaleArguments.DATE_OF_SALE);
        Mockito.doReturn("3").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Assertions.assertEquals(Pages.UPDATE_SALE.getValue(),command.execute(request).getPage());
    }

}