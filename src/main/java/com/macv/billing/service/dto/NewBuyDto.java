package com.macv.billing.service.dto;

import java.util.List;

public class NewBuyDto {
    String customerId;
    String paymentMethod;
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
