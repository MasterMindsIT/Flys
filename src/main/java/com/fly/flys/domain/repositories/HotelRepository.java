package com.fly.flys.domain.repositories;

import com.fly.flys.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Set;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    Set<HotelEntity> findByPriceLessThan(BigDecimal price);
    Set<HotelEntity> findByPriceIsBetween(BigDecimal min, BigDecimal max);
    Set<HotelEntity> findByRatingGreaterThan(Integer rating);
}
