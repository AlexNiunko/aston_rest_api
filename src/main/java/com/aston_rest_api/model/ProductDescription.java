package com.aston_rest_api.model;

import java.time.LocalDateTime;

public class ProductDescription extends AbstractEntity {
    private long productID;
    private String countryOfOrigin;
    private String type;
    private String brand;
    private LocalDateTime issueDate;

    private ProductDescription(ProductDescriptionBuilder builder) {
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
        private LocalDateTime issueDate;

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
        public ProductDescriptionBuilder setIssueDate (LocalDateTime issueDate) {
            this.issueDate = issueDate;
            return this;
        }
        public ProductDescription build(){
            return new ProductDescription(this);
        }

    }

}
