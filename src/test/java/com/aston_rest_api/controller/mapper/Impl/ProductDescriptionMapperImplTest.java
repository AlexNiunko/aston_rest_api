package com.aston_rest_api.controller.mapper.Impl;

import com.aston_rest_api.controller.dto.ProductDescriptionDto;
import com.aston_rest_api.controller.mapper.ProductDescriptionMapper;
import com.aston_rest_api.model.ProductDescription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductDescriptionMapperImplTest {

    private ProductDescriptionMapper mapper=ProductDescriptionMapperImpl.getMapper();
    private ProductDescription description=new ProductDescription
            .ProductDescriptionBuilder(1)
            .setProductId(1)
            .setCountryOfOrigin("China")
            .setType("hand tool")
            .setBrand("Expert")
            .setIssueDate(LocalDate.of(2024,2,9))
            .build();
    private ProductDescriptionDto productDescriptionDto=new ProductDescriptionDto
            .ProductDescriptionBuilder(1)
            .setIdProduct(1)
            .setCountryOfOrigin("China")
            .setType("hand tool")
            .setBrand("Expert")
            .setIssueDate("2024-02-09")
            .build();

    @Test
    void shouldMapToModel() {
        ProductDescription actual=mapper.map(productDescriptionDto);
        Assertions.assertEquals(description,actual);
    }

    @Test
    void shouldMapToDto() {
        ProductDescriptionDto actual=mapper.map(description);
        Assertions.assertEquals(productDescriptionDto,actual);
    }
}