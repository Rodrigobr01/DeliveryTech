package com.deliverytech.deliveryapi.service;

import com.deliverytech.deliveryapi.domain.Customer;
import com.deliverytech.deliveryapi.exception.NotFoundException;
import com.deliverytech.deliveryapi.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(String name, String email) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }
}
