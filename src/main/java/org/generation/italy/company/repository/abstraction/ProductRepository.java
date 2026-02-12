package org.generation.italy.company.repository.abstraction;

import org.generation.italy.company.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


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
    //1
    @Query("""   
            SELECT p
            FROM Product p
            JOIN p.category c
            WHERE c.categoryName = :name
            """)
    List<Product> findByCategoryName (@Param("name")String name);
    //2
    @Query("""
            SELECT p
            FROM Product p
            JOIN p.supplier s
            WHERE s.country = :country
            """)
    List<Product> findBySupplierCountry(@Param("country")String country);
    //3
    @Query("""
            SELECT p
            FROM Product p
            WHERE p.unitprice > (
               SELECT AVG(p2.unitprice)
               FROM Product p2 )
            """)
    List<Product> findByAvgPrice();
    //4
    @Query("""
             SELECT p1
             FROM Product p1
             WHERE p1.unitprice > (
               SELECT AVG(p2.unitprice)
               FROM Product p2
               WHERE p2.category = p1.category)
             """)
    List<Product> findByAvgPriceCategory();
    //5 //il prodotto in questa linea d'ordine è uguale al prodotto selezionato
    @Query("""
            SELECT p
            FROM Product p
            WHERE NOT EXISTS (
                SELECT od
                FROM OrderDetails od
                WHERE od.product = p )
            """)
    List<Product> findProductsNeverOrdered();

    @Query("""
            SELECT od.product
            FROM OrderDetails od
            GROUP BY od.product
            ORDER BY SUM (od.qty) DESC
            """) // non posso usare limit allora creerò un oggetto di tipo Pageable gli darò in input (0 per indicare la prima pagina
                 // ,3 i primi tre risultati) e il metodo associato a questa query la prenderà in input
    Page<Product> findTop3OrderedProduct(Pageable pageable);
    //7
    @Query("""
           SELECT DISTINCT p
           FROM Product p
           JOIN OrderDetails od
           ON od.product = p
           JOIN od.order o
           WHERE o.employee.empId = :id
           ORDER BY p.productId
           """)
    List<Product> findOrderByEmployee(@Param("id")Integer id);
    //8
    @Query("""
            SELECT p
            FROM Product p
            WHERE NOT EXISTS (
                SELECT od
                FROM OrderDetails od
                WHERE od.product = p
                AND od.order.orderDate >= :data)
            ORDER BY p.productId
            """)
    List<Product> findProductNotOrderedAfterDate(@Param("data") LocalDateTime orderDate);
    //9
//    @Transactional // ci indica che è un operzaione che si svolge in blocco in simultanea, sia qui che su Pgadmin
    @Modifying (clearAutomatically = true, flushAutomatically = true)//una query di modifica come delete
    @Query("""
           UPDATE Product p
           SET p.supplier.supplierId = :supplierId
           WHERE p.productId = :productId
           """)
    void updateSupplier(@Param("supplierId")int supplierId, @Param("productId")int productId);

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
