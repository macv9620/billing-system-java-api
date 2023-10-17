package com.macv.billing.persistence.repository.view;

import com.macv.billing.persistence.entity.view.ProductStockAndPriceViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductStockAndPriceViewRepository extends JpaRepository<ProductStockAndPriceViewEntity, Integer> {
    @Query(value = "SELECT p.id_product, p.stock, p.unit_price" +
            " FROM product p WHERE p.id_product = :productId",
            nativeQuery = true)
    ProductStockAndPriceViewEntity getById(@Param("productId") int productId);
}
