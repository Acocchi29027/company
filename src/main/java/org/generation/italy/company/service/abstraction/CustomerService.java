package org.generation.italy.company.service.abstraction;

import org.generation.italy.company.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
}
