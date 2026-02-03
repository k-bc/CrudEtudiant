package tn.esprit.spring.crudetudiant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour la classe CrudEtudiantApplication")
class CrudEtudiantApplicationTest {

    @Test
    @DisplayName("La classe CrudEtudiantApplication doit exister")
    void testClassExists() {
        try {
            Class.forName("tn.esprit.spring.crudetudiant.CrudEtudiantApplication");
            assertTrue(true, "La classe CrudEtudiantApplication existe");
        } catch (ClassNotFoundException e) {
            fail("La classe CrudEtudiantApplication n'a pas été trouvée: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("La classe CrudEtudiantApplication doit avoir la bonne structure")
    void testApplicationStructure() {
        assertTrue(CrudEtudiantApplication.class.isAnnotationPresent(
                org.springframework.boot.autoconfigure.SpringBootApplication.class),
                "La classe doit avoir l'annotation @SpringBootApplication");
    }

    @Test
    @DisplayName("La classe CrudEtudiantApplication peut être instantiée")
    void testApplicationCanBeInstantiated() {
        assertDoesNotThrow(() -> {
            CrudEtudiantApplication app = new CrudEtudiantApplication();
            assertNotNull(app, "L'instance de CrudEtudiantApplication ne doit pas être null");
        }, "La classe CrudEtudiantApplication doit pouvoir être instanciée");
    }

    @Test
    @DisplayName("La classe CrudEtudiantApplication doit avoir une méthode main")
    void testMainMethodExists() {
        assertDoesNotThrow(() -> {
            CrudEtudiantApplication.class.getMethod("main", String[].class);
        }, "La classe doit avoir une méthode main");
    }
}

