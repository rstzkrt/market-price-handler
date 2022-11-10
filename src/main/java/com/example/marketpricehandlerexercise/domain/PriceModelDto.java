package com.example.marketpricehandlerexercise.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class PriceModelDto {
    private Long id;
    private String instrumentName;
    private BigDecimal bid;
    private BigDecimal ask;
    private Timestamp timestamp;
}
