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
    public List<Customer> findAll(){
        return repo.findAll();
    }

    @Override
    public Optional<Customer> findById(int id){
        return repo.findById(id);
    }

    @Override
    public boolean deleteById(int id){
        Optional<Customer> oc = repo.findById(id);
        if (oc.isPresent()){
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Customer create(Customer c){
        return repo.save(c);
    }

    @Override
    public boolean update(Customer customer){
        if (!repo.existsById(customer.getCustId())){
            return false;
        }
        repo.save(customer);
        return true;
    }
}
