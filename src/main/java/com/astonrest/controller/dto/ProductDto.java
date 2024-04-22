package com.astonrest.controller.dto;

import java.util.Objects;

public class ProductDto {
    private long productDtoId;
    private String productName;
    private String productPrice;
    private String amount;
    private ProductDescriptionDto description;

    private ProductDto(ProductDtoBuilder builder) {
        this.productDtoId = builder.productDtoId;
        this.productName = builder.productName;
        this.productPrice = builder.productPrice;
        this.amount = builder.amount;
        this.description = builder.description;
    }

    public long getProductDtoId() {
        return productDtoId;
    }

    public void setProductDtoId(long productDtoId) {
        this.productDtoId = productDtoId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public ProductDescriptionDto getDescription() {
        return description;
    }

    public void setDescription(ProductDescriptionDto description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto)) return false;
        ProductDto that = (ProductDto) o;
        return getProductDtoId() == that.getProductDtoId() && getProductName().equals(that.getProductName()) && getProductPrice().equals(that.getProductPrice()) && getAmount().equals(that.getAmount()) && getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductDtoId(), getProductName(), getProductPrice(), getAmount(), getDescription());
    }

    public static class ProductDtoBuilder {
        private long productDtoId;
        private String productName;
        private String productPrice;
        private String amount;
        private ProductDescriptionDto description;

        public ProductDtoBuilder(long productDtoId) {
            this.productDtoId = productDtoId;
        }

        public ProductDtoBuilder setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductDtoBuilder setProductPrice(String productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public ProductDtoBuilder setProductAmount(String productAmount) {
            this.amount = productAmount;
            return this;
        }

        public ProductDtoBuilder setDescriptions(ProductDescriptionDto description) {
            this.description = description;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(this);
        }


    }

}
