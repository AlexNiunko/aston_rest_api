package com.aston_rest_api.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class SaleDto {
    private long saleId;
    private long buyerId;
    private long productId;
    private String dateOfSale;
    private String amountOfSale;

    private SaleDto(SaleDtoBuilder saleDtoBuilder) {
        this.saleId = saleDtoBuilder.saleId;
        this.buyerId = saleDtoBuilder.buyerId;
        this.productId = saleDtoBuilder.productId;
        this.dateOfSale = saleDtoBuilder.dateOfSale;
        this.amountOfSale = saleDtoBuilder.amountOfSale;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
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

    public String getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(String dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public String getAmountOfSale() {
        return amountOfSale;
    }

    public void setAmountOfSale(String amountOfSale) {
        this.amountOfSale = amountOfSale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaleDto)) return false;
        SaleDto saleDto = (SaleDto) o;
        return getSaleId() == saleDto.getSaleId() && getBuyerId() == saleDto.getBuyerId() && getProductId() == saleDto.getProductId() && getDateOfSale().equals(saleDto.getDateOfSale()) && getAmountOfSale().equals(saleDto.getAmountOfSale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSaleId(), getBuyerId(), getProductId(), getDateOfSale(), getAmountOfSale());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SaleDto{");
        sb.append("saleId=").append(saleId);
        sb.append(", buyerId=").append(buyerId);
        sb.append(", productId=").append(productId);
        sb.append(", dateOfSale='").append(dateOfSale).append('\'');
        sb.append(", amountOfSale='").append(amountOfSale).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class SaleDtoBuilder {
        private long saleId;
        private long buyerId;
        private long productId;
        private String dateOfSale;
        private String amountOfSale;

        public SaleDtoBuilder(long saleId) {
            this.saleId = saleId;
        }

        public SaleDtoBuilder setBuyerId(long buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public SaleDtoBuilder setProductId(long productId) {
            this.productId = productId;
            return this;
        }

        public SaleDtoBuilder setDateOfSale(String dateOfSale) {
            this.dateOfSale = dateOfSale;
            return this;
        }


        public SaleDtoBuilder setAmountOfSale(String amountOfSale) {
            this.amountOfSale = amountOfSale;
            return this;
        }

        public SaleDto build() {
            return new SaleDto(this);
        }
    }
}
