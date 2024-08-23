package com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.repository;

import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {


    @Query("""
           select a from PriceEntity a 
           where a.brandId = :brandId 
           and a.productId = :productId
           and :appDate between a.startDate
           and a.endDate
           order by a.priority desc
           LIMIT 1
    """)
    List<PriceEntity> findPreciosByCriterias(
        @Param("brandId") Integer brandId,
        @Param("productId") Integer productId,
        @Param("appDate") LocalDateTime appDate
    );
}
