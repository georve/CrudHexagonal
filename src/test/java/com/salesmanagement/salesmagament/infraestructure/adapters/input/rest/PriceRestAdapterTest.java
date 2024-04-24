package com.salesmanagement.salesmagament.infraestructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanagement.salesmagament.application.ports.input.PriceServicePort;
import com.salesmanagement.salesmagament.domain.model.Prices;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.mapper.PriceMapper;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.model.PriceCreateDtoRequest;
import com.salesmanagement.salesmagament.infraestructure.adapters.input.rest.model.PriceDtoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void createPriceSuccess()  throws Exception{
        PriceCreateDtoRequest priceToSave = getBuildPriceRequest();
        when(mapper.toPrice(any())).thenReturn(getBuildPrice());
        when(port.save(any())).thenReturn(getBuildPrice());
        when(mapper.toPriceDtoResponse(any())).thenReturn(getBuildPriceResponse());

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = objectMapper.writeValueAsString(priceToSave);

        client.perform(post("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eatToDoJSON)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.price").value("22.5"));
    }

    private PriceDtoResponse getBuildPriceResponse() {
        return PriceDtoResponse.builder()
                .startDate("2024-01-01")
                .endDate("2024-03-01")
                .brandId(1)
                .productId(33244)
                .price(22.5)
                .build();
    }

    private List<PriceDtoResponse> getPriceListDto() {
        List<PriceDtoResponse> priceList= new ArrayList<>();
        priceList.add(PriceDtoResponse.builder()
                .startDate("2024-01-01")
                .endDate("2024-03-01")
                .brandId(1)
                .productId(33244)
                .price(22.5)
                .build());
        return priceList;
    }

    private List<Prices> getPriceList() {
        List<Prices> priceList= new ArrayList<>();
        priceList.add(getBuildPrice());
        return priceList;
    }

    private static Prices getBuildPrice() {
        return Prices.builder()
                .startDate("2024-01-01")
                .endDate("2024-03-01")
                .brandId(1)
                .priceList("55")
                .priority(2)
                .price(22.5)
                .build();
    }

    private PriceCreateDtoRequest getBuildPriceRequest(){
        return PriceCreateDtoRequest.builder()
                .startDate("2024-01-01")
                .endDate("2024-03-01")
                .brandId(1)
                .productId(2343)
                .priority(2)
                .price(22.5)
                .build();
    }


}
