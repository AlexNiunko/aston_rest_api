package com.astonrest.controller.mapper.impl;

import com.astonrest.controller.dto.ProductDescriptionDto;
import com.astonrest.controller.mapper.ProductDescriptionMapper;
import com.astonrest.model.ProductDescription;

import java.time.LocalDate;

public class ProductDescriptionMapperImpl implements ProductDescriptionMapper {
    private static ProductDescriptionMapperImpl mapper=new ProductDescriptionMapperImpl();
    private ProductDescriptionMapperImpl() {
    }
    public static ProductDescriptionMapperImpl getMapper(){
        return mapper;
    }
    @Override
    public ProductDescription map(ProductDescriptionDto descriptionDto) {
        return new ProductDescription
                .ProductDescriptionBuilder(descriptionDto.getIdDescription())
                .setProductId(descriptionDto.getIdProduct())
                .setCountryOfOrigin(descriptionDto.getCountryOfOrigin())
                .setType(descriptionDto.getType())
                .setBrand(descriptionDto.getBrand())
                .setIssueDate(LocalDate.parse(descriptionDto.getIssueDate()))
                .build();
    }

    @Override
    public ProductDescriptionDto map(ProductDescription productDescription) {
        return new ProductDescriptionDto
                .ProductDescriptionBuilder(productDescription.getId())
                .setIdProduct(productDescription.getProductID())
                .setCountryOfOrigin(productDescription.getCountryOfOrigin())
                .setBrand(productDescription.getBrand())
                .setType(productDescription.getType())
                .setIssueDate(productDescription.getIssueDate().toString())
                .build();
    }
}
