package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.arguments.ProductArguments;
import com.astonrest.controller.arguments.SaleArguments;
import com.astonrest.controller.dto.UserDto;
import com.astonrest.controller.mapper.impl.UserMapperImpl;
import com.astonrest.controller.mapper.UserMapper;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.Sale;
import com.astonrest.model.User;
import com.astonrest.service.ProductService;
import com.astonrest.service.SaleService;
import com.astonrest.validator.ParameterValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.Optional;

public class BuyProductCommand implements Command {
    private SaleService saleService;
    private ProductService productService;

    public BuyProductCommand(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        UserMapper userMapper = UserMapperImpl.getMapper();
        ParameterValidator validator = ParameterValidator.getInstance();
        long idProduct = Long.parseLong(request.getParameter(ProductArguments.ID_PRODUCT));
        String amountOfSale = request.getParameter(SaleArguments.AMOUNT_OF_SALE);
        UserDto userDto = (UserDto) session.getAttribute(Attributes.USER.toString().toLowerCase());
        LocalDate dateOfSale = LocalDate.now();
        User user = userMapper.map(userDto);
        Product product = new Product.ProductBuilder(idProduct).build();
        try {
            Optional<Product> optionalProduct = productService.findProductBuId(product.getId());
            if (optionalProduct.isPresent() && validator.validateNumber(amountOfSale)) {
                Sale sale = new Sale.SaleBuilder(optionalProduct.get().getId() + optionalProduct.get().getDescription().getId())
                        .setProductId(optionalProduct.get().getId())
                        .setBuyerId(user.getId())
                        .setDateOfSale(dateOfSale)
                        .setAmountSale(Integer.parseInt(amountOfSale))
                        .build();
                if (saleService.buyProduct(sale)) {
                    router.setPage(Pages.USER_PAGE.getValue());
                    router.setRedirect();
                } else {
                    router.setPage(Pages.BUY_PRODUCT.getValue());
                }
            } else {
                router.setPage(Pages.BUY_PRODUCT.getValue());
            }
        } catch (ServiceException e) {
            throw new CommandException("Failed buy product " + e);
        }
        return router;
    }
}
