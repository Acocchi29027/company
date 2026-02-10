package org.generation.italy.company.repository.abstraction;

import org.generation.italy.company.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("""
            SELECT c FROM Customer c
            WHERE LOWER(c.companyName) = :name""")
    List<Customer> findByCompanyName(String name);
}
