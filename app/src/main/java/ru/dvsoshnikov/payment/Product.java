package ru.dvsoshnikov.payment;

import java.math.BigDecimal;

public class Product {

    private BigDecimal coast; // стоимость товара
    private String productName;  //наименование товара
    private String productDescription;  //описание товара

    public Product(BigDecimal coast, String productName, String productDescription) {
        this.coast = coast;
        this.productName = productName;
        this.productDescription = productDescription;
    }

    public BigDecimal getCoast() {
        return coast;
    }

    public void setCoast(BigDecimal coast) {
        this.coast = coast;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
