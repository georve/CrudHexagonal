package com.salesmanagement.salesmagament.application.services;

import com.salesmanagement.salesmagament.application.ports.input.PriceServicePort;
import com.salesmanagement.salesmagament.application.ports.output.PricePersistencePort;
import com.salesmanagement.salesmagament.domain.exception.PriceNotFoundException;
import com.salesmanagement.salesmagament.domain.model.Prices;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class PricesService implements PriceServicePort {

    private  PricePersistencePort pricePersistencePort;


    public PricesService(PricePersistencePort pricePersistencePort) {
        this.pricePersistencePort = pricePersistencePort;
    }

    @Override
    public List<Prices> findByCriteria(Map<String, String> criteria) {
        return this.pricePersistencePort.findByCriteria(criteria);
    }

    @Override
    public Prices save(Prices priceToSave) {
        return this.pricePersistencePort.save(priceToSave);
    }

    @Override
    public Prices findById(String priceList) {
        return this.pricePersistencePort.findById(priceList)
                .orElseThrow(PriceNotFoundException::new);
    }

    @Override
    public Prices update(String priceListId, Prices priceToUpdate) {
        return this.pricePersistencePort.findById(priceListId)
                .map(price -> {
                    price.setBrandId(priceToUpdate.getBrandId());
                    price.setPriority(priceToUpdate.getPriority());
                    price.setStartDate(priceToUpdate.getStartDate());
                    price.setEndDate(priceToUpdate.getEndDate());
                    price.setCurrencyCode(priceToUpdate.getCurrencyCode());
                    price.setPrice(priceToUpdate.getPrice());
                    return pricePersistencePort.save( price);
                })
                .orElseThrow(PriceNotFoundException::new);
    }
}
