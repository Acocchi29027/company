package org.generation.italy.company.controller;

import org.generation.italy.company.dto.CustomerDTO;
import org.generation.italy.company.dto.ProductDTO;
import org.generation.italy.company.model.Customer;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.company.dto.CustomerDTO.fromCustomer;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService service;

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDto) {
        Customer customer = customerDto.toEntity();
        Customer created = service.create(customer);
        CustomerDTO dto = fromCustomer(created);
        URI location = URI.create("/api/products/" + created.getCustId());
        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping
    public List<CustomerDTO> findAll() {
        List<Customer> lc = service.findAll();
        return lc.stream().map(CustomerDTO::fromCustomer).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Customer> oc = service.findById(id);
        if (oc.isPresent()){
            return ResponseEntity.ok(fromCustomer(oc.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        boolean deleted = service.deleteById(id);
        if (deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody CustomerDTO customerDTO){
        if (id != customerDTO.getCustId()){
            return ResponseEntity.badRequest().body("L'id non corrisponde a quello desiderato.");
        }
        Customer customer = customerDTO.toEntity();
        boolean updated = service.update(customer);
        if (!updated){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fromCustomer(customer));
    }
}
