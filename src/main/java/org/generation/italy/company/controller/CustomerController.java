package org.generation.italy.company.controller;

import org.generation.italy.company.dto.CustomerDTO;
import org.generation.italy.company.service.abstraction.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO dto) {
        CustomerDTO created = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/customers/" + created.getCustId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable int id, @RequestBody CustomerDTO dto) {
        return service.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteById(id)
                ? ResponseEntity.noContent().build() // se la condizione è true → ritorna 204 No Content (nessun body)
                : ResponseEntity.notFound().build(); // se la condizione è false → ritorna 404 Not Found
    }

    @GetMapping("/search/company/{name}")
    public ResponseEntity<List<CustomerDTO>> searchByCompanyName(@PathVariable String name) {
        List<CustomerDTO> result = service.searchByCompanyName(name);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build(); // se la condizione è true → ritorna 204 No Content (nessun body)
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/contact/{name}")
    public ResponseEntity<List<CustomerDTO>> searchByContactName(@PathVariable String name){
        List<CustomerDTO> result = service.searchByContactName(name);
        if (result.isEmpty()){
            return ResponseEntity.noContent().build(); // se la condizione è true → ritorna 204 No Content (nessun body)
        }
        return ResponseEntity.ok(result); //  ritorna 200 equivalente a --> return ResponseEntity.status(200).body(result);
    }

    


}
