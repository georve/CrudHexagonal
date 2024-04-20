package com.salesmanagement.salesmagament.application.ports.input;

import com.salesmanagement.salesmagament.domain.model.Prices;

import java.util.List;
import java.util.Map;

public interface PriceServicePort {
    Prices findById(Long id);
    List<Prices> findAll();
    Prices save(Prices price);
    Prices update(Long id, Prices prices);
    List<Prices> findByCriteria(Map<String,String> criteria);
}
