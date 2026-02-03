package com.deliverytech.deliveryapi.repository;

import com.deliverytech.deliveryapi.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
