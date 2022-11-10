package com.example.marketpricehandlerexercise.component;

import com.example.marketpricehandlerexercise.domain.PriceModel;
import com.example.marketpricehandlerexercise.repository.PriceModelRepository;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class ListenerComponentTest {
    @Autowired
    private PriceModelRepository priceModelRepository;
    @Autowired
    private ListenerComponent listenerComponent;
    private PriceModel priceModel;
    private String message;

    @BeforeEach
    void setUp() {
        message = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n" + "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002\n" + "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002\n" + "109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100\n" + "110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110\n";
        priceModel = PriceModel.builder()
                .id(123L)
                .bid(BigDecimal.valueOf(1000))
                .ask(BigDecimal.valueOf(2000))
                .instrumentName("EUR/USD")
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void given_Message_whenOnPriceFeedMessage_thenReturnSize5True() throws IOException, CsvException {
        listenerComponent.onPriceFeedMessage(message);
        assertEquals(priceModelRepository.findAll().size(), 5);
    }

    @Test
    void applyBidCommission() {
        listenerComponent.applyBidCommission(priceModel,0.1);
        assertEquals(priceModel.getBid(), NumberUtils.createBigDecimal("999.00"));
    }

    @Test
    void applyAskCommission() {
        listenerComponent.applyAskCommission(priceModel,0.1);
        assertEquals(priceModel.getAsk(), NumberUtils.createBigDecimal("2002.00"));
    }

}