package com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.mapper;


import com.salesmanagement.salesmagament.domain.model.Prices;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.PricePersistenceAdapter;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;


import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses={PricePersistenceAdapter.class})
public interface PriceEntityMapper {

    @Mapping(source = "startDate", target = "startDate", qualifiedByName="timestampToString")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName="timestampToString")
    @Mapping(source ="id", target="priceList")
    Prices toPrices(PriceEntity priceEntity);
    List<Prices> toPricesList(List<PriceEntity> priceEntities);


    @Named("timestampToString")
    default String stringToLocalDate(Timestamp date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date.toLocalDateTime());
    }

}
