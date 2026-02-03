package tn.esprit.spring.crudetudiant.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.crudetudiant.entities.Etudiant;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour l'interface EtudiantRepository")
class EtudiantRepositoryInterfaceTest {

    @Test
    @DisplayName("Vérifier que EtudiantRepository existe")
    void testRepositoryExists() {
        assertNotNull(EtudiantRepository.class);
    }

    @Test
    @DisplayName("Vérifier les méthodes du repository")
    void testRepositoryMethods() {
        // Vérifier que les méthodes héritées de CrudRepository existent
        assertDoesNotThrow(() -> {
            EtudiantRepository.class.getMethod("findById", Object.class);
            EtudiantRepository.class.getMethod("findAll");
            EtudiantRepository.class.getMethod("count");
            EtudiantRepository.class.getMethod("existsById", Object.class);
        });
    }

    @Test
    @DisplayName("Vérifier que le repository est une interface")
    void testRepositoryIsInterface() {
        assertTrue(EtudiantRepository.class.isInterface());
    }
}

