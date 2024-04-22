package com.astonrest.controller.dto;

import java.util.Objects;

public class ProductDescriptionDto {
    private long idDescription;
    private long idProduct;
    private String countryOfOrigin;
    private String type;
    private String brand;
    private String issueDate;

    private ProductDescriptionDto(ProductDescriptionBuilder builder) {
        this.idDescription = builder.idDescription;
        this.idProduct = builder.idProduct;
        this.countryOfOrigin = builder.countryOfOrigin;
        this.type = builder.type;
        this.brand = builder.brand;
        this.issueDate = builder.issueDate;
    }

    public long getIdDescription() {
        return idDescription;
    }

    public void setIdDescription(long idDescription) {
        this.idDescription = idDescription;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDescriptionDto)) return false;
        ProductDescriptionDto that = (ProductDescriptionDto) o;
        return getIdDescription() == that.getIdDescription() && getIdProduct() == that.getIdProduct() && getCountryOfOrigin().equals(that.getCountryOfOrigin()) && getType().equals(that.getType()) && getBrand().equals(that.getBrand()) && getIssueDate().equals(that.getIssueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdDescription(), getIdProduct(), getCountryOfOrigin(), getType(), getBrand(), getIssueDate());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductDescriptionDto{");
        sb.append("idDescription=").append(idDescription);
        sb.append(", idProduct=").append(idProduct);
        sb.append(", countryOfOrigin='").append(countryOfOrigin).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", issueDate='").append(issueDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class ProductDescriptionBuilder {
        private long idDescription;
        private long idProduct;
        private String countryOfOrigin;
        private String type;
        private String brand;
        private String issueDate;

        public ProductDescriptionBuilder(long idDescription) {
            this.idDescription = idDescription;
        }

        public ProductDescriptionBuilder setIdProduct(long idProduct) {
            this.idProduct = idProduct;
            return this;
        }

        public ProductDescriptionBuilder setCountryOfOrigin(String countryOfOrigin) {
            this.countryOfOrigin = countryOfOrigin;
            return this;
        }

        public ProductDescriptionBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public ProductDescriptionBuilder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public ProductDescriptionBuilder setIssueDate(String issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public ProductDescriptionDto build() {
            return new ProductDescriptionDto(this);
        }
    }


}
