package com.salesmanagement.salesmagament.integration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.model.PriceCreateDtoRequest;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.model.PriceEntity;
import com.salesmanagement.salesmagament.infraestructure.adapters.output.persistence.repository.PriceRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    public void setup() {
        priceRepository.deleteAll();
        priceRepository.saveAll(List.of(
                new PriceEntity(1L, 1, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 35455, 35.00, 0, "EUR"),
                new PriceEntity(2L, 1, Timestamp.valueOf("2020-06-14 15:00:00"), Timestamp.valueOf("2020-06-14 18:30:00"), 35455, 25.00, 1, "EUR"),
                new PriceEntity(3L, 1, Timestamp.valueOf("2020-06-15 00:00:00"), Timestamp.valueOf("2020-06-15 11:00:00"), 35455, 30.50, 1, "EUR"),
                new PriceEntity(4L, 1, Timestamp.valueOf("2020-06-15 16:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 35455, 39.95, 1, "EUR")
        ));
    }


    @Test
    public void testSearchPricesByCriteria_10AM_14thJune() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("appDate", "2020-06-14 10:00:00"))  // Fecha y hora específica
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(35.00));  // Se espera el precio de 35.00
    }

    @Test
    public void testSearchPricesByCriteria_4PM_14thJune() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("appDate", "2020-06-14 16:00:00"))  // Fecha y hora específica
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(25.00));  // Se espera el precio de 25.00
    }

    @Test
    public void testSearchPricesByCriteria_9PM_14thJune() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("appDate", "2020-06-14 21:00:00"))  // Fecha y hora específica
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(35.00));  // Se espera el precio de 25.00
    }

    @Test
    public void testSearchPricesByCriteria_10AM_15thJune() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("appDate", "2020-06-15 10:00:00"))  // Fecha y hora específica
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(30.50));  // Se espera el precio de 30.50
    }

    @Test
    public void testSearchPricesByCriteria_9PM_16thJune() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("appDate", "2020-06-16 21:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(39.95));
    }

    @Test
    public void testSearchPricesByCriteria_Found() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("appDate", "2020-06-14 15:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(25.00));
    }

    @Test
    public void testSearchPricesByCriteria_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("appDate", "2024-07-01 00:00:00"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testSavePrice() throws Exception {
        PriceCreateDtoRequest request = PriceCreateDtoRequest.builder()
                .brandId(2)
                .startDate("2020-09-01 00:00:00")
                .endDate("2020-09-30 23:59:59")
                .productId(35678)
                .price(60.0)
                .priority(2)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(60.0));
    }

    @Test
    public void testUpdatePrice() throws Exception {

        PriceEntity existingEntity = priceRepository.save(new PriceEntity(1L,1,
                Timestamp.valueOf("2020-06-14 00:00:00"),
                Timestamp.valueOf("2020-12-31 23:59:59"),
                35455,35.00,0,"EUR"));

        Long existingId = existingEntity.getId();
        assertTrue(priceRepository.findById(existingId).isPresent(), "La entidad debería estar presente antes de la actualización");

        PriceCreateDtoRequest updateRequest = PriceCreateDtoRequest.builder()
                .brandId(1)
                .startDate("2020-06-14 00:00:00")
                .endDate("2020-12-31 23:59:59")
                .productId(35455)
                .price(45.50)
                .priority(1)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/prices/" + existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(45.50));
    }

}
