package com.macv.billing.persistence.repository;

import com.macv.billing.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query(value = "UPDATE product " +
            "SET stock = :updatedStock " +
            "WHERE id_product = :productId",
            nativeQuery = true)
    @Modifying
    int stockOut(@Param("productId") int productId, @Param("updatedStock") int updatedStock);

    @Modifying
    @Query(value = "UPDATE product " +
            "SET stock = :updatedStock " +
            "WHERE id_product = :productId",
            nativeQuery = true)
    int stockUpdate(@Param("productId") int productId, @Param("updatedStock") int updatedStock);

    @Modifying
    @Query(value = "UPDATE product " +
            "SET unit_price = :updatedPrice " +
            "WHERE id_product = :productId",
            nativeQuery = true)
    int unitPriceUpdate(@Param("productId") int productId, @Param("updatedPrice") double updatedPrice);

    List<ProductEntity> findAllByOrderByProductIdAsc();
}
