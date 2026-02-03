package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.domain.Customer;
import com.deliverytech.delivery.dto.CreateCustomerRequest;
import com.deliverytech.delivery.dto.CustomerResponse;
import com.deliverytech.delivery.service.CustomerService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResponse> listCustomers() {
        return customerService.findAll().stream()
            .map(customer -> new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail()))
            .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        Customer customer = customerService.create(request.getName(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail()));
    }
}
