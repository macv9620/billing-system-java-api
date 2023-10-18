package com.macv.billing.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class NewBuyDto {
    @Schema(description = "Identificación o documento del cliente",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "879545")
    String customerId;

    @Schema(description = "Tipo método de pago CASH o CREDIT_CARD",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "CREDIT_CARD")
    String paymentMethod;

    @Schema(description = "Comentario de usuario para la compra",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "As soon as posible")
    String userComment;

    List<ProductSummaryDto> products;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public List<ProductSummaryDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSummaryDto> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "NewBuyDto{" +
                "customerId='" + customerId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", userComment='" + userComment + '\'' +
                ", products=" + products +
                '}';
    }
}
