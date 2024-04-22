package com.astonrest.controller.mapper;

import com.astonrest.controller.dto.ProductDto;
import com.astonrest.model.Product;

public interface ProductMapper {
    Product map(ProductDto productDto);
    ProductDto map(Product product);
}
