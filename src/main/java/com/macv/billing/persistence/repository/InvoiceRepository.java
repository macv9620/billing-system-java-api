package com.macv.billing.persistence.repository;

import com.macv.billing.persistence.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {
    List<InvoiceEntity> findAllByCustomerIdOrderByInvoiceIdDesc(String customerId);

    List<InvoiceEntity> findAllByOrderByInvoiceIdDesc();
}
