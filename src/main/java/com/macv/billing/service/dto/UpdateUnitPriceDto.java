package com.macv.billing.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateUnitPriceDto {

    @Schema(description = "Identificador del producto", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1")
    private int productId;

    @Schema(description = "Nuevo valor unitario", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "20.6")
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
