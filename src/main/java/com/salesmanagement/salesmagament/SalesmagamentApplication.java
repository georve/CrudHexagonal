package com.salesmanagement.salesmagament;

import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model.PriceEntity;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class SalesmagamentApplication implements CommandLineRunner {


	private final PriceRepository priceRepository;



    public static void main(String[] args) {
		SpringApplication.run(SalesmagamentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<PriceEntity> products = List.of(
				new PriceEntity(1L,1, Timestamp.valueOf("2020-06-14 00:00:00"),Timestamp.valueOf("2020-12-31 23:59:59"),35455,35.00,0,"EUR"),
				new PriceEntity(2L,1,Timestamp.valueOf("2020-06-14 15:00:00"),Timestamp.valueOf("2020-06-14 18:30:00"),35455,25.00,1,"EUR"),
				new PriceEntity(3L,1,Timestamp.valueOf("2020-06-15 00:00:00"),Timestamp.valueOf("2020-06-15 11:00:00"),35455,30.50,1,"EUR"),
				new PriceEntity(4L,1,Timestamp.valueOf("2020-06-15 16:00:00"),Timestamp.valueOf("2020-12-31 23:59:59"),35455,39.95,1,"EUR")
		);

		priceRepository.saveAll(products);


	}

}
