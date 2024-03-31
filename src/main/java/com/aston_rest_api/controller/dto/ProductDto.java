package com.aston_rest_api.controller.dto;

import java.util.Map;

public class ProductDto extends AbstractDto {
    private String productName;
    private double productDouble;
    private int amount;
    private Map<Long, UserDto>buyers;
    private ProductDescriptionDto description;

}
