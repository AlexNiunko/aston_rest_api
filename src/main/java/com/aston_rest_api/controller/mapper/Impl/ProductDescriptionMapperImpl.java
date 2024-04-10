package com.aston_rest_api.controller.mapper.Impl;

import com.aston_rest_api.controller.dto.ProductDescriptionDto;
import com.aston_rest_api.controller.mapper.ProductDescriptionMapper;
import com.aston_rest_api.model.ProductDescription;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
