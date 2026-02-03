package tn.esprit.spring.crudetudiant.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests complémentaires pour l'entité Etudiant")
class EtudiantEntityTest {

    @Test
    @DisplayName("L'entité Etudiant doit avoir les annotations Lombok correctes")
    void testEtudiantAnnotations() {
        assertTrue(Etudiant.class.isAnnotationPresent(javax.persistence.Entity.class),
                "La classe doit avoir l'annotation @Entity");

        // Vérifier que les getters existent (ce qui prouve que @Getter a fonctionné)
        assertDoesNotThrow(() -> {
            Etudiant.class.getDeclaredMethod("getIdEtudiant");
            Etudiant.class.getDeclaredMethod("getNomEtudiant");
            Etudiant.class.getDeclaredMethod("getPrenomEtudiant");
            Etudiant.class.getDeclaredMethod("getOpt");
        }, "La classe doit avoir les getters générés par @Getter");

        // Vérifier que les setters existent (ce qui prouve que @Setter a fonctionné)
        assertDoesNotThrow(() -> {
            Etudiant.class.getDeclaredMethod("setIdEtudiant", Long.class);
            Etudiant.class.getDeclaredMethod("setNomEtudiant", String.class);
            Etudiant.class.getDeclaredMethod("setPrenomEtudiant", String.class);
            Etudiant.class.getDeclaredMethod("setOpt", Option.class);
        }, "La classe doit avoir les setters générés par @Setter");

        // Vérifier que le constructeur sans arguments existe (généré par @NoArgsConstructor)
        assertDoesNotThrow(() -> {
            Etudiant.class.getDeclaredConstructor();
        }, "La classe doit avoir un constructeur sans arguments");

        // Vérifier que le constructeur avec tous les arguments existe (généré par @AllArgsConstructor)
        assertDoesNotThrow(() -> {
            Etudiant.class.getDeclaredConstructor(Long.class, String.class, String.class, Option.class);
        }, "La classe doit avoir un constructeur avec tous les arguments");
    }

    @Test
    @DisplayName("Vérifier que les getters et setters fonctionnent avec Lombok")
    void testLombokGeneratedMethods() {
        Etudiant etudiant = new Etudiant();

        // Tester les setters et getters générés par Lombok
        etudiant.setIdEtudiant(1L);
        assertEquals(1L, etudiant.getIdEtudiant(), "Le setter/getter pour ID doit fonctionner");

        etudiant.setNomEtudiant("Test");
        assertEquals("Test", etudiant.getNomEtudiant(), "Le setter/getter pour Nom doit fonctionner");

        etudiant.setPrenomEtudiant("User");
        assertEquals("User", etudiant.getPrenomEtudiant(), "Le setter/getter pour Prenom doit fonctionner");

        etudiant.setOpt(Option.TWIN);
        assertEquals(Option.TWIN, etudiant.getOpt(), "Le setter/getter pour Option doit fonctionner");
    }

    @Test
    @DisplayName("Vérifier le ToString généré par Lombok")
    void testLombokToString() {
        Etudiant etudiant = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        String toString = etudiant.toString();

        assertNotNull(toString, "Le toString ne doit pas être null");
        assertTrue(toString.contains("Dupont") || toString.contains("1"),
                "Le toString doit contenir les données de l'objet");
    }

    @Test
    @DisplayName("Vérifier l'ID de l'entité avec l'annotation @Id")
    void testEntityId() {
        try {
            java.lang.reflect.Field idField = Etudiant.class.getDeclaredField("idEtudiant");
            assertTrue(idField.isAnnotationPresent(javax.persistence.Id.class),
                    "Le champ idEtudiant doit avoir l'annotation @Id");
        } catch (NoSuchFieldException e) {
            fail("Le champ idEtudiant doit exister");
        }
    }

    @Test
    @DisplayName("Vérifier la génération d'ID avec @GeneratedValue")
    void testEntityGeneratedValue() {
        try {
            java.lang.reflect.Field idField = Etudiant.class.getDeclaredField("idEtudiant");
            assertTrue(idField.isAnnotationPresent(javax.persistence.GeneratedValue.class),
                    "Le champ idEtudiant doit avoir l'annotation @GeneratedValue");
        } catch (NoSuchFieldException e) {
            fail("Le champ idEtudiant doit exister");
        }
    }

    @Test
    @DisplayName("Vérifier que l'entité Etudiant peut être utilisée comme entité JPA")
    void testEtudiantAsJpaEntity() {
        assertTrue(Etudiant.class.isAnnotationPresent(javax.persistence.Entity.class),
                "La classe doit être une entité JPA");

        try {
            Etudiant.class.getDeclaredField("idEtudiant");
            assertTrue(true, "La classe doit avoir un champ ID");
        } catch (NoSuchFieldException e) {
            fail("La classe doit avoir un champ ID pour être une entité JPA");
        }
    }

    @Test
    @DisplayName("Tester les constructeurs de l'entité")
    void testEtudiantConstructors() {
        // Tester le constructeur sans arguments
        Etudiant etudiant1 = new Etudiant();
        assertNotNull(etudiant1, "Le constructeur sans arguments doit fonctionner");

        // Tester le constructeur avec tous les arguments
        Etudiant etudiant2 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        assertNotNull(etudiant2, "Le constructeur avec tous les arguments doit fonctionner");
        assertEquals(1L, etudiant2.getIdEtudiant(), "L'ID doit être défini correctement");
        assertEquals("Dupont", etudiant2.getNomEtudiant(), "Le nom doit être défini correctement");
        assertEquals("Jean", etudiant2.getPrenomEtudiant(), "Le prénom doit être défini correctement");
        assertEquals(Option.TWIN, etudiant2.getOpt(), "L'option doit être définie correctement");
    }

    @Test
    @DisplayName("Tester que l'option peut être null")
    void testEtudiantWithNullOption() {
        Etudiant etudiant = new Etudiant(1L, "Dupont", "Jean", null);
        assertNull(etudiant.getOpt(), "L'option peut être null");

        etudiant.setOpt(Option.TWIN);
        assertEquals(Option.TWIN, etudiant.getOpt(), "L'option doit pouvoir être définie");
    }
}

