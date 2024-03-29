package com.aston_rest_api.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SaleDto extends AbstractDto {
    private long buyerId;
    private long productId;
    private LocalDateTime dateOfSale;
    private int amountSale;

    private SaleDto(SaleDtoBuilder builder) {
        super(builder.idSale);
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SaleDto{");
        sb.append("buyerId=").append(buyerId);
        sb.append(", productId=").append(productId);
        sb.append(", dateOfSale=").append(dateOfSale);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    public static class SaleDtoBuilder{
        private long idSale;
        private long buyerId;
        private long productId;
        private LocalDateTime dateOfSale;
        private int amountSale;

        public SaleDtoBuilder(long idSale) {
            this.idSale = idSale;
        }
        public SaleDtoBuilder setBuyerId(long buyerId) {
            this.buyerId = buyerId;
            return this;
        }
        public SaleDtoBuilder setProductId(long productId) {
            this.productId = productId;
            return this;
        }
        public SaleDtoBuilder setAmountSale(int amountSale) {
            this.amountSale = amountSale;
            return this;
        }
        public SaleDtoBuilder setDateOfSale(LocalDateTime dateOfSale) {
//            String date=dateOfSale.split("\\s")[0];
//            String year=date.split("-")[0];
//            String month=date.split("-")[1];
//            String day=date.split("-")[2];

//            LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day))
            this.dateOfSale =dateOfSale ;
            return this;
        }
        public SaleDto build(){
            return new SaleDto(this);
        }

    }
}
