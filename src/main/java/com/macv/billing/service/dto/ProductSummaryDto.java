package com.macv.billing.service.dto;

public class ProductSummaryDto {
    private int productId;
    private int productQuantity;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "ProductSummaryDto{" +
                "productId=" + productId +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
