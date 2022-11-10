package com.example.marketpricehandlerexercise.service;

import com.example.marketpricehandlerexercise.domain.PriceModel;
import com.example.marketpricehandlerexercise.domain.PriceModelDto;
import com.example.marketpricehandlerexercise.repository.PriceModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceModelService {

    private final PriceModelRepository priceModelRepository;

    public List<PriceModelDto> getAllPriceModels() {
        return priceModelRepository.findAll().stream()
                .map(PriceModel::toPriceModelDto)
                .toList();
    }
}
