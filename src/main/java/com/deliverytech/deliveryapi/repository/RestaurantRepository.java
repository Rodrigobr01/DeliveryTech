package com.deliverytech.deliveryapi.repository;

import com.deliverytech.deliveryapi.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
