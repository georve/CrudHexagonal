package com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "price",
        indexes = {
                @Index(name = "idx_price_brand_product_dates", columnList = "BRAND_ID, PRODUCT_ID, START_DATE, END_DATE"),
                @Index(name = "idx_price_priority", columnList = "PRIORITY")
        }
)
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="BRAND_ID")
    private Integer brandId;
    @Column(name="START_DATE")
    private Timestamp  startDate;
    @Column(name="END_DATE")
    private Timestamp  endDate;
    @Column(name="PRODUCT_ID")
    private Integer productId;
    @Column(name="PRICE")
    private Double  price;
    @Column(name="PRIORITY")
    private Integer priority;
    @Column(name="CURRENCY_CODE")
    private String currencyCode;
}
