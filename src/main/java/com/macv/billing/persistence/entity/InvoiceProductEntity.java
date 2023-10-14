package com.macv.billing.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macv.billing.persistence.entity.compositeKey.InvoiceProductPK;
import jakarta.persistence.*;

@Entity
@Table(name = "invoice_product")
public class InvoiceProductEntity {

    @EmbeddedId
    private InvoiceProductPK id;

    @Column(name = "quantity")
    private int productQuantity;

    @Column(name = "product_total")
    private double productTotalPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_invoice", insertable = false, updatable = false)
    private InvoiceEntity invoice;

    public InvoiceProductPK getId() {
        return id;
    }

    public void setId(InvoiceProductPK id) {
        this.id = id;
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
}
