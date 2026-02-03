package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
