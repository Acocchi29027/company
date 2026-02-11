package org.generation.italy.company.repository.abstraction;

import org.generation.italy.company.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    //modi per far implementare automaticmanete dei metodi più avanzati rispetto ai classici metodi CRUD
    //scrivendo la query
    @Query("""
            SELECT p FROM Product p
            WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))
            AND p.discontinued = :discontinued
            """)
    List<Product> findByProductNameAndIsDiscontinued(@Param("name") String name, @Param("discontinued") boolean discontinued);

    @Query("""
            SELECT p FROM Product p
            WHERE p.unitprice BETWEEN :min AND :max
            AND p.discontinued = true
            """)
    List<Product> findAvailableProductsInPriceRange(@Param("min") double min, @Param("max") double max);

    //seguendo un pattern per il nome
    List<Product> findByProductNameContaining(String name);

    List<Product> findByDiscontinued(boolean discontinued);


    @Query("""
            SELECT p
            FROM Product p
            WHERE TRIM(LOWER(p.category.categoryName)) = TRIM(LOWER(:name))
            """)
    List<Product> findByCategoryName(@Param("name") String categoryName);

    @Query("""
            SELECT p
            FROM Product p
            WHERE TRIM(LOWER(p.supplier.country)) = TRIM(LOWER(:country))
            """)
    List<Product> findBySupplierCountry(@Param("country") String country);

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.unitprice>(
                       SELECT AVG(p1.unitprice)
                       FROM Product p1
                       )
            """)
    List<Product> findMoreThanAvg();

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.unitprice>(
                      SELECT AVG(p1.unitprice)
                      FROM Product p1
                      WHERE p1.category = p.category
                      )
            """)
    List<Product> findMoreThanAvgByCategoryId();

    @Query("""
            SELECT p
            FROM Product p 
            WHERE NOT EXISTS(
                      SELECT 1
                      FROM OrderDetails od
                      WHERE od.product = p
                      )
            """)
    List<Product> findProductNeverOrdered();

    @Query("""
          SELECT od.product
          FROM OrderDetails od
          JOIN od.product
          GROUP BY od.product
          ORDER BY COUNT(od.qty) DESC
          """)
    Page<Product>findTop3Products(Pageable pageable);

    @Query("""
          SELECT od.product
          FROM OrderDetails od JOIN od.order o
          JOIN o.empId e
          WHERE e.empId = :empId
          """)
    List<Product> findByEmpId(@Param("empId") int empId);
}

/**
 * Implementazione dei seguenti metodi più test.
 * 1) Metodo che ritorna tutti i prodotti che appartengono a una specifica categoria, il cui nome viene dato in input. X
 * 2) metodo che ritorna tutti i prodotti che appartengono a un supplier che vivono in una country che viene dato in input
 * 3) Metodo che ritorna la lista di tutti i prodotti che costano più del costo medio dei prodotti X
 * 4) Metodo che ritorna la lista di tutti i prodotti che costano più del costo medio dei prodotti della stessa categoria X
 * 5) Metodo che ritorna tutti i prodotti che non sono mai stati ordinatiX
 * 6) Metodo che ritorna i tre prodotti che sono stati ordinati di più X
 * 7) Metodo che ritorna la lista di tutti i prodotti la cui vendita è stata seguita da un impiegato che viene dato in input X
 * 8) Metodo che ritorna lista di prodotti che non sono stati ordinati a partire da una certa data nel passato
 * 9) Metodo che va a segnare ad un prodotto con un certo id, un nuovo supplier di cui viene dato in input l'id
 **/