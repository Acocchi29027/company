package org.generation.italy.company.repository.abstraction;

import jakarta.persistence.EntityManager;
import org.generation.italy.company.CompanyApplication;
import org.generation.italy.company.model.Category;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.model.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ContextConfiguration(classes = CompanyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private ProductRepository repo;

    @Test
    void findByProductNameAndIsDiscontinued() {
    }

    @Test
    void findAvailableProductsInPriceRange() {
    }

    @Test
    void findByProductNameContaining() {
    }

    @Test
    void findByDiscontinued() {
    }

    @Test
    void findByCategoryName() {
        Category category = new Category(null,"TEST_CAT_NAME",
                                    "TEST_CAT_DESC");
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1);   //voglio segnalare che sono prodotti senza id settato perciò null
        Product product1 = new Product(null,"Coca-Cola",supplier,category,12,false);
        Product product2 = new Product(null,"Fanta",supplier,category,4,false);
        em.persist(category);
        em.persist(product1);
        em.persist(product2);
        em.flush(); //comando che si assicura che le persist salvino gli oggetti sul db
        List<Product> products = repo.findByCategoryName(category.getCategoryname());
        assertEquals(2,products.size()); // in questo caso l' assert verifica che ci siano 2 prodotti nel products
        assertTrue(products.stream()
                .allMatch(p->List.of("Coca-Cola","Fanta").contains(p.getProductName())));
    }

    @Test
    void findByContrySupplier() {
    }

    @Test
    void findByAvgPrice() {
    }

    @Test
    void findByAvgPriceCategory() {
    }

    @Test
    void findProductsNeverOrdered() {
    }

    @Test
    void findTop3OrderedProduct() {
    }

    @Test
    void findOrderByEmployee() {
    }

    @Test
    void findProductNotOrderderByDate() {
    }

    @Test
    void updateSupplier() {
    }
}