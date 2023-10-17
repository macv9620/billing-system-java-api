package com.macv.billing.persistence.repository.view;

import com.macv.billing.persistence.entity.view.SalesReportViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesReportViewRepository extends JpaRepository<SalesReportViewEntity, Integer> {
    @Query(value = "SELECT k.id_transaction," +
            "k.id_product," +
            "k.product_name," +
            "k.id_invoice," +
            "i.payment_method," +
            "ip.product_total," +
            "i.id_customer," +
            "c.name," +
            "k.transaction_type," +
            "k.initial_stock," +
            "k.transaction_quantity," +
            "k.final_stock," +
            "k.transaction_date" +
            " FROM sales_report k" +
            " JOIN invoice i ON i.id_invoice = k.id_invoice" +
            " JOIN customer c ON i.id_customer = c.id_customer" +
            " JOIN invoice_product ip ON k.id_product = ip.id_product AND ip.id_invoice = k.id_invoice" +
            " WHERE k.id_product = :productId" +
            " ORDER BY k.id_transaction ASC", nativeQuery = true)
    List<SalesReportViewEntity> getSalesReport(@Param("productId") int productId);
}
