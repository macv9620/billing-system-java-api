package com.macv.billing.persistence.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Consecutivo autogenerado que identifica la factura",
            requiredMode = Schema.RequiredMode.AUTO, example = "40", accessMode = Schema.AccessMode.READ_ONLY)
    private int invoiceId;


    @Column(name = "id_customer")
    @Schema(description = "Identificador de cliente",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "875599")
    private String customerId;

    @CreationTimestamp
    @Column(name = "invoice_date")
    @Schema(description = "Fecha de generación de la factura",
            requiredMode = Schema.RequiredMode.AUTO, example = "2023-10-16T21:16:30.485521", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime invoiceDate;

    @Column(name = "payment_method")
    @Schema(description = "Tipo método de pago CASH o CREDIT_CARD",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "CREDIT_CARD")
    private String paymentMethod;

    @Column(name = "user_comment")
    @Schema(description = "Comentario del cliente para la orden",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "As soon as posible")
    private String userComment;

    @Column(name = "total")
    @Schema(description = "Valor total de todos los productos y cantidades de la orden",
            requiredMode = Schema.RequiredMode.AUTO, example = "5489.36", accessMode = Schema.AccessMode.READ_ONLY)
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
