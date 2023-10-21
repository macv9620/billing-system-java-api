package com.macv.billing.persistence.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name="\"user\"")
public class UserEntity {

    @Id
    @Column(name = "id_user")
    @Schema(description = "Identificación o documento del usuario",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "789657")
    private String userId;

    @Schema(description = "Nombre completo del usuario",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "Maria Córdoba")
    private String name;

    @Schema(description = "Dirección de correo electrónico del usuario",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "maria@gmail.com")
    private String email;


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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
