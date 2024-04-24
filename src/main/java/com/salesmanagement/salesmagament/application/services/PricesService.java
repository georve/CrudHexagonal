package com.salesmanagement.salesmagament.application.services;

import com.salesmanagement.salesmagament.application.ports.input.PriceServicePort;
import com.salesmanagement.salesmagament.application.ports.output.PricePersistencePort;
import com.salesmanagement.salesmagament.domain.model.Prices;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.mapper.PriceEntityMapper;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
