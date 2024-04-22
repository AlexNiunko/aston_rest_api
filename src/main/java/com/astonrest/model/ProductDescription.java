package com.astonrest.model;

import java.time.LocalDate;
import java.util.Objects;

public class ProductDescription  {
    private long id;
    private long productID;
    private String countryOfOrigin;
    private String type;
    private String brand;
    private LocalDate issueDate;

    private ProductDescription(ProductDescriptionBuilder builder) {
        this.id= builder.productDescriptionId;
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

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDescription)) return false;
        ProductDescription that = (ProductDescription) o;
        return getProductID() == that.getProductID() && getCountryOfOrigin().equals(that.getCountryOfOrigin()) && getType().equals(that.getType()) && getBrand().equals(that.getBrand()) && getIssueDate().equals(that.getIssueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductID(), getCountryOfOrigin(), getType(), getBrand(), getIssueDate());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductDescriptionDto{");
        sb.append("productID=").append(productID);
        sb.append(", countryOfOrigin='").append(countryOfOrigin).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", issueDate=").append(issueDate);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    public static class ProductDescriptionBuilder {
        private long productDescriptionId;
        private long productID;
        private String countryOfOrigin;
        private String type;
        private String brand;
        private LocalDate issueDate;

        public ProductDescriptionBuilder(long productDescriptionId) {
            this.productDescriptionId = productDescriptionId;
        }
        public ProductDescriptionBuilder setProductId(long productId) {
            this.productID = productId;
            return this;
        }
        public ProductDescriptionBuilder setCountryOfOrigin(String countryOfOrigin) {
            this.countryOfOrigin = countryOfOrigin;
            return this;
        }

        public ProductDescriptionBuilder setType (String type) {
            this.type = type;
            return this;
        }
        public ProductDescriptionBuilder setBrand (String brand) {
            this.brand = brand;
            return this;
        }
        public ProductDescriptionBuilder setIssueDate (LocalDate issueDate) {
            this.issueDate = issueDate;
            return this;
        }
        public ProductDescription build(){
            return new ProductDescription(this);
        }

    }

}
