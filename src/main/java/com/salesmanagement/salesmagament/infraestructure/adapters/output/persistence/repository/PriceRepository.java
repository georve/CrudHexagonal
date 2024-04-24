package com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.repository;

import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.time.LocalDateTime;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
    

    @Query("""
        select a from PriceEntity a 
        where a.brandId = :brandId 
        and a.productId = :productId
        and a.startDate <= :appDate
        and a.endDate >= :appDate
        """)
    List<PriceEntity> findPreciosByCriterias(
        @Param("brandId") Integer brandId,
        @Param("productId") Integer productId,
        @Param("appDate") LocalDateTime appDate
    );
}
