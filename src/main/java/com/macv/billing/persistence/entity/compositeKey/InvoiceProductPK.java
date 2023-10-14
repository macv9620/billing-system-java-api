package com.macv.billing.persistence.entity.compositeKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class InvoiceProductPK implements Serializable {

    @JsonIgnore
    @Column(name = "id_invoice")
    private int invoiceId;
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
