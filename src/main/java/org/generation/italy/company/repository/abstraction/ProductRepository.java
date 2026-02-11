package org.generation.italy.company.repository.abstraction;

import org.generation.italy.company.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
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
    @Query("""
            SELECT p
            FROM Product p
            JOIN p.category c
            WHERE c.categoryName = :name
            """)
    List<Product> findByCategoryName (@Param("name") String name);

    List<Product> findByProductNameContaining(String name);
    List<Product> findByDiscontinued(boolean discontinued);
    @Query("""
SELECT p
FROM Product p
WHERE p.supplier.country = :country
""")
    List<Product> findBySupplierCountry (@Param("country") String country);
    @Query("""
SELECT p
FROM Product p
WHERE p.unitprice >
(SELECT AVG(p2.unitprice)
FROM Product p2
WHERE p2.category = p.category)
""")
    List<Product> findProductsAboveAveragePrice();
    @Query("""
SELECT p
FROM Product p
WHERE p.unitprice > (
    SELECT AVG(p2.unitprice)
    FROM Product p2
    WHERE p2.category = p.category
)
""")
    List<Product> findProductsAboveCategoryAverage();
    @Query("""
SELECT p
FROM Product p
WHERE NOT EXIST(
SELECT od
FROM OrderDetails od
WHERE od.product = p
)
""") // WHERE productId NOT IN (SELECT od.product.productId FROM OrderDetail od
    List<Product> findProductsNeverOrdered();
    @Query("""
SELECT DISTINCT p FROM Product p
JOIN OrderDetail od ON od.product = p
JOIN od.order o
WHERE o.employee.employeeId = :empId
""")
    List<Product> findProductsSoldByEmployee(@Param("empId") int empid);
    @Query("""
SELECT p FROM Product p
WHERE p.productId NOT IN (
    SELECT od.product.productId
    FROM OrderDetail od
    WHERE od.order.orderDate >= :date
)
""")
    List<Product> findProductsNotOrderedSince(@Param("date") LocalDate date);

}
/*
Implementazione dei seguenti metodi più test.
1) Metodo che ritorna tutti i prodotti che appartengono ad una categoria il cui nome viene dato in input
2) metodo che ritorna tutti i prodotti che appartengono a un supplier che vivono in una country che viene dato in input
3) Metodo che ritorna la lista di tutti i prodotti che costano più del costo medio dei prodotti
4) Metodo che ritorna la lista di tutti i prodotti che costano più del costo medio dei prodotti della stessa categoria
5) Metodo che ritorna tutti i prodotti che non sono mai stati ordinati
6) Metodo che ritorna i tre prodotti che sono stati ordinati di più ???
7) Metodo che ritorna la lista di tutti i prodotti la cui vendita è stata seguita da un impiegato che viene dato in input
8) Metodo che ritorna lista di prodotti che non sono stati ordinati a partire da una certa data nel passato
9) Metodo che va a segnare ad un prodotto con un certo id, un nuovo supplier di cui viene dato in input l'id
 */
