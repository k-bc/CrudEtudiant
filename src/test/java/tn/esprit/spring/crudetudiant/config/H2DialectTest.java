package tn.esprit.spring.crudetudiant.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class H2DialectTest {

    @Test
    void testH2DialectConfiguration() {
        // Ce test vérifie simplement que le contexte Spring se charge
        // Si le contexte se charge, cela signifie que H2 a créé les tables correctement
        assertNotNull(this, "Test should pass if context loads");
    }
}

