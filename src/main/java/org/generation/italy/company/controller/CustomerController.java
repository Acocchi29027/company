package org.generation.italy.company.controller;

import org.generation.italy.company.dto.CustomerDTO;
import org.generation.italy.company.model.Customer;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.generation.italy.company.dto.CustomerDTO.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    public CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {this.service = service;}

    @GetMapping
    public List<CustomerDTO> findCustomers(@RequestParam(required = false) String companyName) {
        List<Customer> customers;
        if (companyName == null){
            customers = service.findAll();
        }else customers = service.findByCompanyName(companyName);
        return customers.stream().map(CustomerDTO::fromCustomer).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Optional<Customer> customer = service.findById(id);
        if(customer.isPresent()){
            return ResponseEntity.ok(fromCustomer(customer.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        boolean deleted = service.deleteById(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerDTO.toEntity();
        Customer created = service.create(customer);
        CustomerDTO dto = fromCustomer(created);
        URI location = URI.create("/api/customers/" + created.getCustId());
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody CustomerDTO customerDTO){
        if(id != customerDTO.getCustId()){
            return ResponseEntity.badRequest().body("l'id del path deve coincidere con l'id dell'entity");
        }
        Customer customer = customerDTO.toEntity();
        boolean updated = service.update(customer);
        if(!updated){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fromCustomer(customer));
    }
}
