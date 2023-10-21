package com.macv.billing.persistence.entity.view;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Identificador autogenerado de la transacción", requiredMode = Schema.RequiredMode.AUTO,
            example = "57", accessMode = Schema.AccessMode.READ_ONLY)
    int transactionId;

    @Column(name = "id_product")
    @Schema(description = "Identificador o id del producto", requiredMode = Schema.RequiredMode.AUTO,
            example = "14", accessMode = Schema.AccessMode.READ_ONLY)
    private int productId;

    @Column(name = "product_name")
    @Schema(description = "Nombre del producto", requiredMode = Schema.RequiredMode.AUTO,
            example = "Keyboard", accessMode = Schema.AccessMode.READ_ONLY)
    private String productName;

    @Column(name = "id_invoice")
    @Schema(description = "Serial o identificador de la factura de compra", requiredMode = Schema.RequiredMode.AUTO,
            example = "100", accessMode = Schema.AccessMode.READ_ONLY)
    private int invoiceId;

    @Column(name = "payment_method")
    @Schema(description = "Método de pago", requiredMode = Schema.RequiredMode.AUTO,
            example = "CASH", accessMode = Schema.AccessMode.READ_ONLY)
    private String paymentMethod;

    @Column(name = "product_total")
    @Schema(description = "Valor total de la compra para el producto en esa transacción, cantidad de producto * precio unitario", requiredMode = Schema.RequiredMode.AUTO,
            example = "429.36", accessMode = Schema.AccessMode.READ_ONLY)
    private double productTotal;

    @Column(name = "id_user")
    @Schema(description = "Identificación o documento del cliente que realizó la compra", requiredMode = Schema.RequiredMode.AUTO,
            example = "875599", accessMode = Schema.AccessMode.READ_ONLY)
    private String userId;

    @Column(name = "name")
    @Schema(description = "Nombre del cliente que realizó la compra", requiredMode = Schema.RequiredMode.AUTO,
            example = "Carolina Giraldo", accessMode = Schema.AccessMode.READ_ONLY)
    private String userName;

    @Column(name = "transaction_type")
    @Schema(description = "Indica si la transacción corresponde a una salida [OUT] o entrada [IN] de inventario", requiredMode = Schema.RequiredMode.AUTO,
            example = "OUT", accessMode = Schema.AccessMode.READ_ONLY)
    private String transactionType;

    @Column(name = "initial_stock")
    @Schema(description = "Cantidad del producto en stock antes de la compra", requiredMode = Schema.RequiredMode.AUTO,
            example = "60", accessMode = Schema.AccessMode.READ_ONLY)
    private int initialStock;

    @Column(name = "transaction_quantity")
    @Schema(description = "Cantidad del producto comprada en la transacción", requiredMode = Schema.RequiredMode.AUTO,
            example = "20", accessMode = Schema.AccessMode.READ_ONLY)
    private int transactionQuantity;

    @Column(name = "final_stock")
    @Schema(description = "Cantidad del producto en stock tras hacer el descuento de la cantidad comprada", requiredMode = Schema.RequiredMode.AUTO,
            example = "40", accessMode = Schema.AccessMode.READ_ONLY)
    private int finalStock;

    @Column(name = "transaction_date")
    @Schema(description = "Fecha de compra del producto", requiredMode = Schema.RequiredMode.AUTO,
            example = "2023-10-16T21:16:30.530497", accessMode = Schema.AccessMode.READ_ONLY)
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", initialStock=" + initialStock +
                ", transactionQuantity=" + transactionQuantity +
                ", finalStock=" + finalStock +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
