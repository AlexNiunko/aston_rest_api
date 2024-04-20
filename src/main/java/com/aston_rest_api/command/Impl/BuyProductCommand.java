package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Attributes;
import com.aston_rest_api.command.Command;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.arguments.SaleArguments;
import com.aston_rest_api.controller.dto.UserDto;
import com.aston_rest_api.controller.mapper.Impl.UserMapperImpl;
import com.aston_rest_api.controller.mapper.UserMapper;
import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.dao.daoimpl.SaleDaoImpl;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.Sale;
import com.aston_rest_api.model.User;
import com.aston_rest_api.service.ProductService;
import com.aston_rest_api.service.SaleService;
import com.aston_rest_api.service.impl.ProductServiceImpl;
import com.aston_rest_api.service.impl.SaleServiceImpl;
import com.aston_rest_api.validator.ParameterValidator;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class BuyProductCommand implements Command {
    private HikariDataSource config;

    public BuyProductCommand(HikariDataSource config) {
        this.config = config;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        ProductDaoImpl productDao = new ProductDaoImpl(ConnectionManagerImpl.getInstance(config));
        SaleDaoImpl saleDao = new SaleDaoImpl(ConnectionManagerImpl.getInstance(config));
        UserMapper userMapper = UserMapperImpl.getMapper();
        ProductService productService = new ProductServiceImpl(productDao);
        ParameterValidator validator = ParameterValidator.getInstance();
        SaleService saleService = new SaleServiceImpl(productDao, saleDao);
        long idProduct = Long.parseLong(request.getParameter(ProductArguments.ID_PRODUCT));
        String amountOfSale = request.getParameter(SaleArguments.AMOUNT_OF_SALE);
        UserDto userDto = (UserDto) session.getAttribute(Attributes.USER);
        User user = userMapper.map(userDto);
        Product product = new Product.ProductBuilder(idProduct).build();
        try {
            Optional<Product> optionalProduct = productService.findProductBuId(product.getId());
            if (optionalProduct.isPresent() && validator.validateNumber(amountOfSale)) {
                LocalDate dateOfSale = LocalDate.now();
                long idSale = UUID.randomUUID().getLeastSignificantBits();
                Sale sale = new Sale.SaleBuilder(idSale)
                        .setProductId(optionalProduct.get().getId())
                        .setBuyerId(user.getId())
                        .setDateOfSale(dateOfSale)
                        .setAmountSale(Integer.parseInt(amountOfSale))
                        .build();
                if (saleService.buyProduct(sale)) {
                    router.setPage(Pages.USER_PAGE);
                    router.setRedirect();
                } else {
                    router.setPage(Pages.BUY_PRODUCT);
                }
            } else {
                router.setPage(Pages.BUY_PRODUCT);
            }
        } catch (ServiceException e) {
            throw new CommandException("Failed buy product " + e);
        }
        return router;
    }
}
