package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Command;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.arguments.ProductDescriptionArguments;
import com.aston_rest_api.controller.dto.ProductDescriptionDto;
import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.controller.mapper.Impl.ProductMapperImpl;
import com.aston_rest_api.controller.mapper.Impl.UserMapperImpl;
import com.aston_rest_api.controller.mapper.ProductMapper;
import com.aston_rest_api.controller.mapper.UserMapper;
import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
import com.aston_rest_api.dao.daoimpl.UserDaoImpl;
import com.aston_rest_api.db.Configuration;
import com.aston_rest_api.db.ConnectionManagerImpl;
import com.aston_rest_api.exception.CommandException;
import com.aston_rest_api.exception.ServiceException;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.service.ProductService;
import com.aston_rest_api.service.impl.ProductServiceImpl;
import com.aston_rest_api.validator.ParameterValidator;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.UUID;

public class AddNewProductCommand  implements Command {
    private HikariDataSource config;



    public AddNewProductCommand(HikariDataSource config) {
        this.config = config;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HikariDataSource dataSource;
        ProductDaoImpl productDao = new ProductDaoImpl(ConnectionManagerImpl.getInstance(config));
        ProductService productService = new ProductServiceImpl(productDao);
        HttpSession session = request.getSession();
        ParameterValidator validator = ParameterValidator.getInstance();
        ProductMapper mapper = ProductMapperImpl.getMapper();
        long idDescription = UUID.randomUUID().getMostSignificantBits();
        long idProduct = UUID.randomUUID().getMostSignificantBits();
        String productName = request.getParameter(ProductArguments.PRODUCT_NAME);
        String productPrice = request.getParameter(ProductArguments.PRODUCT_PRICE);
        String amountOfProduct = request.getParameter(ProductArguments.AMOUNT_OF_PRODUCT);
        String countryOfOrigin = request.getParameter(ProductDescriptionArguments.COUNTRY_OF_ORIGIN);
        String typeOfProduct = request.getParameter(ProductDescriptionArguments.TYPE_OF_PRODUCT);
        String brandOfProduct = request.getParameter(ProductDescriptionArguments.BRAND_OF_PRODUCT);
        String issueDate = request.getParameter(ProductDescriptionArguments.ISSUE_DATE);
        if
        (validator.validateNumber(productPrice) && validator.validateNumber(amountOfProduct)
                && validator.validateNameOrSurname(countryOfOrigin) && validator.validateNameOrSurname(typeOfProduct)
                && validator.validateNameOrSurname(brandOfProduct)
                && validator.validateDate(issueDate)) {
            ProductDto productDto = new ProductDto.ProductDtoBuilder(idProduct)
                    .setProductName(productName)
                    .setProductPrice(productPrice)
                    .setProductAmount(amountOfProduct)
                    .build();
            ProductDescriptionDto descriptionDto = new ProductDescriptionDto.ProductDescriptionBuilder(idDescription)
                    .setIdProduct(idProduct)
                    .setCountryOfOrigin(countryOfOrigin)
                    .setBrand(brandOfProduct)
                    .setType(typeOfProduct)
                    .setIssueDate(issueDate)
                    .build();
            productDto.setDescription(descriptionDto);
            Product product = mapper.map(productDto);
            try {
                if (productService.addNewProduct(product)) {
                    router.setRedirect();
                    router.setPage(Pages.ADMIN_PAGE);
                } else {
                    router.setPage(Pages.ADD_NEW_PRODUCT);
                }
            } catch (ServiceException e) {
                throw new CommandException("Failed to add new product " + e);
            }

        } else {
            router.setPage(Pages.ADD_NEW_PRODUCT);
        }
        return router;
    }
}
