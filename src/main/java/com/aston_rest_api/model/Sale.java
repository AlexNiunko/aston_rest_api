package com.aston_rest_api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Sale extends AbstractEntity {
    private long buyerId;
    private long productId;
    private LocalDate dateOfSale;
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

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public static class SaleBuilder {
        private long idSale;
        private long buyerId;
        private long productId;
        private LocalDate dateOfSale;
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
        public SaleBuilder setDateOfSale(LocalDate dateOfSale) {
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
