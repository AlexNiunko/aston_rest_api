package com.astonrest.command.impl;

import com.astonrest.command.Command;
import com.astonrest.command.Pages;
import com.astonrest.command.Router;
import com.astonrest.controller.arguments.ProductArguments;
import com.astonrest.controller.arguments.ProductDescriptionArguments;
import com.astonrest.controller.dto.ProductDescriptionDto;
import com.astonrest.controller.dto.ProductDto;
import com.astonrest.controller.mapper.impl.ProductMapperImpl;
import com.astonrest.controller.mapper.ProductMapper;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.service.ProductService;
import com.astonrest.validator.ParameterValidator;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public class AddNewProductCommand implements Command {
    private ProductService productService;

    public AddNewProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
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
                    router.setPage(Pages.ADMIN_PAGE.getValue());
                } else {
                    router.setPage(Pages.ADD_NEW_PRODUCT.getValue());
                }
            } catch (ServiceException e) {
                throw new CommandException("Failed to add new product " + e);
            }

        } else {
            router.setPage(Pages.ADD_NEW_PRODUCT.getValue());
        }
        return router;
    }
}
