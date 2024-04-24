package com.salesmanagement.salesmagament.application.ports.input;

import com.salesmanagement.salesmagament.domain.model.Prices;

import java.util.List;
import java.util.Map;

public interface PriceServicePort {

    List<Prices> findByCriteria(Map<String,String> criteria);
}
