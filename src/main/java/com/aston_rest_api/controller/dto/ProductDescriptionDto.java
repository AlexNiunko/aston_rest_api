package com.aston_rest_api.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProductDescriptionDto extends AbstractDto {
    private long productID;
    private String countryOfOrigin;
    private String type;
    private String brand;
    private LocalDateTime issueDate;

    private ProductDescriptionDto(ProductDescriptionDTOBuilder builder) {
        super(builder.productDescriptionId);
        this.productID = builder.productID;
        this.countryOfOrigin = builder.countryOfOrigin;
        this.type = builder.type;
        this.brand = builder.brand;
        this.issueDate = builder.issueDate;
    }


    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public static class ProductDescriptionDTOBuilder{
        private long productDescriptionId;
        private long productID;
        private String countryOfOrigin;
        private String type;
        private String brand;
        private LocalDateTime issueDate;

        public ProductDescriptionDTOBuilder(long productDescriptionId) {
            this.productDescriptionId = productDescriptionId;
        }
        public ProductDescriptionDTOBuilder setProductId(long productId) {
            this.productID = productId;
            return this;
        }
        public ProductDescriptionDTOBuilder setCountryOfOrigin(String countryOfOrigin) {
            this.countryOfOrigin = countryOfOrigin;
            return this;
        }
        public ProductDescriptionDTOBuilder setType (String type) {
            this.type = type;
            return this;
        }
        public ProductDescriptionDTOBuilder setBrand (String brand) {
            this.brand = brand;
            return this;
        }
        public ProductDescriptionDTOBuilder setIssueDate (LocalDateTime issueDate) {
            this.issueDate = issueDate;
            return this;
        }
        public ProductDescriptionDto build(){
            return new ProductDescriptionDto(this);
        }

    }

}
