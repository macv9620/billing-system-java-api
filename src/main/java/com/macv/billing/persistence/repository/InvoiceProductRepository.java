package com.macv.billing.persistence.repository;

import com.macv.billing.persistence.entity.InvoiceProductEntity;
import com.macv.billing.persistence.entity.compositeKey.InvoiceProductPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProductEntity, InvoiceProductPK> {

}
