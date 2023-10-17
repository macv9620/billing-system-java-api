package com.macv.billing.persistence.repository;

import com.macv.billing.persistence.entity.SalesReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesReportRepository extends JpaRepository<SalesReportEntity, Integer> {

}
