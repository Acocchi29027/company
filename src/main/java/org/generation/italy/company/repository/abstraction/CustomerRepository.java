package org.generation.italy.company.repository.abstraction;

import org.generation.italy.company.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("""
        SELECT c FROM Customer c
        WHERE LOWER(c.companyName) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Customer> searchByCompanyName(@Param("name") String name);

    @Query("""
        SELECT c FROM Customer c
        WHERE LOWER(c.contactName) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Customer> searchByContactName(@Param("name") String name);

    boolean searchByCompanyNameIgnoreCase(String companyName); // Questi non stanno in JpaRepository perché dipendono dai tuoi campi. Ecco perchè lo dichiaro
}
