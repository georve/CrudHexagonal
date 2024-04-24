package com.salesmanagement.salesmagament.infraestructure.adapters.input.rest;

import com.salesmanagement.salesmagament.application.ports.input.PriceServicePort;
import com.salesmanagement.salesmagament.domain.model.Prices;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.mapper.PriceMapper;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.model.PriceDtoResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
public class PriceRestAdapterTest {

   @Autowired
   private MockMvc client;

   @MockBean
   private PriceServicePort port;

    @MockBean
   private PriceMapper mapper;

    @Test
    public void getPricesNotFound() throws Exception{
        Map<String,String> map = new HashMap<>();
        map.put("starDate","2024-01-01");
        map.put("endDte","2024-03-01");

        when(port.findByCriteria(anyMap())).thenReturn(new ArrayList<>());

        client.perform(MockMvcRequestBuilders.get("/prices?starDate='2024-01-01'&endDte='2024-03-01'")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

    }

    @Test void getOnePrice() throws Exception{

        Map<String,String> map = new HashMap<>();
        map.put("starDate","2024-01-01");
        map.put("endDte","2024-03-01");



        when(port.findByCriteria(anyMap())).thenReturn(getPriceList());
        when(mapper.toPriceDtoResponseList(anyList())).thenReturn(getPriceListDto());

        client.perform(MockMvcRequestBuilders.get("/prices?starDate='2024-01-01'&endDte='2024-03-01'")
                       .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());

    }

    private List<PriceDtoResponse> getPriceListDto() {
        List<PriceDtoResponse> priceList= new ArrayList<>();
        priceList.add(PriceDtoResponse.builder()
                .startDate("2024-01-01")
                .endDate("2024-03-01")
                .brandId(1)
                .price(22.5)
                .build());
        return priceList;
    }

    private List<Prices> getPriceList() {
        List<Prices> priceList= new ArrayList<>();
        priceList.add(Prices.builder()
                .startDate("2024-01-01")
                .endDate("2024-03-01")
                .brandId(1)
                .priceList("55")
                .priority(2)
                .price(22.5)
                .build());
        return priceList;
    }
}
