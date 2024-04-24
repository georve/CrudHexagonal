package com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceDtoResponse {
    private Integer brandId;
    private String  startDate;
    private String  endDate;
    private Integer productId;
    private Double  price;
}
