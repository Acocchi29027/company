package org.generation.italy.company.repository.abstraction;

import org.generation.italy.company.model.Employee;
import org.generation.italy.company.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    List<Product> findByCategoryCategoryName(String name);

    List<Product> findBySupplierCountry(String country);
    @Query("""
            SELECT p FROM Product p
            WHERE p.unitprice > (
                SELECT AVG(p2.unitprice)
                FROM Product p2
            )
            """)
    List<Product> findByUnitpriceGreaterThanAverageUnitprice();
    @Query("""
            SELECT p FROM Product p
            WHERE p.unitprice > (
                SELECT AVG(p2.unitprice)
                FROM Product p2
                WHERE p2.category.categoryId = p.category.categoryId
            )
            """)
    List<Product> findByUnitpriceGreaterThanAverageUnitpriceAndSameCategory();
    @Query("""
            SELECT p FROM Product p
            WHERE (
                SELECT COUNT(*)
                FROM OrderDetails o
                WHERE o.product.productId = p.productId
            ) = 0
            """)
    List<Product> findByNoOrders();
    @Query("""
            SELECT p FROM Product p
            WHERE p.productId IN (
                SELECT o.product.productId FROM OrderDetails o
                GROUP BY o.product.productId
                ORDER BY COUNT(*)
                LIMIT 3
            )
            """)
    List<Product> findByInThreeMostOrdered();
    @Query("""
            SELECT p FROM Product p
            JOIN OrderDetails od ON p.productId = od.product.productId
            JOIN Order o ON o.orderId = od.order.orderId
            WHERE o.employee.empId = :#{#emp.empId}
            """)
    List<Product> findByOrderEmployee(@Param("emp") Employee e);
}
/*
Implementazione dei seguenti metodi più test.
1) Metodo che ritorna tutti i prodotti che appartengono a una categoria il cui nome viene dato in input
2) metodo che ritorna tutti i prodotti che appartengono a un supplier che vivono in una country che viene dato in input
3) Metodo che ritorna la lista di tutti i prodotti che costano più del costo medio dei prodotti
4) Metodo che ritorna la lista di tutti i prodotti che costano più del costo medio dei prodotti della stessa categoria
5) Metodo che ritorna tutti i prodotti che non sono mai stati ordinati
6) Metodo che ritorna i tre prodotti che sono stati ordinati di più
7) Metodo che ritorna la lista di tutti i prodotti la cui vendita è stata seguita da un impiegato che viene dato in input
8) Metodo che ritorna lista di prodotti che non sono stati ordinati a partire da una certa data nel passato
9) Metodo che va a segnare ad un prodotto con un certo id, un nuovo supplier di cui viene dato in input l'id
 */
