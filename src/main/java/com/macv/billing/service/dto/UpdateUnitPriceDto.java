package com.macv.billing.service.dto;

public class UpdateUnitPriceDto {
    private int productId;
    private double newUnitPrice;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getNewUnitPrice() {
        return newUnitPrice;
    }

    public void setNewUnitPrice(double newUnitPrice) {
        this.newUnitPrice = newUnitPrice;
    }

    @Override
    public String toString() {
        return "UpdateUnitPriceDto{" +
                "productId=" + productId +
                ", newUnitPrice=" + newUnitPrice +
                '}';
    }
}
