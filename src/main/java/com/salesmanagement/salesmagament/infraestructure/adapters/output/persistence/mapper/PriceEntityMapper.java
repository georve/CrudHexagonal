package com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.mapper;


import com.salesmanagement.salesmagament.domain.model.Prices;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.PricePersistenceAdapter;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses={PricePersistenceAdapter.class})
public interface PriceEntityMapper {

    @Mapping(source = "startDate", target = "startDate", dateFormat = "YYYY-HH-DD HH:mm:ss")
    @Mapping(source = "endDate", target = "endDate", dateFormat = "YYYY-HH-DD HH:mm:ss")
    @Mapping(source ="id", target="priceList")
    Prices toPrices(PriceEntity priceEntity);
    List<Prices> toPricesList(List<PriceEntity> priceEntities);
}
