package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.arguments.SaleArguments;
import com.astonrest.controller.dto.SaleDto;
import com.astonrest.controller.mapper.impl.SaleMapperImpl;
import com.astonrest.controller.mapper.SaleMapper;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Sale;
import com.astonrest.service.SaleService;
import com.astonrest.validator.ParameterValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShowSalesByDateCommand implements Command {
    private SaleService saleService;

    public ShowSalesByDateCommand(SaleService saleService) {
        this.saleService = saleService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        ParameterValidator validator = ParameterValidator.getInstance();
        SaleMapper saleMapper = SaleMapperImpl.getMapper();
        String date = request.getParameter(SaleArguments.DATE_OF_SALE);
        try {
            if (validator.validateDate(date)) {
                LocalDate localDate = LocalDate.parse(date);
                List<Sale> saleList = saleService.findSalesByDate(localDate);
                List<SaleDto> saleDtos = new ArrayList<>();
                for (Sale sale : saleList) {
                    saleDtos.add(saleMapper.map(sale));
                }
                session.setAttribute(Attributes.SALES.toString().toLowerCase(), saleDtos);
                router.setPage(Pages.SALES_PAGE.getValue());
                router.setRedirect();
            } else {
                router.setPage(Pages.FIND_SALES_BY_DATE.getValue());
            }
        } catch (ServiceException e) {
            throw new CommandException("Failed buy product " + e);
        }
        return router;
    }
}
