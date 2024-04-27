package com.salesmanagement.salesmagament.infraestructure.adapters.ouput.persistence;

import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model.PriceEntity;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
 class PricePersistenceAdapterRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    public void setUp() {
        priceRepository.deleteAll();
    }
    @Test
     void testInsert_record_prices(){
        PriceEntity price = new PriceEntity(null,1,
                Timestamp.valueOf("2020-06-14 00:00:00"),
                Timestamp.valueOf("2020-12-31 23:59:59"),
                35455,
                35.50D,0,"EUR");

       PriceEntity entitySaved= priceRepository.save(price);

        assertEquals(1.0, priceRepository.count());

    }
    @Test
     void testQuery_find_using_query(){
        List<PriceEntity> products = List.of(
                new PriceEntity(1L,1,Timestamp.valueOf("2020-06-14 00:00:00"),Timestamp.valueOf("2020-12-31 23:59:59"),35455,35.00,0,"EUR"),
                new PriceEntity(2L,1,Timestamp.valueOf("2020-06-14 15:00:00"),Timestamp.valueOf("2020-06-14 18:30:00"),35455,25.00,1,"EUR"),
                new PriceEntity(3L,1,Timestamp.valueOf("2020-06-15 00:00:00"),Timestamp.valueOf("2020-06-15 11:00:00"),35455,30.50,1,"EUR"),
                new PriceEntity(4L,1,Timestamp.valueOf("2020-06-15 16:00:00"),Timestamp.valueOf("2020-12-31 23:59:59"),35455,39.95,1,"EUR")
        );

        priceRepository.saveAll(products);
        //find by query
        Integer brandId=1;
        Integer productId=35455;
        LocalDateTime time =LocalDateTime.of(2020,06,14,10,00);
        List<PriceEntity> prices=priceRepository.findPreciosByCriterias(brandId,productId,time);
        System.out.println(prices.stream().count());
        assertEquals(true,prices.stream().count()>0);

    }

}
