package com.macv.billing.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales_report")
public class SalesReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private int transactionId;

    @Column(name = "id_product")
    private int productId;

    @Column(name = "id_invoice")
    private int invoiceId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "initial_stock")
    private int initialStock;

    @Column(name = "transaction_quantity")
    private int transactionQuantity;

    @Column(name = "final_stock")
    private int finalStock;

    @CreationTimestamp
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

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public String toString() {
        return "KardexEntity{" +
                "transactionId=" + transactionId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", initialStock=" + initialStock +
                ", transactionQuantity=" + transactionQuantity +
                ", finalStock=" + finalStock +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
