package com.example.marketpricehandlerexercise.repository;

import com.example.marketpricehandlerexercise.domain.PriceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceModelRepository extends JpaRepository<PriceModel,Long> {
}
