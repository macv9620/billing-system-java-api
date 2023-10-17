package com.macv.billing.persistence.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "brand")
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_brand")
    @Schema(description = "Identificador de marca", requiredMode = Schema.RequiredMode.AUTO, example = "4")
    private int brandId;

    @Schema(description = "Nombre de la marca", requiredMode = Schema.RequiredMode.REQUIRED, example = "Asus")
    private String name;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
