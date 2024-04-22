package com.astonrest.command.impl;

import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.arguments.SaleArguments;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Sale;
import com.astonrest.service.SaleService;
import com.astonrest.validator.ParameterValidator;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;

public class UpdateSaleCommand implements Command {
    private SaleService saleService;

    public UpdateSaleCommand(SaleService saleService) {
        this.saleService = saleService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        ParameterValidator validator = ParameterValidator.getInstance();
        long idSale = Long.parseLong(request.getParameter(SaleArguments.ID_SALE));
        String dateOfSale = request.getParameter(SaleArguments.DATE_OF_SALE);
        String amountOfSale = request.getParameter(SaleArguments.AMOUNT_OF_SALE);
        if (validator.validateDate(dateOfSale) && validator.validateNumber(amountOfSale)) {
            Sale sale = new Sale.SaleBuilder(idSale)
                    .setDateOfSale(LocalDate.parse(dateOfSale))
                    .setAmountSale(Integer.parseInt(amountOfSale))
                    .build();
            try {
                if (saleService.updateSale(sale)) {
                    router.setPage(Pages.ADMIN_PAGE.getValue());
                    router.setRedirect();
                } else {
                    router.setPage(Pages.UPDATE_SALE.getValue());
                }
            } catch (ServiceException e) {
                throw new CommandException("Failed buy product " + e);
            }
        } else {
            router.setPage(Pages.UPDATE_SALE.getValue());
        }
        return router;
    }
}
