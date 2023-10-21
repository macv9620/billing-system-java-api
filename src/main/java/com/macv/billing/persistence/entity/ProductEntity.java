package com.macv.billing.persistence.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    @Schema(description = "Identificador del producto", requiredMode = Schema.RequiredMode.AUTO,
            example = "1",  accessMode = Schema.AccessMode.READ_ONLY)
    private int productId;

    @Schema(description = "Nombre del producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "Mouse")
    private String name;

    @Schema(description = "Descripción del producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "Black, wireless")
    private String description;

    @Schema(description = "Precio unitario del producto, debe ser >0", requiredMode = Schema.RequiredMode.REQUIRED, example = "20.6")
    @Column(name = "unit_price")
    private double unitPrice;

    @Schema(description = "Identificador válido de categoria", requiredMode = Schema.RequiredMode.REQUIRED, example = "4")
    @Column(name = "id_category")
    private int categoryId;

    @Schema(description = "Identificador válido de marca", requiredMode = Schema.RequiredMode.REQUIRED, example = "9")
    @Column(name = "id_brand")
    private int brandId;

    @Schema(description = "Unidades en stock del producto, debe ser >=0", requiredMode = Schema.RequiredMode.REQUIRED, example = "150")
    private int stock;

    @Schema(description = "Estado del producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @Column(name = "active")
    private boolean isActive;

    @CreationTimestamp
    @Column(name = "creation_date")
    @Schema(description = "Hora de creación del producto, autogenerada por la base de datos",
            requiredMode = Schema.RequiredMode.AUTO, accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "id_category", insertable = false, updatable = false)
    @Schema(description = "Detalle de la categoria",
            requiredMode = Schema.RequiredMode.AUTO,  accessMode = Schema.AccessMode.READ_ONLY, hidden = true)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "id_brand", insertable = false, updatable = false)
    @Schema(description = "Detalle de la marca",
            requiredMode = Schema.RequiredMode.AUTO,  accessMode = Schema.AccessMode.READ_ONLY, hidden = true)
    private BrandEntity brand;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }



    @Override
    public String toString() {
        return "ProductEntity{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", stock=" + stock +
                ", isActive=" + isActive +
                ", creationDate=" + creationDate +
                ", category=" + category +
                ", brand=" + brand +
                '}';
    }
}
