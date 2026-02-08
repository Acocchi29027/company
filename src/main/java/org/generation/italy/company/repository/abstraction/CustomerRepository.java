package org.generation.italy.company.repository.abstraction;

import org.generation.italy.company.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query("""
            SELECT c FROM Customer c 
            WHERE c.city = :city
            """)
    List <Customer> findByCityName (@Param("city")String city);
    @Query("""
            SELECT c FROM Customer c
            WHERE c.companyName = :companyName
            """)
    List<Customer> findByCompanyname (@Param("companyName") String companyName);
}
