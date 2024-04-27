package com.salesmanagement.salesmagament.infraestructure.adapters.ouput.persistence;

import com.salesmanagement.salesmagament.domain.model.Prices;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.PricePersistenceAdapter;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.mapper.PriceEntityMapper;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model.PriceEntity;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
 class PricePersistenceAdapterTest {

    private PricePersistenceAdapter pricePersistenceAdapter;
    @Autowired
    private PriceRepository priceRepository;

    private PriceEntityMapper priceEntityMapper= Mappers.getMapper(PriceEntityMapper.class);

    @BeforeEach
    void setUp() {
        priceRepository.deleteAll();
        this.pricePersistenceAdapter=new PricePersistenceAdapter(priceRepository,priceEntityMapper);
    }



    @Test
     void findingPrice(){

        List<PriceEntity> products = List.of(
                new PriceEntity(1L,1, Timestamp.valueOf("2020-06-14 00:00:00"),Timestamp.valueOf("2020-12-31 23:59:59"),35455,35.00,0,"EUR"),
                new PriceEntity(2L,1,Timestamp.valueOf("2020-06-14 15:00:00"),Timestamp.valueOf("2020-06-14 18:30:00"),35455,25.00,1,"EUR"),
                new PriceEntity(3L,1,Timestamp.valueOf("2020-06-15 00:00:00"),Timestamp.valueOf("2020-06-15 11:00:00"),35455,30.50,1,"EUR"),
                new PriceEntity(4L,1,Timestamp.valueOf("2020-06-15 16:00:00"),Timestamp.valueOf("2020-12-31 23:59:59"),35455,39.95,1,"EUR")
        );

        priceRepository.saveAll(products);
        Map<String,String> map = new HashMap<>();
        map.put("productId","35455");
        map.put("brandId","1");
        map.put("appDate","2020-06-14 10:00:00");
       List<Prices> prices= pricePersistenceAdapter.findByCriteria(map);


        assertEquals(true,prices.stream().count()>0);


    }

    @Test
     void savePriceSuccess(){
        Prices pricesToSave=getBuildPrice();
        Prices saved= pricePersistenceAdapter.save(pricesToSave);
        assertNotNull(saved);
    }

    private static Prices getBuildPrice() {
        return Prices.builder()
                .startDate("2024-01-01 00:00:00")
                .endDate("2024-03-01 23:59:59")
                .brandId(1)
                .priceList("55")
                .priority(2)
                .price(22.5)
                .build();
    }


}
