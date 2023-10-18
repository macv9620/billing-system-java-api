package com.macv.billing.persistence.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @Column(name = "id_customer")
    @Schema(description = "Identificación o documento del cliente",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "789657")
    private String customerId;

    @Schema(description = "Nombre completo del cliente",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "Maria Córdoba")
    private String name;

    @Schema(description = "Dirección de correo electrónico del cliente",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "maria@gmail.com")
    private String email;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
