package com.aston_rest_api.model;

import java.time.LocalDateTime;

public class Sale extends AbstractEntity {
    private long buyerId;
    private long productId;
    private LocalDateTime dateOfSale;

    private Product productOfSale;
    private int amountSale;

    private Sale(SaleBuilder builder) {
        super(builder.idSale);
        this.buyerId = builder.buyerId;
        this.productId=builder.productId;
        this.dateOfSale=builder.dateOfSale;
        this.amountSale= builder.amountSale;
        this.productOfSale= builder.productOfSale;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public LocalDateTime getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDateTime dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public int getAmountSale() {
        return amountSale;
    }

    public void setAmountSale(int amountSale) {
        this.amountSale = amountSale;
    }

    public Product getProductOfSale() {
        return productOfSale;
    }

    public void setProductOfSale(Product productOfSale) {
        this.productOfSale = productOfSale;
    }

    public static class SaleBuilder {
        private long idSale;
        private long buyerId;
        private long productId;
        private LocalDateTime dateOfSale;
        private Product productOfSale;
        private int amountSale;

        public SaleBuilder(long idSale) {
            this.idSale = idSale;
        }
        public SaleBuilder setBuyerId(long buyerId) {
            this.buyerId = buyerId;
            return this;
        }
        public SaleBuilder setProductId(long productId) {
            this.productId = productId;
            return this;
        }
        public SaleBuilder setAmountSale(int amountSale) {
            this.amountSale = amountSale;
            return this;
        }
        public SaleBuilder setDateOfSale(LocalDateTime dateOfSale) {
            this.dateOfSale =dateOfSale ;
            return this;
        }
        public SaleBuilder setProductOfSale(Product product) {
            this.productOfSale =product ;
            return this;
        }
        public Sale build(){
            return new Sale(this);
        }

    }
}
