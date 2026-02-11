package org.generation.italy.company.repository.abstraction;

import jakarta.transaction.Transactional;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

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
    //1
    @Query("""   
            SELECT p
            FROM Product p 
            JOIN p.category c
            WHERE c.categoryName = :name
            """)
    List<Product> findByCategoryName (@Param("name")String name);
//    //2
//    @Query("""
//            SELECT p
//            FROM Product p
//            JOIN p.supplier s
//            WHERE s.country = :country
//            """)
//    List<Product> findByContrySupplier(@Param("country")String country);
//    //3
//    @Query("""
//            SELECT p
//            FROM Product p
//            WHERE p.unitprice > (
//               SELECT AVG(p2.unitprice)
//               FROM Product p2)
//              AND p.name = :name
//            """)
//    List<Product> findByAvgPrice(@Param("name")String roductName);
//    //4
//    @Query("""
//             SELECT p
//             FROM Product p
//             WHERE p.unitprice > (
//               SELECT AVG(p2.unitprice)
//               FROM Product p2)
//             AND p.categoryId = :id
//             """)
//    List<Product> findByAvgPriceCategory(@Param("id")Integer productId);
//    //5
//    @Query("""
//            SELECT p.
//            FROM Product p
//            WHERE NOT EXISTS (
//                SELECT od
//                FROM OrderDetails od
//                WHERE od.product=p.product )
//            """)
//    //6
//    List<Product> findProductsNeverOrdered();
//    @Query("""
//            SELECT p
//            FROM Product p
//            JOIN p.OrderDetails od
//            GROP BY p
//            ORDER BY SUM (od.qty) DESC
//            """) // non posso usare limit allora creerò un ogetto di tipo Pageable gli darò in input (0 per indicare la prima pagina
//                 // ,3 i primi tre risultati) e il metodo associato a questa query lp prenderà in input
//    List<Product> findTop3OrderedProduct(Pageable pageable);
//    //7
//    @Query("""
//           SELECT p
//           FROM Product p
//           JOIN p.OrderDetails od
//           JOIN od.Order o
//           WHERE o.empId.empid = :id
//           """)
//    List<Product> findOrderByEmployee(@Param("id")Integer id);
//    //8
//    @Query("""
//            SELECT P
//            FROM Product p
//            WHERE NOT EXIST (
//                SELECT od
//                FROM OrderDetails od
//                WHERE od.product = p
//                AND od.order.orderDate >= :data)
//            """)
//    //9
//    List<Product>findProductNotOrderderByDate(@Param("data")LocalDateTime orderDate);
//    @Transactional // ci indica che è un operzaione che si svolge in blocco in simultanea, sia qui che su Pgadmin
//    @Modifying //una query di modifica come delete
//    @Query("""
//           UPDATE Product p
//           SET p.supplier = :supplier
//           WHERE p.productId = :productId
//           """)
//    void updateSupplier(@Param("supplier")Supplier supplier, @Param("producId")Integer productId);

}

/*
Implementazione dei seguenti metodi più test.
1) Metodo che ritorna tutti i prodotti che appartengono ad una categoria il cui nome viene dato in input
2) metodo che ritorna tutti i prodotti che appartengono a un supplier che vivono in una country che viene dato in input
3) Metodo che ritorna la lista di tutti i prodotti che costano più del costo medio dei prodotti
4) Metodo che ritorna la lista di tutti i prodotti che costano più del costo medio dei prodotti della stessa categoria
5) Metodo che ritorna tutti i prodotti che non sono mai stati ordinati
6) Metodo che ritorna i tre prodotti che sono stati ordinati di più
7) Metodo che ritorna la lista di tutti i prodotti la cui vendita è stata seguita da un impiegato che viene dato in input
8) Metodo che ritorna lista di prodotti che non sono stati ordinati a partire da una certa data nel passato
9) Metodo che va a segnare ad un prodotto con un certo id, un nuovo supplier di cui viene dato in input l'id
 */
