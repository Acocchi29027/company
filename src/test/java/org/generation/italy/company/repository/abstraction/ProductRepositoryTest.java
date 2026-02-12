package org.generation.italy.company.repository.abstraction;

import jakarta.persistence.EntityManager;
import org.checkerframework.checker.units.qual.C;
import org.generation.italy.company.CompanyApplication;
import org.generation.italy.company.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = CompanyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private ProductRepository repo;
    private Category category;
    private Supplier supplier;
    private Customer customer;
    private Employee employee;
    private Order order;
    private OrderDetailsId orderDetailsId;
    private OrderDetails orderDetails;
    private Product p1;
    private Product p2;

    private String FIND_AVG_UNITPRICE = """
            SELECT AVG(p.unitprice)
            FROM Product p
            """;
    private String FIND_AVG_UNITPRICE_FOR_CATEGORY = """
            SELECT p.category.categoryId, AVG(p.unitprice)
            FROM Product p
            GROUP BY p.category
            """;
    private String FIND_MOST_ORDERED_ID = """
            SELECT p.productId FROM Product p
            WHERE p.productId IN (
                SELECT o.product.productId FROM OrderDetails o
                GROUP BY o.product.productId
                ORDER BY COUNT(*)
                LIMIT 3
            )
            """;

    @BeforeEach
    void setUp() {
        category = new Category(null, "MyBeverages", "Le MIE bevande");
        supplier = new Supplier(null, "Test_name", "Test_contact", "Test_title",
                "Test_address", "Test_city", "Test_region", "Test_code",
                "Test_country", "Test_phone", "Test_fax");
        p1 = new Product(null, "Coca Cola", supplier, category, 100, false);
        p2 = new Product(null, "Fanta", supplier, category, 90, false);
        customer = new Customer(null, "Test_name", "Test_contact", "Test_title", "Test_address", "Test_city",
                "Test_region", "Test_code", "Test_country", "Test_phone", "Test_fax");
        employee = new Employee(null, "Test_last", "Test_first", "Test_title",
                "Test_court", LocalDateTime.of(1990, 1, 1, 20, 40),
                LocalDateTime.of(2010, 1, 1, 10, 40), "Test_address",
                "Test_city", "Test_region", "Test_code", "Test_country", "Test_phone", null);
        order = new Order(null, customer, employee, LocalDateTime.of(1990, 1, 1, 20, 40),
                LocalDateTime.of(1990, 1, 3, 20, 40),
                LocalDateTime.of(1990, 1, 2, 20, 40), 1, 200, "Test_name", "Test_address",
                "Test_city", "Test_region", "Test_code", "Test_country");
        orderDetailsId = new OrderDetailsId(order.getOrderId(), p1.getProductId());
        orderDetails = new OrderDetails(orderDetailsId, order, p1, p1.getUnitprice(), 3, 0);
        em.persist(employee);
        em.persist(customer);
        em.persist(category);
        em.persist(supplier);
        em.persist(p1);
        em.persist(p2);
        em.persist(order);
        em.persist(orderDetails);
        em.flush();
    }

    @AfterEach
    void tearDown() {
    }

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
    void findByCategoryCategoryName() {
        List<Product> products = repo.findByCategoryCategoryName("MyBeverages");
        assertEquals(2, products.size());
        assertTrue(products.stream().allMatch(p -> List.of("Coca Cola", "Fanta").contains(p.getProductName())));
    }

    @Test
    void findBySupplierCountry() {
        String country = supplier.getCountry();
        List<Product> products = repo.findBySupplierCountry(country);
        assertEquals(2, products.size());
        assertTrue(products.stream().allMatch(p -> p.getSupplier().getCountry().equals(country)));
    }

    @Test
    void findByUnitpriceGreaterThanAverageUnitprice() {
        double avg = em.createQuery(FIND_AVG_UNITPRICE, Double.class).getSingleResult();
        List<Product> products = repo.findByUnitpriceGreaterThanAverageUnitprice();
        assertTrue(products.stream().allMatch(p -> p.getUnitprice() > avg));
    }

    @Test
    void findByUnitpriceGreaterThanAverageUnitpriceAndSameCategory() {
        List<Object[]> result = em.createQuery(FIND_AVG_UNITPRICE_FOR_CATEGORY, Object[].class).getResultList();
        Map<Integer, Double> catAverages = result.stream().collect(Collectors.toMap(r -> (Integer) r[0],
                                                                                  r -> (Double) r[1]));
        List<Product> products = repo.findByUnitpriceGreaterThanAverageUnitpriceAndSameCategory();
        assertTrue(products.stream()
                .allMatch(p -> p.getUnitprice() > catAverages.get(p.getCategory().getCategoryId())));
    }

    @Test
    void findByNoOrders() {
        List<Product> products = repo.findByNoOrders();
        assertTrue(products.contains(p1));
        assertTrue(products.contains(p2));
    }

    @Test
    void findByInThreeMostOrdered() {
        List<Integer> mostOrderedIds = em.createQuery(FIND_MOST_ORDERED_ID, Integer.class).getResultList();
        assertEquals(3, mostOrderedIds.size());
        List<Product> mostOrdered = repo.findByInThreeMostOrdered();
        assertEquals(3, mostOrdered.size());
        assertTrue(mostOrdered.stream().allMatch(p -> mostOrderedIds.contains(p.getProductId())));
    }

    @Test
    void findByOrderEmployee() {
        List<Product> productsByEmployee = repo.findByOrderEmployee(employee);
        assertEquals(1, productsByEmployee.size());
        assertTrue(productsByEmployee.contains(p1));
    }
}