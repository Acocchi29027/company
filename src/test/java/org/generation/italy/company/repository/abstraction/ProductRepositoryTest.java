package org.generation.italy.company.repository.abstraction;

import jakarta.persistence.EntityManager;
import org.generation.italy.company.CompanyApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = CompanyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    private EntityManager em;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void findBySupplierCountry() {
    }

    @Test
    void findByUnitpriceGreaterThanAverageUnitprice() {
    }

    @Test
    void findByUnitpriceGreaterThanAverageUnitpriceAndSameCategory() {
    }

    @Test
    void findByNoOrders() {
    }

    @Test
    void findByInThreeMostOrdered() {
    }

    @Test
    void findByOrderEmployee() {
    }
}