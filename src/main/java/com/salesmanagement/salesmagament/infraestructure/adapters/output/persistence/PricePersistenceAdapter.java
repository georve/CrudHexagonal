package com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence;

import com.salesmanagement.salesmagament.application.ports.output.PricePersistencePort;
import com.salesmanagement.salesmagament.domain.exception.PriceBadRequestException;
import com.salesmanagement.salesmagament.domain.model.Prices;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.mapper.PriceEntityMapper;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model.PriceEntity;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class PricePersistenceAdapter implements PricePersistencePort {

    private PriceRepository priceRepository;
    private PriceEntityMapper mapper;

    public PricePersistenceAdapter(PriceRepository priceRepository, PriceEntityMapper mapper) {
        this.priceRepository = priceRepository;
        this.mapper = mapper;
    }
    @Override
    public List<Prices> findByCriteria(Map<String, String> criteria) {

        String productId = criteria.get("productId");
        String brandId = criteria.get("brandId");
        String appDate = criteria.get("appDate");

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         LocalDateTime dateTime = LocalDateTime.parse(appDate, formatter);


        List<PriceEntity> prices=priceRepository.findPreciosByCriterias(Integer.parseInt(brandId),
        Integer.parseInt(productId),
        dateTime);

        Optional<PriceEntity> priceOp=prices.stream().sorted((x, y)->x.getPriority().compareTo(y.getPriority())).findFirst();
        List<Prices> result=new ArrayList<>();
        priceOp.ifPresent(priceEntity->{
            result.add(mapper.toPrices(priceEntity));
        });

        return result;
    }

    @Override
    public Prices save(Prices priceToSave) {
        PriceEntity entity=this.priceRepository.save(mapperToEntity(priceToSave));
        return this.mapper.toPrices(entity);
    }

    private PriceEntity mapperToEntity(Prices prices){
        PriceEntity priceEntity = new PriceEntity();
       try {

           priceEntity.setProductId(prices.getProductId());
           priceEntity.setBrandId(prices.getBrandId());
           priceEntity.setPrice(prices.getPrice());
           priceEntity.setPriority(prices.getPriority());
           priceEntity.setCurrencyCode(priceEntity.getCurrencyCode());
           priceEntity.setStartDate(Timestamp.valueOf(prices.getStartDate()));
           priceEntity.setEndDate(Timestamp.valueOf(prices.getEndDate()));

       }catch( IllegalArgumentException ex){
           throw new PriceBadRequestException(ex.getMessage());
       }
        return priceEntity;
    }


}
