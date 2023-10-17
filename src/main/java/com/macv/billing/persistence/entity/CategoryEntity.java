package com.macv.billing.persistence.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    @Schema(description = "Identificador de categoria", requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private int categoryId;

    @Schema(description = "Nombre de categoria", requiredMode = Schema.RequiredMode.REQUIRED, example = "Shoes")
    private String name;

    @Schema(description = "Estado de la categoria", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @Column(name = "active")
    private boolean isActive;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
