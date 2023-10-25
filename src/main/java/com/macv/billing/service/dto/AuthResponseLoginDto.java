package com.macv.billing.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthResponseLoginDto {
    @Schema(description = "Identificación de usuario",
            requiredMode = Schema.RequiredMode.AUTO, example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3ODk2NTciLCJpc3MiOiJzcHJpbmctYmlsbGluZy1hcGkiLCJleHAiOjE2OTgyMDczODUsImlhdCI6MTY5ODIwNTU4NX0.Mk2Q828PrMNkjbgO2PQ0yVThj7iyHZIrQfEYWDWkkV0")
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
