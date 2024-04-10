package com.aston_rest_api.controller.mapper.Impl;

import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.controller.mapper.ProductMapper;
import com.aston_rest_api.model.Product;

public class ProductMapperImpl implements ProductMapper {
    private static ProductMapperImpl mapper=new ProductMapperImpl();
    private ProductMapperImpl() {
    }
    public static ProductMapperImpl getMapper(){
        return mapper;
    }
    @Override
    public Product map(ProductDto productDto) {
        return new Product
                .ProductBuilder(productDto.getProductDtoId())
                .setProductName(productDto.getProductName())
                .setProductPrice(Double.valueOf(productDto.getProductPrice()))
                .setAmount(Integer.valueOf(productDto.getAmount()))
                .setProductDescription(ProductDescriptionMapperImpl.getMapper().map(productDto.getDescription()))
                .build();
    }

    @Override
    public ProductDto map(Product product) {
        return new ProductDto
                .ProductDtoBuilder(product.getId())
                .setProductName(product.getProductName())
                .setProductPrice(String.valueOf(product.getProductPrice()))
                .setProductAmount(String.valueOf(product.getAmount()))
                .setDescriptions(ProductDescriptionMapperImpl.getMapper().map(product.getDescription()))
                .build();
    }
}
