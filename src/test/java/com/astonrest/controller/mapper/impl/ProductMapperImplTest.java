package com.astonrest.controller.mapper.impl;

import com.astonrest.controller.dto.ProductDescriptionDto;
import com.astonrest.controller.dto.ProductDto;
import com.astonrest.controller.mapper.ProductMapper;
import com.astonrest.model.Product;
import com.astonrest.model.ProductDescription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ProductMapperImplTest {
    private ProductMapper mapper=ProductMapperImpl.getMapper();
    private static ProductDescription description=new ProductDescription
            .ProductDescriptionBuilder(1)
            .setProductId(1)
            .setCountryOfOrigin("China")
            .setType("hand tool")
            .setBrand("Expert")
            .setIssueDate(LocalDate.of(2024,2,9))
            .build();
    private static ProductDescriptionDto productDescriptionDto=new ProductDescriptionDto
            .ProductDescriptionBuilder(1)
            .setIdProduct(1)
            .setCountryOfOrigin("China")
            .setType("hand tool")
            .setBrand("Expert")
            .setIssueDate("2024-02-09")
            .build();
    private static Product product = new Product.ProductBuilder(1)
            .setProductName("hammer")
            .setProductPrice(10.25)
            .setAmount(5)
            .build();
    private static ProductDto productDto = new ProductDto.ProductDtoBuilder(1)
            .setProductName("hammer")
            .setProductPrice("10.25")
            .setProductAmount("5")
            .build();
    @BeforeAll
    static void init(){
        product.setDescription(description);
        productDto.setDescription(productDescriptionDto);
    }

    @Test
    void shouldMapToModel() {
        Product actual= mapper.map(productDto);
        Assertions.assertEquals(product,actual);
    }

    @Test
    void shouldMapToDto() {
        ProductDto actual=mapper.map(product);
        Assertions.assertEquals(productDto,actual);
    }
}