package org.generation.italy.company.service.abstraction;

import org.generation.italy.company.model.Customer;
import org.generation.italy.company.model.Product;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
        List<Customer> findAll();
        Optional<Customer> findById(int id);
        boolean deleteById(int id);
        Customer create(Customer c);
        List<Customer> findByCompanyName(String companyName);
        boolean update(Customer customer);
}
