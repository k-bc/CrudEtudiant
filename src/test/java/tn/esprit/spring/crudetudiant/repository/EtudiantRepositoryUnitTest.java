package tn.esprit.spring.crudetudiant.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests unitaires pour le repository EtudiantRepository")
class EtudiantRepositoryUnitTest {

    @Test
    @DisplayName("Le repository EtudiantRepository doit exister")
    void testRepositoryExists() {
        try {
            Class.forName("tn.esprit.spring.crudetudiant.repository.EtudiantRepository");
            assertTrue(true, "Le repository EtudiantRepository existe");
        } catch (ClassNotFoundException e) {
            fail("Le repository EtudiantRepository n'a pas été trouvé: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Le repository doit être une interface")
    void testRepositoryIsInterface() {
        assertTrue(EtudiantRepository.class.isInterface(),
                "EtudiantRepository doit être une interface");
    }

    @Test
    @DisplayName("Le repository doit étendre JpaRepository")
    void testRepositoryExtendsJpaRepository() {
        assertTrue(org.springframework.data.jpa.repository.JpaRepository.class
                .isAssignableFrom(EtudiantRepository.class),
                "EtudiantRepository doit étendre JpaRepository");
    }

    @Test
    @DisplayName("Vérifier que le repository a les bonnes interfaces parentes")
    void testRepositoryInterfaces() {
        Class<?>[] interfaces = EtudiantRepository.class.getInterfaces();
        assertTrue(interfaces.length > 0, "Le repository doit implémenter au moins une interface");
    }
}

