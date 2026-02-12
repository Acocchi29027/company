package org.generation.italy.company.repository.abstraction;

import org.generation.italy.company.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}
