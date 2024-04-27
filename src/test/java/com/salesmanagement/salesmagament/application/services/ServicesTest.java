package com.salesmanagement.salesmagament.application.services;

import com.salesmanagement.salesmagament.application.ports.output.PricePersistencePort;
import com.salesmanagement.salesmagament.domain.model.Prices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ServicesTest {
    
    
    private PricesService service;
    
    @MockBean
    private PricePersistencePort persistencePort;
    
    @BeforeEach
    void setUp() {
        service = new PricesService(persistencePort);
    }
    
    @Test
    void findOnePriceSuccess(){
        //arrange
        Map<String,String> map = new HashMap<>();

        when(persistencePort.findByCriteria(any())).thenReturn(getOnePrice());
        //act
        List<Prices> found=service.findByCriteria(map);
        //assert
        assertNotNull(found);
    }

    @Test
    void saveOneObjectSuccess(){
        when(persistencePort.save(any())).thenReturn(getEurPrice());
        Prices saved=service.save(getEurPrice());
        assertNotNull(saved);
        assertEquals(1,saved.getBrandId());
        assertEquals(25.80,saved.getPrice());

    }
    @Test
    void updateOneObjectSuccess(){
        when(persistencePort.findById(anyString())).thenReturn(Optional.of(getEurPrice()) );
        when(persistencePort.save(any())).thenReturn(getEurPriceChanged());

        Prices saved=service.update("1",getEurPrice());

        assertNotNull(saved);
        assertEquals(1,saved.getBrandId());
        assertEquals(58.90,saved.getPrice());

    }

    private List<Prices> getOnePrice() {

        List<Prices> prices = new ArrayList<>();
        prices.add(getEurPrice());
        return prices;
    }

    private static Prices getEurPrice() {
        return Prices.builder().
                price(25.80)
                .priceList("1").
                brandId(1).
                currencyCode("EUR").
                startDate("2020-06-14 14:00:00")
                .endDate("2020-06-14 14:50:00")
                .build();
    }

    private static Prices getEurPriceChanged() {
        return Prices.builder().
                price(58.90)
                .priceList("1").
                brandId(1).
                currencyCode("EUR").
                startDate("2020-06-14 14:00:00")
                .endDate("2020-06-14 14:50:00")
                .build();
    }

}
