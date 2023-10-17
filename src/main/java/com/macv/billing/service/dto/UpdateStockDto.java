package com.macv.billing.service.dto;

public class UpdateStockDto {
    private int productId;
    private int newStock;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getNewStock() {
        return newStock;
    }

    public void setNewStock(int newStock) {
        this.newStock = newStock;
    }

    @Override
    public String toString() {
        return "UpdateStockDto{" +
                "productId=" + productId +
                ", newStock=" + newStock +
                '}';
    }
}
