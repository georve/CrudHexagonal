package com.salesmanagement.salesmagament.infraestructure.adapters.input.rest;

import com.salesmanagement.salesmagament.application.ports.input.PriceServicePort;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.mapper.PriceMapper;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.model.PriceDtoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prices")
public class PriceControllerRestAdapter {

    private PriceServicePort port;
    private PriceMapper restMapper;


    public PriceControllerRestAdapter(PriceServicePort port, PriceMapper restMapper) {
        this.port = port;
        this.restMapper = restMapper;
    }

    @GetMapping()
    public ResponseEntity<List<PriceDtoResponse>> searchPricesByCriteria(@RequestParam Map<String,String> criteria){
       List<PriceDtoResponse> response= restMapper.toPriceDtoResponseList(
                port.findByCriteria(criteria));
        HttpStatus status=(response!=null && !response.isEmpty())? HttpStatus.OK:HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }
}