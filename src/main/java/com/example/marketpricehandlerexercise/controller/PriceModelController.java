package com.example.marketpricehandlerexercise.controller;

import com.example.marketpricehandlerexercise.domain.PriceModelDto;
import com.example.marketpricehandlerexercise.service.PriceModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PriceModelController {

    private final PriceModelService priceModelService;

    @GetMapping("/prices")
    public ResponseEntity<List<PriceModelDto>> getPrices() {
        return ResponseEntity.ok(priceModelService.getAllPriceModels());
    }
}
