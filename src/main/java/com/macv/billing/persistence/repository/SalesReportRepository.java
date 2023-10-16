package com.macv.billing.persistence.repository;

import com.macv.billing.persistence.entity.SalesReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesReportRepository extends JpaRepository<SalesReportEntity, Integer> {
}
