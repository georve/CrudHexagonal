package com.salesmanagement.salesmagament.application.ports.output;

import com.salesmanagement.salesmagament.domain.model.Prices;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PricePersistencePort {


    List<Prices> findByCriteria(Map<String, String> criteria);

    Prices save(Prices priceToSave);

    Optional<Prices> findById(String priceList);
}
