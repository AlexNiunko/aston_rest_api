package com.astonrest.controller.mapper;

import com.astonrest.controller.dto.ProductDescriptionDto;
import com.astonrest.model.ProductDescription;

public interface ProductDescriptionMapper {
    ProductDescription map(ProductDescriptionDto descriptionDto);
    ProductDescriptionDto map(ProductDescription productDescription);
}
