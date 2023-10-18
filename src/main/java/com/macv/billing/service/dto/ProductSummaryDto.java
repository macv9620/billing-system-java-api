package com.macv.billing.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductSummaryDto {
    @Schema(description = "Identificación del producto comprado",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private int productId;

    @Schema(description = "Cantidad comprada del producto",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "60")
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
