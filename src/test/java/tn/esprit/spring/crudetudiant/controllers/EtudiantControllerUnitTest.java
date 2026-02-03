package tn.esprit.spring.crudetudiant.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests unitaires pour le contrôleur EtudiantController")
class EtudiantControllerUnitTest {

    @Test
    @DisplayName("Le contrôleur EtudiantController doit exister")
    void testControllerExists() {
        try {
            Class.forName("tn.esprit.spring.crudetudiant.controllers.EtudiantController");
            assertTrue(true, "Le contrôleur EtudiantController existe");
        } catch (ClassNotFoundException e) {
            fail("Le contrôleur EtudiantController n'a pas été trouvé: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Le contrôleur doit avoir l'annotation @RestController")
    void testControllerHasRestControllerAnnotation() {
        assertTrue(EtudiantController.class.isAnnotationPresent(
                org.springframework.web.bind.annotation.RestController.class),
                "Le contrôleur doit avoir l'annotation @RestController");
    }

    @Test
    @DisplayName("Le contrôleur doit avoir l'annotation @AllArgsConstructor")
    void testControllerHasAllArgsConstructorAnnotation() {
        // Vérifier que le contrôleur a un constructeur avec un paramètre (IEtudiant)
        // ce qui prouve que @AllArgsConstructor a fonctionné
        assertDoesNotThrow(() -> {
            EtudiantController.class.getDeclaredConstructor(
                    tn.esprit.spring.crudetudiant.services.IEtudiant.class);
        }, "Le contrôleur doit avoir un constructeur avec paramètre IEtudiant (généré par @AllArgsConstructor)");
    }

    @Test
    @DisplayName("Le contrôleur peut être instancié")
    void testControllerCanBeInstantiated() {
        assertDoesNotThrow(() -> {
            EtudiantController controller = new EtudiantController(null);
            assertNotNull(controller, "L'instance du contrôleur ne doit pas être null");
        }, "Le contrôleur doit pouvoir être instancié");
    }

    @Test
    @DisplayName("Vérifier les méthodes du contrôleur")
    void testControllerMethods() {
        assertDoesNotThrow(() -> {
            EtudiantController.class.getDeclaredMethod("afficherAllEtudiant");
            EtudiantController.class.getDeclaredMethod("afficherEtudiantByID", Long.class);
            EtudiantController.class.getDeclaredMethod("ajouterEtudiant",
                    tn.esprit.spring.crudetudiant.entities.Etudiant.class);
            EtudiantController.class.getDeclaredMethod("modifierEtudiant",
                    tn.esprit.spring.crudetudiant.entities.Etudiant.class);
            EtudiantController.class.getDeclaredMethod("supprimerEtudiant", long.class);
        }, "Toutes les méthodes du contrôleur doivent exister");
    }
}

