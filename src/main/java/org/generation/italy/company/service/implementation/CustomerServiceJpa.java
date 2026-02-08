package org.generation.italy.company.service.implementation;

import org.generation.italy.company.model.Customer;
import org.generation.italy.company.repository.abstraction.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceJpa {
    private CustomerRepository repo;

    public List<Customer> findAll() {
        return repo.findAll();
    }
}
