package com.macv.billing.persistence.entity.compositeKey;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;

public class InvoiceProductPK implements Serializable {
    @Id
    @Column(name = "id_invoice")
    private int invoiceId;

    @Id
    @Column(name = "id_product")
    private int productId;

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
}
