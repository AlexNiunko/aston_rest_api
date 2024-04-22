package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.dto.SaleDto;
import com.astonrest.controller.mapper.impl.SaleMapperImpl;
import com.astonrest.controller.mapper.SaleMapper;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Sale;
import com.astonrest.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class ShowAllSalesCommand implements Command {
    private SaleService saleService;

    public ShowAllSalesCommand(SaleService saleService) {
        this.saleService = saleService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        SaleMapper saleMapper = SaleMapperImpl.getMapper();
        try {
            List<Sale> saleList = saleService.findAllSales();
            List<SaleDto> saleDtos = new ArrayList<>();
            for (Sale sale : saleList) {
                saleDtos.add(saleMapper.map(sale));
            }
            session.setAttribute(Attributes.SALES.toString().toLowerCase(), saleDtos);
            router.setPage(Pages.SALES_PAGE.getValue());
        } catch (ServiceException e) {
            throw new CommandException("Failed buy product " + e);
        }
        return router;
    }
}
