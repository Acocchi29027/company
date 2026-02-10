package org.generation.italy.company.service.abstraction;

import org.generation.italy.company.dto.CustomerDTO;
import org.generation.italy.company.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDTO> findAll();
    Optional<CustomerDTO> findById(int id);
    CustomerDTO create(CustomerDTO dto);
    Optional<CustomerDTO> update(int id, CustomerDTO dto);
    boolean deleteById(int id);
    List<CustomerDTO> searchByCompanyName(String name); // Customizzato --> CustomerRepository.java
    List<CustomerDTO> searchByContactName(String name); // Customizzato --> CustomerRepository.java
    boolean existsByCompanyNameIgnoreCase(String name);
}
