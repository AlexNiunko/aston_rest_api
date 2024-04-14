package com.aston_rest_api.command.Impl;

import com.aston_rest_api.command.Command;
import com.aston_rest_api.command.Pages;
import com.aston_rest_api.command.Router;
import com.aston_rest_api.controller.arguments.ProductArguments;
import com.aston_rest_api.controller.arguments.ProductDescriptionArguments;
import com.aston_rest_api.controller.dto.ProductDescriptionDto;
import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.controller.mapper.Impl.ProductMapperImpl;
import com.aston_rest_api.controller.mapper.ProductMapper;
import com.aston_rest_api.dao.daoimpl.ProductDaoImpl;
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

import java.util.Optional;

public class UpdateProductCommand  implements Command {
    private HikariDataSource config;


    public UpdateProductCommand(HikariDataSource config) {
        this.config = config;
    }
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        ProductDaoImpl productDao = new ProductDaoImpl(ConnectionManagerImpl.getInstance(config));
        ProductService productService = new ProductServiceImpl(productDao);
        ProductMapper mapper = ProductMapperImpl.getMapper();
        ParameterValidator validator = ParameterValidator.getInstance();
        long idProduct= Long.parseLong(request.getParameter(ProductArguments.ID_PRODUCT));
        String productName = request.getParameter(ProductArguments.PRODUCT_NAME);
        String productPrice = request.getParameter(ProductArguments.PRODUCT_PRICE);
        String amountOfProduct = request.getParameter(ProductArguments.AMOUNT_OF_PRODUCT);
        String countryOfOrigin = request.getParameter(ProductDescriptionArguments.COUNTRY_OF_ORIGIN);
        String typeOfProduct = request.getParameter(ProductDescriptionArguments.TYPE_OF_PRODUCT);
        String brandOfProduct = request.getParameter(ProductDescriptionArguments.BRAND_OF_PRODUCT);
        String issueDate = request.getParameter(ProductDescriptionArguments.ISSUE_DATE);
        try{
            Product product=new Product.ProductBuilder(idProduct).build();
            Optional<Product>optionalProduct=productService.findProductBuId(product.getId());
                if (optionalProduct.isPresent()  && validator.validateNumber(productPrice) && validator.validateNumber(amountOfProduct)
                        && validator.validateNameOrSurname(countryOfOrigin) && validator.validateNameOrSurname(typeOfProduct)
                        && validator.validateNameOrSurname(brandOfProduct)
                        && validator.validateDate(issueDate)){
                    ProductDto productDto = new ProductDto.ProductDtoBuilder(idProduct)
                            .setProductName(productName)
                            .setProductPrice(productPrice)
                            .setProductAmount(amountOfProduct)
                            .build();
                    ProductDescriptionDto descriptionDto = new ProductDescriptionDto.ProductDescriptionBuilder(optionalProduct.get().getId())
                            .setIdProduct(idProduct)
                            .setCountryOfOrigin(countryOfOrigin)
                            .setBrand(brandOfProduct)
                            .setType(typeOfProduct)
                            .setIssueDate(issueDate)
                            .build();
                    productDto.setDescription(descriptionDto);
                    Product productToUpdate= mapper.map(productDto);
                    if (productService.updateProduct(productToUpdate)) {
                        router.setPage(Pages.ADMIN_PAGE);
                        router.setRedirect();
                    } else {
                        router.setPage(Pages.UPDATE_PRODUCT_PAGE);
                    }
                }else {
                    router.setPage(Pages.UPDATE_PRODUCT_PAGE);
                }
        }catch (ServiceException e){
            throw new CommandException("Failed to update product "+e);
        }
        return router;
    }
}
