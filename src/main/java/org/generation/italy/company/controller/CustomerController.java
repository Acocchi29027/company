package org.generation.italy.company.controller;

import org.generation.italy.company.dto.CustomerDTO;
import org.generation.italy.company.model.Customer;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.company.dto.CustomerDTO.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerDTO> findCustomers() {
        List<Customer> customers = service.findAll();
        return customers.stream().map(CustomerDTO::fromCustomer).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Optional<Customer> oc = service.findById(id);
        if (oc.isPresent()) {
            return ResponseEntity.ok(fromCustomer(oc.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create (@RequestBody CustomerDTO dto) {
        Customer customer = dto.toEntity();
        Customer created = service.create(customer);
        CustomerDTO createdDto = fromCustomer(created);
        URI location = URI.create("/api/customers/" + created.getCustId());
        return ResponseEntity.created(location).body(createdDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        boolean deleted = service.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
