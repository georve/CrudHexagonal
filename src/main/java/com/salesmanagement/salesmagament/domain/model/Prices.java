package com.salesmanagement.salesmagament.domain.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prices {
    private Integer brandId;
    private String  startDate;
    private String  endDate;
    private String  priceList;
    private Integer productId;
    private Double  price;
    private Integer priority;
}
