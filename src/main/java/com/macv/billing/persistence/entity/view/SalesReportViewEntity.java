package com.macv.billing.persistence.entity.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity(name = "sales_report_view")
@Immutable
public class SalesReportViewEntity {
    @Id
    @Column(name = "id_transaction")
    int transactionId;

    @Column(name = "id_product")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "id_invoice")
    private int invoiceId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "product_total")
    private double productTotal;

    @Column(name = "id_customer")
    private String customerId;

    @Column(name = "name")
    private String customerName;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "initial_stock")
    private int initialStock;

    @Column(name = "transaction_quantity")
    private int transactionQuantity;

    @Column(name = "final_stock")
    private int finalStock;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(double productTotal) {
        this.productTotal = productTotal;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(int initialStock) {
        this.initialStock = initialStock;
    }

    public int getTransactionQuantity() {
        return transactionQuantity;
    }

    public void setTransactionQuantity(int transactionQuantity) {
        this.transactionQuantity = transactionQuantity;
    }

    public int getFinalStock() {
        return finalStock;
    }

    public void setFinalStock(int finalStock) {
        this.finalStock = finalStock;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "SalesReportView{" +
                "transactionId=" + transactionId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", invoiceId=" + invoiceId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", productTotal=" + productTotal +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", initialStock=" + initialStock +
                ", transactionQuantity=" + transactionQuantity +
                ", finalStock=" + finalStock +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
