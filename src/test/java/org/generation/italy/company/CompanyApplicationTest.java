package org.generation.italy.company;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test “smoke” di Spring Boot:
 * serve solo a verificare che l'ApplicationContext si avvii correttamente,
 * quindi che:
 * - configurazione Spring sia valida
 * - bean principali vengano creati
 * - non ci siano errori di wiring / component scan
 *
 * Non testa logiche di business: testa solo che l'app parte.
 */
@SpringBootTest
class CompanyApplicationTests {

    @Test
    void contextLoads() {
        // Se il contesto non si carica, il test fallisce prima ancora di arrivare qui.
    }
}