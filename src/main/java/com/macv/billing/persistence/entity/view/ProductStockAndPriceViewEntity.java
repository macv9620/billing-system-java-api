package com.macv.billing.persistence.entity.view;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "product_stock_price_view")
public class ProductStockAndPriceViewEntity {

    @Id
    @Column(name = "id_product")
    @Schema(description = "Identificador del producto", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int productId;

    @Schema(description = "Stock del producto", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "150", accessMode = Schema.AccessMode.READ_ONLY)
    private int stock;

    @Schema(description = "Precio unitario del producto", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "33.68", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "unit_price")
    private double unitPrice;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}
