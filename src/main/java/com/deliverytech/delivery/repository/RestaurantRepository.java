package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
