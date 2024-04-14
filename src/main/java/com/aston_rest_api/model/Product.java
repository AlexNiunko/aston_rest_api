package com.aston_rest_api.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Product extends AbstractEntity {
     private String productName;
     private double productPrice;
     private int amount;
     private ProductDescription description;
     private List<User> buyers;

     private Product(ProductBuilder builder) {
          super(builder.idProduct);
          this.productName = builder.productName;
          this.productPrice = builder.productPrice;
          this.amount = builder.amount;
          this.buyers=builder.buyers;
          this.description= builder.description;
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

     public ProductDescription getDescription() {
          return description;
     }

     public void setDescription(ProductDescription description) {
          this.description = description;
     }

     public List<User> getBuyers() {
          return buyers;
     }

     public void setBuyers(List<User> buyers) {
          this.buyers = buyers;
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (!(o instanceof Product)) return false;
          Product product = (Product) o;
          return Double.compare(product.getProductPrice(), getProductPrice()) == 0 && getAmount() == product.getAmount() && getProductName().equals(product.getProductName());
     }

     @Override
     public int hashCode() {
          return Objects.hash(getProductName(), getProductPrice(), getAmount());
     }

     @Override
     public String toString() {
          final StringBuilder sb = new StringBuilder("Product{");
          sb.append("productName='").append(productName).append('\'');
          sb.append(", productPrice=").append(productPrice);
          sb.append(", amount=").append(amount);
          sb.append(", description=").append(description);
          sb.append(", buyers=").append(buyers);
          sb.append(", id=").append(id);
          sb.append('}');
          return sb.toString();
     }

     public static class ProductBuilder {
          private long idProduct;
          private String productName;
          private double productPrice;
          private int amount;
          private ProductDescription description;
          private List<User>buyers;

          public ProductBuilder(long idProduct) {
               this.idProduct = idProduct;
          }
          public ProductBuilder() {

          }
          public ProductBuilder setProductName(String productName) {
               this.productName = productName;
               return this;
          }
          public ProductBuilder setProductPrice(double productPrice) {
               this.productPrice = productPrice;
               return this;
          }
          public ProductBuilder setAmount(int amount) {
               this.amount = amount;
               return this;
          }
          public ProductBuilder setBuyers(List<User>buyers) {
               this.buyers = buyers;
               return this;
          }
          public ProductBuilder setProductDescription(ProductDescription description){
               this.description=description;
               return this;
          }
          public Product build(){
               return new Product(this);
          }

     }
}
