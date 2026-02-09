package org.generation.italy.company.controller;

import org.generation.italy.company.dto.CustomerDTO;
import org.generation.italy.company.model.Customer;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.generation.italy.company.dto.CustomerDTO.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers") //punto di acesso
public class CustomerController {
    private CustomerService service;

    @Autowired
    public CustomerController(CustomerService service){
    this.service=service;
}
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Optional<Customer> oc = service.findById(id);
        if (oc.isPresent()) {
            return ResponseEntity.ok(fromCustomer(oc.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        boolean deleted = service.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO) {
        Customer c = toEntity(customerDTO);
        Customer created = service.create(c);
        CustomerDTO cd = fromCustomer(c);
        URI location = URI.create("/api/customers/" + created.getCustId());
        return ResponseEntity.created(location).body(cd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody CustomerDTO customerDTO) {
        if (id != customerDTO.getCustId()) {
            return ResponseEntity.badRequest().body("l'id del path deve coincidere con l'id dell'entity");
        }
        Customer c = toEntity(customerDTO);
        boolean updated = service.update(c);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fromCustomer(c));
    }

    @GetMapping
    public List<CustomerDTO> findCustomers(){
        List<Customer> lista= service.findAll();
        return lista.stream().map(CustomerDTO::fromCustomer).toList();
    }
}
