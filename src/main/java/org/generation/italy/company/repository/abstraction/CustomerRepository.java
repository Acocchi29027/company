package org.generation.italy.company.repository.abstraction;

import org.generation.italy.company.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("""
        SELECT c FROM Customer c 
            WHERE LOWER(c.companyName) LIKE LOWER(CONCAT('%', :name, '%'))
            AND c.region = :region    
    """)
    List<Customer> findByCompanyNameAndByRegion(@Param("name") String companyName, @Param("region") String region);

    @Query("""
        SELECT c FROM Customer c
                WHERE LOWER(c.city) = :city1
        """)
    List<Customer> findByCity(@Param("city1")String city1);
    List<Customer> findByPostalCode(String postalcode);
    List<Customer> findByCompanyName(String name);
}
