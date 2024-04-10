package com.aston_rest_api.controller.mapper;

import com.aston_rest_api.controller.dto.ProductDescriptionDto;
import com.aston_rest_api.model.Product;
import com.aston_rest_api.model.ProductDescription;

public interface ProductDescriptionMapper {
    ProductDescription map(ProductDescriptionDto descriptionDto);
    ProductDescriptionDto map(ProductDescription productDescription);
}
