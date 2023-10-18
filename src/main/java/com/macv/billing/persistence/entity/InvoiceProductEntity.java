package com.macv.billing.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macv.billing.persistence.entity.compositeKey.InvoiceProductPK;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "invoice_product")
@IdClass(InvoiceProductPK.class)
public class InvoiceProductEntity {

    @Id
    @Column(name = "id_invoice")
    @Schema(description = "Serial o identificador de la factura",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "40")
    private int invoiceId;

    @Id
    @Column(name = "id_product")
    @Schema(description = "Identificador del producto",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "15")
    private int productId;

    @Column(name = "quantity")
    @Schema(description = "Cantidad vendida o comprada del producto en unidades",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "60")
    private int productQuantity;

    @Column(name = "product_total")
    @Schema(description = "Valor total de item, cantidad multiplicado por precio unitario",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "2150.66")
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
