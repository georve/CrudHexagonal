package com.salesmanagement.salesmagament.application.ports.output;

import com.salesmanagement.salesmagament.domain.model.Prices;

import java.util.List;
import java.util.Optional;

public interface PricePersistencePort {

    Optional<Prices> findById(Long id);
    List<Prices> findAll();
    Prices save(Prices prices);
    void deleteById(Long id);
}
