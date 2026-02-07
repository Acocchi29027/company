package org.generation.italy.company.service.abstraction;

import org.generation.italy.company.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDTO> findAll();
    Optional<CustomerDTO> findById(int id);
    CustomerDTO create(CustomerDTO dto);
    Optional<CustomerDTO> update(int id, CustomerDTO dto);
    boolean deleteById(int id);
}
