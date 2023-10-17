package com.macv.billing.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateStockDto {
    @Schema(description = "Identificador del producto", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1")
    private int productId;

    @Schema(description = "Nuevo stock", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "150")
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
