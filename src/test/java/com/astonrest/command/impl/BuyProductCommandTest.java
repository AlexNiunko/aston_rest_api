package com.astonrest.command.impl;

import com.astonrest.command.Attributes;
import com.astonrest.command.Pages;
import com.astonrest.controller.arguments.ProductArguments;
import com.astonrest.controller.arguments.SaleArguments;
import com.astonrest.controller.dto.UserDto;
import com.astonrest.exception.CommandException;
import com.astonrest.exception.ServiceException;
import com.astonrest.model.Product;
import com.astonrest.model.ProductDescription;
import com.astonrest.model.Sale;
import com.astonrest.service.ProductService;
import com.astonrest.service.SaleService;
import com.astonrest.service.impl.ProductServiceImpl;
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
import java.util.Optional;

class BuyProductCommandTest {
    private static BuyProductCommand command;
    private static SaleService saleService;
    private static ProductService productService;
    @BeforeAll
    static void init() throws SQLException {
        saleService=Mockito.mock(SaleServiceImpl.class);
        productService=Mockito.mock(ProductServiceImpl.class);
        command = new BuyProductCommand(saleService,productService);
    }
    private UserDto userDto = new UserDto.UserDtoBuilder(1)
            .setLogin("michai@gmail.com")
            .setPassword("123")
            .setName("Michail")
            .setSurname("Radzivil")
            .setIsAdmin(1)
            .build();
    private Product product=new Product
            .ProductBuilder(3)
            .setProductName("hammer")
            .setProductPrice(10.25)
            .setAmount(5)
            .build();
    private ProductDescription description=new ProductDescription
            .ProductDescriptionBuilder(1)
            .setProductId(3)
            .setCountryOfOrigin("China")
            .setType("hand tool")
            .setBrand("Expert")
            .setIssueDate(LocalDate.of(2024,2,9))
            .build();
    private Sale sale=new Sale.SaleBuilder(product.getId()+description.getId())
                .setBuyerId(userDto.getIdUser())
                .setProductId(3)
                .setDateOfSale(LocalDate.now())
                .setAmountSale(1)
                .build();

    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    void prepare(){
        request= Mockito.mock(HttpServletRequest.class);
        session=Mockito.mock(HttpSession.class);
    }
    @Test
    void shouldBuyProduct() throws CommandException, ServiceException {
        product.setDescription(description);
        Optional<Product>optionalProduct=Optional.of(product);
        Mockito.doReturn("3").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn("1").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER.toString().toLowerCase());
        Mockito.doReturn(optionalProduct).when(productService).findProductBuId(3);
        Mockito.doReturn(true).when(saleService).buyProduct(sale);
        Assertions.assertEquals(Pages.USER_PAGE.getValue(),command.execute(request).getPage());
    }
    @Test
    void shouldNotBuyProduct() throws CommandException {
        Mockito.doReturn("143").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn("1").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER.toString().toLowerCase());
        Assertions.assertEquals(Pages.BUY_PRODUCT.getValue(),command.execute(request).getPage());
    }
    @Test
    void invalidInpuDataBuyProduct() throws CommandException {
        Mockito.doReturn("143").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn("dgg").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER.toString().toLowerCase());
        Assertions.assertEquals(Pages.BUY_PRODUCT.getValue(),command.execute(request).getPage());
    }
    @Test
    void invalidAmountBuyProduct() throws CommandException {
        Mockito.doReturn("1").when(request).getParameter(ProductArguments.ID_PRODUCT);
        Mockito.doReturn("1000").when(request).getParameter(SaleArguments.AMOUNT_OF_SALE);
        Mockito.doReturn(session).when(request).getSession();
        Mockito.doReturn(userDto).when(session).getAttribute(Attributes.USER.toString().toLowerCase());
        Assertions.assertEquals(Pages.BUY_PRODUCT.getValue(),command.execute(request).getPage());
    }

}