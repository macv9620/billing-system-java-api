package com.macv.billing.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice")
    private int invoiceId;

    @Column(name = "id_customer")
    private String customerId;

    @CreationTimestamp
    @Column(name = "invoice_date")
    private LocalDateTime invoiceDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "user_comment")
    private String userComment;

    @Column(name = "total")
    private Double invoiceTotal;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER)
    private List<InvoiceProductEntity> products;

    public void addNewInvoiceProductEntity(InvoiceProductEntity product){
        this.products.add(product);
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
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

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public List<InvoiceProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<InvoiceProductEntity> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "invoiceId=" + invoiceId +
                ", customerId='" + customerId + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", userComment='" + userComment + '\'' +
                ", invoiceTotal=" + invoiceTotal +
                ", products=" + products +
                '}';
    }
}