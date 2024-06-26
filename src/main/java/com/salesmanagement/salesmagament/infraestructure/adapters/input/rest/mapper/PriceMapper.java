package com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.mapper;

import com.salesmanagement.salesmagament.domain.model.Prices;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.model.PriceCreateDtoRequest;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.model.PriceDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PriceMapper {

    @Mapping(source = "priceList",target="id")
    PriceDtoResponse toPriceDtoResponse(Prices prices);

    List<PriceDtoResponse> toPriceDtoResponseList(List<Prices> pricesList);

    Prices toPrice(PriceCreateDtoRequest request);
}
