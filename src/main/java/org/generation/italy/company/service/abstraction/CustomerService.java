package org.generation.italy.company.service.abstraction;

import org.generation.italy.company.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> findAll();
    Optional<Customer> findById(Integer id);
}
