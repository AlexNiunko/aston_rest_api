package com.aston_rest_api.controller.mapper;

import com.aston_rest_api.controller.dto.ProductDto;
import com.aston_rest_api.model.Product;

public interface ProductMapper {
    Product map(ProductDto productDto);
    ProductDto map(Product product);
}
