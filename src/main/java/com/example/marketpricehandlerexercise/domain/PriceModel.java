package com.example.marketpricehandlerexercise.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class PriceModel {

    @Id
    private Long id;
    @NotBlank
    private String instrumentName;
    @NotNull
    private BigDecimal bid;
    @NotNull
    private BigDecimal ask;
    @NotNull
    private Timestamp timestamp;

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public PriceModelDto toPriceModelDto() {
        return PriceModelDto.builder()
                .id(this.getId())
                .instrumentName(this.getInstrumentName())
                .bid(this.getBid())
                .ask(this.getAsk())
                .timestamp(this.getTimestamp())
                .build();
    }

    public String toString() {
        return "PriceModel(id=%d, instrumentName=%s, bid=%s, ask=%s, timestamp=%s)".formatted(this.getId(), this.getInstrumentName(), this.getBid(), this.getAsk(), this.getTimestamp());
    }
}
