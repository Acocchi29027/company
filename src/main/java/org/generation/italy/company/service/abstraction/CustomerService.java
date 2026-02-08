package org.generation.italy.company.service.abstraction;

import org.generation.italy.company.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> findAll();
    Optional<Customer> findById(int id);
    boolean deleteById(int id);
    Customer create(Customer c);
    List<Customer> findByCompanyName(String name);
    List<Customer> findByCity(String city);
    List<Customer> findByCompanyNameAndByRegion(String companyname, String region);
    List<Customer> findByPostalCode(String postalcode);
    boolean update(Customer c);
}
