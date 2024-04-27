package com.salesmanagement.salesmagament.infraestructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanagement.salesmagament.application.ports.input.PriceServicePort;
import com.salesmanagement.salesmagament.domain.exception.InternalException;
import com.salesmanagement.salesmagament.domain.exception.PriceBadRequestException;
import com.salesmanagement.salesmagament.domain.exception.PriceNotFoundException;
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


import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
 class PriceRestAdapterTest {

   @Autowired
   private MockMvc client;

   @MockBean
   private PriceServicePort port;

    @MockBean
    private PriceMapper mapper;

    @Test
    void getPricesNotFound() throws Exception{
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
    void getOnePriceWithBadTimeException() throws Exception{
        Map<String,String> map = new HashMap<>();
        map.put("starDate","2024-01-01 16:00:00");
        map.put("endDte","2024-03-01 18:00:00");
        when(port.findByCriteria(anyMap())).thenThrow(PriceBadRequestException.class);

        client.perform(MockMvcRequestBuilders.get("/prices?starDate='2024-01-01 16:00:00'&endDte='2024-03-01 18:00:00'")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PriceBadRequestException));
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

    @Test
    void createPriceFailure() throws Exception{
        PriceCreateDtoRequest priceToSave = getBuildPriceRequest();
        when(mapper.toPrice(any())).thenReturn(getBuildPrice());
        when(port.save(any())).thenThrow(RuntimeException.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = objectMapper.writeValueAsString(priceToSave);

        client.perform(post("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eatToDoJSON)
                ).andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception));
    }

    @Test
    void updateSuccessFullTest() throws Exception {
        PriceCreateDtoRequest priceToSave = getBuildPriceRequest();
        when(mapper.toPrice(any())).thenReturn(getBuildPrice());

        when(port.update(anyString(),eq(getBuildPrice()))).thenReturn(getBuildPrice());
        when(mapper.toPriceDtoResponse(any())).thenReturn(getBuildPriceResponse());
        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = objectMapper.writeValueAsString(priceToSave);

        client.perform(MockMvcRequestBuilders.put("/prices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(eatToDoJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.price").value("22.5"));


    }

    @Test
    void updatePriceNotFound() throws Exception{
        PriceCreateDtoRequest priceToSave = getBuildPriceRequest();
        when(mapper.toPrice(any())).thenReturn(getBuildPrice());

        when(port.update(anyString(),any())).thenThrow(PriceNotFoundException.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String eatToDoJSON = objectMapper.writeValueAsString(priceToSave);
        client.perform(MockMvcRequestBuilders.put("/prices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(eatToDoJSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PriceNotFoundException));
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
