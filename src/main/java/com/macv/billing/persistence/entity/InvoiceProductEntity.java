package com.macv.billing.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "invoice_product")
public class InvoiceProductEntity {

    @Id
    @Column(name = "id_invoice")
    private int invoiceId;

    @Id
    @Column(name = "id_product")
    private int productId;

    @Column(name = "quantity")
    private int productQuantity;

    @Column(name = "product_total")
    private double productTotalPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_invoice", insertable = false, updatable = false)
    private InvoiceEntity invoice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    private ProductEntity product;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(double productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
