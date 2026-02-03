package com.deliverytech.deliveryapi.repository;

import com.deliverytech.deliveryapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
