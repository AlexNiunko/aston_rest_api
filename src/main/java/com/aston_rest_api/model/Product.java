package com.aston_rest_api.model;

import java.util.Map;

public class Product extends AbstractEntity {
    private String productName;
    private double productDouble;
    private int amount;
    private Map<Long,User>buyers;
    private ProductDescription description;

}
