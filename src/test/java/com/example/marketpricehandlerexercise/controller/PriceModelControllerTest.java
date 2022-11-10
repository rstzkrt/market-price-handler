package com.example.marketpricehandlerexercise.controller;

import com.example.marketpricehandlerexercise.domain.PriceModel;
import com.example.marketpricehandlerexercise.repository.PriceModelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class PriceModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceModelRepository priceModelRepository;
    private PriceModel priceModel;

    @BeforeEach
    void setUp() {
        priceModel = PriceModel.builder()
                .id(123L)
                .bid(BigDecimal.valueOf(1000))
                .ask(BigDecimal.valueOf(2000))
                .instrumentName("EUR/USD")
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        priceModelRepository.save(priceModel);
    }

    @AfterEach
    void tearDown() {
        priceModelRepository.deleteAll();
    }

    @Test
    void getPrices_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(priceModel.getId()))
                .andExpect(jsonPath("$[0].bid").value(priceModel.getBid().setScale(1)))
                .andExpect(jsonPath("$[0].ask").value(priceModel.getAsk().setScale(1)))
                .andExpect(jsonPath("$[0].instrumentName").value(priceModel.getInstrumentName()));
    }
}