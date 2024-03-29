package com.aston_rest_api.controller.dto;

import com.aston_rest_api.model.User;

import java.util.Map;

public class ProductDto extends AbstractDto {
     private String productName;
     private double productPrice;
     private int amount;
     private Map<Long, UserDto>buyers;

     private ProductDto(ProductDtoBuilder builder) {
          super(builder.idProduct);
          this.productName = builder.productName;
          this.productPrice = builder.productPrice;
          this.amount = builder.amount;
     }

     public String getProductName() {
          return productName;
     }

     public void setProductName(String productName) {
          this.productName = productName;
     }

     public double getProductPrice() {
          return productPrice;
     }

     public void setProductPrice(double productPrice) {
          this.productPrice = productPrice;
     }

     public int getAmount() {
          return amount;
     }

     public void setAmount(int amount) {
          this.amount = amount;
     }

     @Override
     public String toString() {
          final StringBuilder sb = new StringBuilder("ProductDto{");
          sb.append("productId='").append(super.id).append('\'');
          sb.append("productName='").append(productName).append('\'');
          sb.append(", productPrice=").append(productPrice);
          sb.append(", amount=").append(amount);
          sb.append('}');
          return sb.toString();
     }

     public static class ProductDtoBuilder{
          private long idProduct;
          private String productName;
          private double productPrice;
          private int amount;
          private Map<Long, UserDto>buyers;

          public ProductDtoBuilder(long idProduct) {
               this.idProduct = idProduct;
          }
          public ProductDtoBuilder setProductName(String productName) {
               this.productName = productName;
               return this;
          }
          public ProductDtoBuilder setProductPrice(double productPrice) {
               this.productPrice = productPrice;
               return this;
          }
          public ProductDtoBuilder setAmount(int amount) {
               this.amount = amount;
               return this;
          }
          public ProductDtoBuilder setBuyers(Map<Long,UserDto>buyers) {
               this.buyers = buyers;
               return this;
          }
          public ProductDto build(){
               return new ProductDto(this);
          }

     }
}
