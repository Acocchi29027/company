package org.generation.italy.company.repository.abstraction;

import org.generation.italy.company.dto.ProductDTO;
import org.generation.italy.company.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
            SELECT p FROM Product p
            WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))
            AND p.discontinued = :discontinued
            """)
    List<Product> findByProductNameAndIsDiscontinued(@Param("name") String name,
                                                     @Param("discontinued") boolean discontinued);

    @Query("""
            SELECT p FROM Product p
            WHERE p.unitprice BETWEEN :min AND :max
            AND p.discontinued = 0
            """)
    List<Product> findAvailableProductsInPriceRange(@Param("min") double min, @Param("max") double max);

    List<Product> findByProductNameContaining(String name);

    List<Product> findByDiscontinued(boolean discontinued);

    List<Product> findByCategoryCategoryName(String name); //1 es

    List<Product> findBySupplierCountry(String country); //2 es

    // es3
    @Query("""
            SELECT p 
            FROM Product p
            WHERE p.unitprice > (
            SELECT AVG(p2.unitprice)
            FROM Product p2
            )
            """)
    List<Product> findProductsCostingMoreThanAverage();

    // es4
    @Query("""
            SELECT p1 
            FROM Product p1
            WHERE p1.unitprice > (
            SELECT AVG(p2.unitprice)
            FROM Product p2
            WHERE p2.category = p1.category
            )
            """)
    List<Product> findProductsCostingMoreThanCategoryAverage();

    // es5
    @Query("""
                SELECT p
                FROM Product p
                WHERE NOT EXISTS (
                    SELECT od
                    FROM OrderDetails od
                    WHERE od.product = p
                )
            """)
    List<Product> findProductsNeverOrdered();

    // es6
    @Query("""
               SELECT od.product
                  FROM OrderDetails od
                  GROUP BY od.product
                  ORDER BY sum(od.qty) desc
            """)
    List<Product> findMostFrequentlyOrderedProducts(Pageable pageable); // Pageable = LIMIT in SQL

    // es7
    @Query("""
                SELECT DISTINCT p
                FROM OrderDetails od
                JOIN od.product p
                JOIN od.order o
                WHERE o.empId.empId = :employeeId
            """)
    // In JPQL di solito non scrivi ON perché non stai joinando “tabelle”, stai joinando relazioni tra entity già definite con @ManyToOne, @OneToMany, ecc.
    List<Product> findProductsByEmployee(@Param("employeeId") Integer employeeId);

    // es8
    @Query("""
                SELECT p
                FROM Product p
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM OrderDetails od
                    JOIN od.order o
                    WHERE od.product = p
                      AND o.orderDate >= :since
                )
            """)
    List<Product> findProductsNotOrderedSince(@Param("since") LocalDateTime since);
    // es9
    /**
     * Per il metodo 9 non serve JPQL perché puoi farlo con i metodi CRUD già pronti di Spring Data JPA:
     *
     * findById(...) (carica Product e Supplier)
     *
     * setSupplier(...) (modifica l’oggetto in memoria)
     *
     * save(...) (JPA fa l’UPDATE nel DB)
     */
}
