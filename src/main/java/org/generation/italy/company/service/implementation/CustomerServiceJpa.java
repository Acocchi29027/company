package org.generation.italy.company.service.implementation;

import org.generation.italy.company.dto.CustomerDTO;
import org.generation.italy.company.model.Customer;
import org.generation.italy.company.repository.abstraction.CustomerRepository;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceJpa implements CustomerService {

    private final CustomerRepository repo;

    public CustomerServiceJpa(CustomerRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CustomerDTO> findAll() {
        return repo.findAll().stream()
                .map(CustomerDTO::fromCustomer)
                .toList();
    }

    @Override
    public Optional<CustomerDTO> findById(int id) {
        return repo.findById(id).map(CustomerDTO::fromCustomer);
    }

    @Override
    public CustomerDTO create(CustomerDTO dto) {
        Customer saved = repo.save(dto.toEntity());
        return CustomerDTO.fromCustomer(saved);
    }

    @Override
    public Optional<CustomerDTO> update(int id, CustomerDTO dto) {
        return repo.findById(id).map(existing -> {
            // update field-by-field (senza cambiare id)
            Customer updated = new Customer(
                    existing.getCustId(),
                    dto.getCompanyName(),
                    dto.getContactName(),
                    dto.getContactTitle(),
                    dto.getAddress(),
                    dto.getCity(),
                    dto.getRegion(),
                    dto.getPostalCode(),
                    dto.getCountry(),
                    dto.getPhone(),
                    dto.getFax()
            );
            return CustomerDTO.fromCustomer(repo.save(updated));
        });
    }

    @Override
    public boolean deleteById(int id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
