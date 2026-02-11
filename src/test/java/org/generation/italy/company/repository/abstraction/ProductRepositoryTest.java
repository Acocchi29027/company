package org.generation.italy.company.repository.abstraction;

import jakarta.persistence.EntityManager;
import org.generation.italy.company.CompanyApplication;
import org.generation.italy.company.model.Category;
import org.generation.italy.company.model.Product;
import org.generation.italy.company.model.Supplier;
import org.generation.italy.company.model.queryresults.CategoryAvg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ContextConfiguration(classes = CompanyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    private Category category;
    private Supplier supplier;
    private Product product1;
    private Product product2;
    private final String FIND_AVG_UNIT_PRICE = "SELECT AVG(p.unitprice) FROM Product p";
    private final String FIND_AVG_CAT_PRICE = """
                                            SELECT p.category.categoryId, AVG (p.unitprice)
                                            FROM Product p
                                            GROUP BY p.category.categoryId
                                            """;

    private final String FIND_AVG_CAT_PRICE_2 = """ 
                                            SELECT new org.generation.italy.company.model.queryresults.CategoryAvg(
                                            p.category.categoryId, AVG(p.unitprice))
                                            FROM Product p
                                            GROUP BY p.category.categoryId
                                            """;
    private final String FIND_NOT_ORDERED_PRODUCT_COUNT = """
                                            SELECT COUNT (*)
                                            FROM products p WHERE NOT EXISTS (
                                            SELECT 1
            	                            FROM orderdetails od
            	                            WHERE od.productid = p.productid)
                                            """;
    private final String FIND_TOP3_ORDERED_PRODUCT = """
                                            SELECT od.productid
                                            FROM OrderDetails od
                                            GROUP BY od.productid
                                            ORDER BY COUNT (*) DESC
                                            LIMIT 3
                                            """;
    @BeforeEach
    public void setup(){
      category = new Category(null,"TEST_CAT_NAME", "TEST_CAT_DESC");
      supplier = new Supplier(null,"TEST_COMP_NAME","TEST_CONTACT_NAME","TEST_CON_TITLE","TEST_ADDRESS","TEST_CITY"
      ,"TEST_REGION","TEST_CODE","TEST_COUNTRY","TEST_PHONE","TEST_FAX");
      product1 = new Product(null,"Coca-Cola",supplier,category,12,false);
      product2 = new Product(null,"Fanta",supplier,category,4,false);
      em.persist(category);
      em.persist(supplier);
      em.persist(product1);
      em.persist(product2);
      em.flush(); //comando che si assicura che le persist salvino gli oggetti sul db

    }

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
        List<Product> products = repo.findByCategoryName(category.getCategoryname());
        assertEquals(2,products.size()); // in questo caso l' assert verifica che ci siano 2 prodotti nel products
        assertTrue(products.stream()
                .allMatch(p->List.of("Coca-Cola","Fanta").contains(p.getProductName())));
    }
    @Test
    void findBySupplierCountry() {
        String country = supplier.getCountry();
        List<Product> productsBySuppCountry = repo.findBySupplierCountry(country);
        assertEquals(2,productsBySuppCountry.size());
        assertTrue(productsBySuppCountry.stream()
                .allMatch(p->p.getSupplier().getCountry().equals(country)));
    }
    @Test
    void findByAvgPrice() {
        double avg = em.createQuery(FIND_AVG_UNIT_PRICE,Double.class).getSingleResult();
        List<Product> productsByAvg = repo.findByAvgPrice();
        assertTrue(productsByAvg.stream().allMatch(p->p.getUnitprice()>avg));
    }
    @Test
    void findByAvgPriceCategory() {
        List<Object[]> result = em.createQuery(FIND_AVG_CAT_PRICE,Object[].class).getResultList();
        Map<Integer,Double> catAverages = result.stream().collect(Collectors.toMap(r->(Integer)r[0],
                                  r->(Double)r[1] ));
        List<Product> expensiveProducts = repo.findByAvgPriceCategory();
        assertTrue(expensiveProducts.stream().allMatch(p->p.getUnitprice() > catAverages
                .get(p.getCategory().getCategoryId())));
    }
    @Test
    void findByAvgPriceCategory2() {
        List<CategoryAvg> result = em.createQuery(FIND_AVG_CAT_PRICE, CategoryAvg.class).getResultList();
        Map<Integer,Double> catAverages = result.stream().collect(Collectors.toMap(CategoryAvg::getId,CategoryAvg::getAvg));
        List<Product> expensiveProducts = repo.findByAvgPriceCategory();
        assertTrue(expensiveProducts.stream().allMatch(p->p.getUnitprice() > catAverages
                .get(p.getCategory().getCategoryId())));
    }
    @Test
    void findProductsNeverOrdered() {
        //crea la query nativa, casto a Number, e infine estraggo il valore int di questo wrapper
        int notOrderedCount = ((Number)em.createNativeQuery(FIND_NOT_ORDERED_PRODUCT_COUNT).getSingleResult()).intValue();
        List<Product> productsNotOrdered = repo.findProductsNeverOrdered();
        assertEquals(notOrderedCount,productsNotOrdered.size());
    }

    @Test
    void findTop3OrderedProduct() {
        List<Number> topProducts = em.createNativeQuery(FIND_TOP3_ORDERED_PRODUCT, Number.class).getResultList();
        Page<Product> top3Products = repo.findTop3OrderedProduct(PageRequest.of(0, 3));
        assertTrue(top3Products.stream().allMatch(p -> topProducts.stream()
                .anyMatch(n -> n.intValue() == p.getProductId())));
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