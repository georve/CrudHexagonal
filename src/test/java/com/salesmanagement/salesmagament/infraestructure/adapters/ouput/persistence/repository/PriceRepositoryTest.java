package com.salesmanagement.salesmagament.infraestructure.adapters.ouput.persistence.repository;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model.PriceEntity;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;

    @Test
    public void testFindPreciosByCriterias() {
        PriceEntity price1 = new PriceEntity(null, 1,
                Timestamp.valueOf(LocalDateTime.of(2024, 8, 1, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2024, 8, 31, 23, 59)),
                100, 50.0, 1, "USD");

        PriceEntity price2 = new PriceEntity(null, 1,
                Timestamp.valueOf(LocalDateTime.of(2024, 8, 1, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2024, 8, 15, 23, 59)),
                100, 60.0, 2, "USD");

        priceRepository.save(price1);
        priceRepository.save(price2);

        List<PriceEntity> result = priceRepository.findPreciosByCriterias(
                1, 100,
                LocalDateTime.of(2024, 8, 10, 0, 0)
        );

        assertThat(result).hasSize(1);

        assertThat(result.get(0).getPrice()).isEqualTo(60.0);
        assertThat(result.get(0).getPriority()).isEqualTo(2);
    }

    @Test
    public void testFindPreciosByCriterias_NoMatch() {
        PriceEntity price1 = new PriceEntity(null, 2,
                Timestamp.valueOf(LocalDateTime.of(2024, 8, 1, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2024, 8, 31, 23, 59)),
                200, 70.0, 1, "EUR");

        priceRepository.save(price1);

        List<PriceEntity> result = priceRepository.findPreciosByCriterias(
                1, 100,
                LocalDateTime.of(2024, 9, 1, 0, 0)
        );

        assertThat(result).isEmpty();
    }
}
