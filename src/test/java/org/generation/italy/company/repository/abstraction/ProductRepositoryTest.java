package org.generation.italy.company.repository.abstraction;

import jakarta.persistence.EntityManager;
import org.generation.italy.company.CompanyApplication;
import org.generation.italy.company.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = CompanyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    // =========================================================
    // DIPENDENZE
    // =========================================================
    @Autowired
    private EntityManager em;

    @Autowired
    private ProductRepository repo;

    // =========================================================
    // DATASET INDIPENDENTE (creato da noi) - condiviso tra i test
    // =========================================================
    private Category catDrinks;
    private Category catSnacks;

    private Supplier supplierIT;

    private Product product1; // Coca-Cola (drinks, price 12, not discontinued)
    private Product product2; // Fanta     (drinks, price 4,  not discontinued)
    private Product product3; // Chips     (snacks, price 10, not discontinued)
    private Product product4; // Candy     (snacks, price 2,  discontinued = true)

    private static final String CAT_DRINKS = "DRINKS";
    private static final String CAT_SNACKS = "SNACKS";
    private static final String SUPP_COUNTRY = "IT";

    // =========================================================
    // QUERY “PROF-STYLE” DI SUPPORTO (per expected in alcuni assert)
    // =========================================================
    private static final String JPQL_AVG_ALL = "SELECT AVG(p.unitprice) FROM Product p";

    // categoryId, avg(unitprice) -> torna Object[] per ogni riga
    private static final String JPQL_AVG_BY_CATEGORY = """
            SELECT p.category.categoryId, AVG(p.unitprice)
            FROM Product p
            GROUP BY p.category.categoryId
            """;

    @BeforeEach
    void setup() {

        // =========================
        // ARRANGE (setup comune): creo entità minime e coerenti
        // =========================

        catDrinks = new Category(null, CAT_DRINKS, "Beverages");
        catSnacks = new Category(null, CAT_SNACKS, "Food");

        supplierIT = new Supplier(
                null,
                "SUPP_TEST",
                "CONTACT_TEST",
                "TITLE_TEST",
                "ADDR_TEST",
                "CITY_TEST",
                "REGION_TEST",
                "ZIP_TEST",
                SUPP_COUNTRY,
                "PHONE_TEST",
                "FAX_TEST"
        );

        // Prezzi scelti apposta per rendere i test “chiari”:
        // - Drinks: (12 + 4) / 2 = 8  -> sopra media: Coca-Cola
        // - Snacks: (10 + 2) / 2 = 6 -> sopra media: Chips
        product1 = new Product(null, "Coca-Cola", supplierIT, catDrinks, 12, false);
        product2 = new Product(null, "Fanta",     supplierIT, catDrinks, 4,  false);
        product3 = new Product(null, "Chips",     supplierIT, catSnacks, 10, false);
        product4 = new Product(null, "Candy",     supplierIT, catSnacks, 2,  true);

        em.persist(catDrinks);
        em.persist(catSnacks);
        em.persist(supplierIT);

        em.persist(product1);
        em.persist(product2);
        em.persist(product3);
        em.persist(product4);

        // flush() => forza INSERT reali sul DB ora (così le query dei test vedono i dati)
        em.flush();
    }

    // =========================================================
    // ES 1 - findByProductNameAndIsDiscontinued
    // =========================================================
    @Test
    void findByProductNameAndIsDiscontinued() {

        // =========================
        // ARRANGE
        // =========================
        String token = "co";      // deve matchare "Coca-Cola"
        boolean discontinued = false;

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findByProductNameAndIsDiscontinued(token, discontinued);

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);
        assertTrue(result.stream().allMatch(p -> !p.isDiscontinued()));
        assertTrue(result.stream().allMatch(p -> p.getProductName().toLowerCase().contains(token.toLowerCase())));

        Set<Integer> ids = result.stream().map(Product::getProductId).collect(Collectors.toSet());
        assertTrue(ids.contains(product1.getProductId()));
        assertFalse(ids.contains(product4.getProductId()));
    }

    // =========================================================
    // ES 2 - findAvailableProductsInPriceRange
    // =========================================================
    @Test
    void findAvailableProductsInPriceRange() {

        // =========================
        // ARRANGE
        // =========================
        double min = 5;
        double max = 12;

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findAvailableProductsInPriceRange(min, max);

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);
        assertTrue(result.stream().allMatch(p -> p.getUnitprice() >= min && p.getUnitprice() <= max));
        assertTrue(result.stream().allMatch(p -> !p.isDiscontinued()));

        Set<Integer> ids = result.stream().map(Product::getProductId).collect(Collectors.toSet());
        assertTrue(ids.contains(product1.getProductId()));
        assertTrue(ids.contains(product3.getProductId()));
        assertFalse(ids.contains(product2.getProductId()));
        assertFalse(ids.contains(product4.getProductId()));
    }

    // =========================================================
    // ES 3 - findByProductNameContaining
    // =========================================================
    @Test
    void findByProductNameContaining() {

        // =========================
        // ARRANGE
        // =========================
        String token = "a"; // matcha Coca-Cola, Fanta, Candy

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findByProductNameContaining(token);

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);
        assertTrue(result.stream().allMatch(p -> p.getProductName().toLowerCase().contains(token)));

        Set<Integer> ids = result.stream().map(Product::getProductId).collect(Collectors.toSet());
        assertTrue(ids.contains(product1.getProductId()));
        assertTrue(ids.contains(product2.getProductId()));
        assertTrue(ids.contains(product4.getProductId()));
    }

    // =========================================================
    // ES 4 - findByDiscontinued
    // =========================================================
    @Test
    void findByDiscontinued() {

        // =========================
        // ARRANGE
        // =========================
        boolean discontinued = false;

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findByDiscontinued(discontinued);

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);
        assertTrue(result.stream().allMatch(p -> !p.isDiscontinued()));

        Set<Integer> ids = result.stream().map(Product::getProductId).collect(Collectors.toSet());
        assertTrue(ids.contains(product1.getProductId()));
        assertTrue(ids.contains(product2.getProductId()));
        assertTrue(ids.contains(product3.getProductId()));
        assertFalse(ids.contains(product4.getProductId()));
    }

    // =========================================================
    // ES 1 (derivata) - findByCategoryCategoryName
    // =========================================================
    @Test
    void findByCategoryCategoryName() {

        // =========================
        // ARRANGE
        // =========================
        String categoryName = CAT_DRINKS;

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findByCategoryCategoryName(categoryName);

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);
        assertTrue(result.stream().allMatch(p -> p.getCategory() != null));
        assertTrue(result.stream().allMatch(p -> categoryName.equalsIgnoreCase(p.getCategory().getCategoryname())));

        Set<Integer> ids = result.stream().map(Product::getProductId).collect(Collectors.toSet());
        assertEquals(Set.of(product1.getProductId(), product2.getProductId()), ids);
    }

    // =========================================================
    // ES 2 (derivata) - findBySupplierCountry
    // =========================================================
    @Test
    void findBySupplierCountry() {

        // =========================
        // ARRANGE
        // =========================
        String country = SUPP_COUNTRY;

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findBySupplierCountry(country);

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);
        assertTrue(result.stream().allMatch(p -> p.getSupplier() != null));
        assertTrue(result.stream().allMatch(p -> country.equalsIgnoreCase(p.getSupplier().getCountry())));

        Set<Integer> ids = result.stream().map(Product::getProductId).collect(Collectors.toSet());
        assertTrue(ids.containsAll(Set.of(
                product1.getProductId(), product2.getProductId(), product3.getProductId(), product4.getProductId()
        )));
    }

    // =========================================================
    // ES 3 - findProductsCostingMoreThanAverage
    // =========================================================
    @Test
    void findProductsCostingMoreThanAverage() {

        // =========================
        // ARRANGE: expected “prof-style” calcolato dal DB con JPQL
        // =========================
        Double avg = em.createQuery(JPQL_AVG_ALL, Double.class).getSingleResult();
        assertNotNull(avg);

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findProductsCostingMoreThanAverage();

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);
        assertTrue(result.stream().allMatch(p -> p.getUnitprice() > avg));

        Set<Integer> ids = result.stream().map(Product::getProductId).collect(Collectors.toSet());
        assertEquals(Set.of(product1.getProductId(), product3.getProductId()), ids);
    }

    // =========================================================
    // ES 4 - findProductsCostingMoreThanCategoryAverage
    // =========================================================
    @Test
    void findProductsCostingMoreThanCategoryAverage() {

        // =========================
        // ARRANGE: expected “prof-style” con query multi-colonna -> List<Object[]>
        // =========================

        // La query seleziona 2 colonne:
        // 1) categoryId
        // 2) AVG(unitprice)
        //
        // Quando una query NON ritorna un'entità (Product) ma più "valori scalari",
        // JPA usa la forma "riga = Object[]".
        // - r[0] => categoria
        // - r[1] => media
        List<Object[]> rows = em.createQuery(JPQL_AVG_BY_CATEGORY, Object[].class).getResultList();

        // Trasformo la lista di righe in una Map per lookup veloce:
        // categoryId -> avgPrice
        Map<Integer, Double> avgByCategory = rows.stream().collect(Collectors.toMap(
                r -> (Integer) r[0],
                r -> (Double) r[1]
        ));

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findProductsCostingMoreThanCategoryAverage();

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);

        // Ogni prodotto deve essere > media della sua categoria (lookup via Map)
        assertTrue(result.stream().allMatch(p ->
                p.getUnitprice() > avgByCategory.get(p.getCategory().getCategoryId())
        ));

        Set<Integer> ids = result.stream().map(Product::getProductId).collect(Collectors.toSet());
        assertEquals(Set.of(product1.getProductId(), product3.getProductId()), ids);
    }

    // =========================================================
    // ES 5 - findProductsNeverOrdered
    // =========================================================
    @Test
    void findProductsNeverOrdered() {

        // =========================
        // ARRANGE: creo UN ordine con UN dettaglio per “marcare” un prodotto come ordinato
        // =========================
        Employee e1 = buildEmployee("Rossi", "Mario");
        em.persist(e1);

        Order o1 = buildOrder(e1, LocalDateTime.now().minusDays(2));
        em.persist(o1);

        // flush: serve per generare orderId (PK) prima di creare l'EmbeddedId di OrderDetails
        em.flush();

        // Ordino SOLO product1
        persistOrderDetail(o1, product1, 2);

        em.flush();

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findProductsNeverOrdered();

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);

        Set<Integer> ids = result.stream().map(Product::getProductId).collect(Collectors.toSet());

        assertFalse(ids.contains(product1.getProductId())); // è stato ordinato
        assertTrue(ids.contains(product2.getProductId()));
        assertTrue(ids.contains(product3.getProductId()));
        assertTrue(ids.contains(product4.getProductId()));
    }

    // =========================================================
    // ES 6 - findMostFrequentlyOrderedProducts(Pageable)
    // =========================================================
    @Test
    void findMostFrequentlyOrderedProducts() {

        // =========================
        // ARRANGE: creo dati per far sì che SUM(qty) generi un ranking chiaro
        // =========================
        Employee e1 = buildEmployee("Rossi", "Mario");
        em.persist(e1);

        Order o1 = buildOrder(e1, LocalDateTime.now().minusDays(2));
        Order o2 = buildOrder(e1, LocalDateTime.now().minusDays(1));
        em.persist(o1);
        em.persist(o2);

        // flush: ottengo orderId (auto-generati) e posso costruire OrderDetailsId
        em.flush();

        // Totali qty:
        // product1: 2 + 6 = 8  -> top1
        // product2: 5          -> top2
        // product3: 1          -> top3
        persistOrderDetail(o1, product1, 2);
        persistOrderDetail(o2, product1, 6);

        persistOrderDetail(o1, product2, 5);

        persistOrderDetail(o2, product3, 1);

        em.flush();

        // =========================
        // ARRANGE (prof-style): calcolo l'EXPECTED direttamente dal DB con una NATIVE QUERY
        // =========================
        // Perché native query?
        // - voglio “vedere” esattamente cosa c’è nella tabella orderdetails
        // - e soprattutto voglio ottenere una lista di ID numerici (productid)
        //
        // Perché List<Number>?
        // - in una native query JDBC il tipo numerico può variare:
        //   Integer / Long / BigInteger... dipende dal driver e dal DB
        // - Number è la superclasse comune; poi userò intValue() per confrontare.
        List<Number> expectedTopProductIds = em.createNativeQuery("""
                        SELECT od.productid
                        FROM orderdetails od
                        GROUP BY od.productid
                        ORDER BY SUM(od.qty) DESC
                        LIMIT 3
                        """, Number.class)
                .getResultList();

        // Sanity check: devo avere esattamente 3 righe, perché nel dataset ho 3 prodotti ordinati
        assertEquals(3, expectedTopProductIds.size());

        // =========================
        // ACT: chiamo il repository con Pageable (= LIMIT in SQL)
        // =========================
        List<Product> top3 = repo.findMostFrequentlyOrderedProducts(PageRequest.of(0, 3));

        // =========================
        // ASSERT: confronto “prof-style” tra IDs attesi e prodotti ritornati dal repo
        // =========================
        assertNotNull(top3);
        assertEquals(3, top3.size());

        // Trasformo List<Number> -> List<Integer> per confrontare facilmente con i ProductId
        // intValue(): conversione sicura a int indipendentemente dal tipo numerico concreto
        List<Integer> expected = expectedTopProductIds.stream()
                .map(Number::intValue)
                .toList();

        List<Integer> actual = top3.stream()
                .map(Product::getProductId)
                .toList();

        // Verifico che l'ordine coincida (ranking top-N)
        assertEquals(expected, actual);
    }

    // =========================================================
    // ES 7 - findProductsByEmployee(employeeId)
    // =========================================================
    @Test
    void findProductsByEmployee() {

        // =========================
        // ARRANGE: 2 employee con ordini diversi => risultati separati
        // =========================
        Employee e1 = buildEmployee("Rossi", "Mario");
        Employee e2 = buildEmployee("Bianchi", "Luigi");
        em.persist(e1);
        em.persist(e2);

        Order o1 = buildOrder(e1, LocalDateTime.now().minusDays(3));
        Order o2 = buildOrder(e2, LocalDateTime.now().minusDays(3));
        em.persist(o1);
        em.persist(o2);

        em.flush();

        // e1 ordina product1 e product2
        persistOrderDetail(o1, product1, 2);
        persistOrderDetail(o1, product2, 1);

        // e2 ordina product3
        persistOrderDetail(o2, product3, 4);

        em.flush();

        Set<Integer> expectedE1 = Set.of(product1.getProductId(), product2.getProductId());

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findProductsByEmployee(e1.getEmpId());

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);

        Set<Integer> actual = result.stream().map(Product::getProductId).collect(Collectors.toSet());
        assertEquals(expectedE1, actual);
        assertFalse(actual.contains(product3.getProductId()));
    }

    // =========================================================
    // ES 8 - findProductsNotOrderedSince(since)
    // =========================================================
    @Test
    void findProductsNotOrderedSince() {

        // =========================
        // ARRANGE
        // =========================
        Employee e1 = buildEmployee("Rossi", "Mario");
        em.persist(e1);

        LocalDateTime since = LocalDate.now().minusDays(10).atStartOfDay();

        // Ordine vecchio (30gg fa) e recente (>= since)
        Order oldOrder = buildOrder(e1, since.minusDays(20));    // sicuramente < since
        Order recentOrder = buildOrder(e1, since.plusDays(1));   // sicuramente >= since
        em.persist(oldOrder);
        em.persist(recentOrder);

        em.flush();

        // product1 ordinato recentemente => NON deve comparire
        persistOrderDetail(recentOrder, product1, 1);

        // product2 ordinato solo vecchio => deve comparire
        persistOrderDetail(oldOrder, product2, 1);

        // product3 mai ordinato => deve comparire

        em.flush();

        Set<Integer> expectedMustContain = Set.of(product2.getProductId(), product3.getProductId());

        // =========================
        // ACT
        // =========================
        List<Product> result = repo.findProductsNotOrderedSince(since);

        // =========================
        // ASSERT
        // =========================
        assertNotNull(result);

        Set<Integer> actual = result.stream().map(Product::getProductId).collect(Collectors.toSet());
        assertTrue(actual.containsAll(expectedMustContain));
        assertFalse(actual.contains(product1.getProductId()));
    }

    // =========================================================
    // HELPER METHODS (per mantenere AAA pulito e test leggibili)
    // =========================================================

    private Employee buildEmployee(String lastName, String firstName) {
        // Creo un employee “minimo”: l’ID lo genera il DB (IDENTITY).
        Employee e = new Employee();
        e.setLastname(lastName);
        e.setFirstname(firstName);
        e.setTitle("Developer");
        e.setTitleOfCourtesy("Mr");
        e.setBirthdate(LocalDateTime.now().minusYears(25));
        e.setHiredate(LocalDateTime.now().minusYears(1));
        e.setAddress("Test Address");
        e.setCity("Test City");
        e.setRegion("Test Region");
        e.setPostalcode("00000");
        e.setCountry("IT");
        e.setPhone("000-000");
        e.setManager(null);
        return e;
    }

    private Order buildOrder(Employee employee, LocalDateTime orderDate) {
        // Creo un Order “minimo”.
        // custId: nel tuo entity è nullable, ma se il DB ha vincolo NOT NULL su custid,
        // qui fallirà l'INSERT. In quel caso serve creare anche un Customer.
        Order o = new Order();
        o.setCustId(null);
        o.setEmpId(employee);

        // orderDate è LocalDateTime; il repo usa LocalDate per "since".
        // Noi settiamo orari coerenti rispetto alla soglia (>= since.atStartOfDay()).
        o.setOrderDate(orderDate);

        o.setRequiredDate(orderDate.plusDays(7));
        o.setShippedDate(orderDate.plusDays(1));
        o.setFreight(10.0);

        o.setShipName("Test Ship");
        o.setShipAddress("Test Address");
        o.setShipCity("Test City");
        o.setShipRegion("Test Region");
        o.setShipPostalCode("00000");
        o.setShipCountry("IT");
        return o;
    }

    private void persistOrderDetail(Order order, Product product, int qty) {
        // OrderDetails usa:
        // - @EmbeddedId (PK composta)
        // - @MapsId per “agganciare” orderId e productId nell'EmbeddedId
        //
        // Quindi: order.getOrderId() e product.getProductId() devono essere già valorizzati
        // => è per questo che nei test facciamo em.flush() prima di creare OrderDetails.
        OrderDetails od = new OrderDetails();
        od.setOrder(order);
        od.setProduct(product);

        // campi coerenti (non sono la base dei tuoi metodi, ma devono essere consistenti)
        od.setUnitprice(product.getUnitprice());
        od.setQty(qty);
        od.setDiscount(0.0);

        // FIX: OrderDetailsId senza costruttore con argomenti -> uso costruttore vuoto + setter
        OrderDetailsId id = new OrderDetailsId();
        id.setOrderId(order.getOrderId());
        id.setProductId(product.getProductId());
        od.setId(id);

        em.persist(od);
    }
}