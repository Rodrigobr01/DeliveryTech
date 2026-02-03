package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
