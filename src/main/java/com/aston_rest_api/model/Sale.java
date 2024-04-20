package com.aston_rest_api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Sale  {
    private long id;
    private long buyerId;
    private long productId;
    private LocalDate dateOfSale;
    private int amountSale;

    private Sale(SaleBuilder builder) {
        this.id=builder.idSale;
        this.buyerId = builder.buyerId;
        this.productId=builder.productId;
        this.dateOfSale=builder.dateOfSale;
        this.amountSale= builder.amountSale;
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

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public int getAmountSale() {
        return amountSale;
    }

    public void setAmountSale(int amountSale) {
        this.amountSale = amountSale;
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
        if (!(o instanceof Sale)) return false;
        Sale sale = (Sale) o;
        return getBuyerId() == sale.getBuyerId() && getProductId() == sale.getProductId() && getAmountSale() == sale.getAmountSale() && getDateOfSale().equals(sale.getDateOfSale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuyerId(), getProductId(), getDateOfSale(), getAmountSale());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sale{");
        sb.append("buyerId=").append(buyerId);
        sb.append(", productId=").append(productId);
        sb.append(", dateOfSale=").append(dateOfSale);
        sb.append(", amountSale=").append(amountSale);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    public static class SaleBuilder {
        private long idSale;
        private long buyerId;
        private long productId;
        private LocalDate dateOfSale;
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

        public Sale build(){
            return new Sale(this);
        }

    }
}
