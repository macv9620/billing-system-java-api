package com.macv.billing.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthRequestLoginDto {
    @Schema(description = "Identificación de usuario",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "789657")
    private String username;

    @Schema(description = "Clave de usuario",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "admin1234")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthRequestLoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
