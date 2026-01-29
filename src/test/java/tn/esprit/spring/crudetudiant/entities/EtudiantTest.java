package tn.esprit.spring.crudetudiant.entities;
}
    }
        assertNotEquals(etudiant1, etudiant2, "Deux étudiants avec des données différentes ne doivent pas être égaux");

        Etudiant etudiant2 = new Etudiant(2L, "Martin", "Pierre", Option.SAE);
        Etudiant etudiant1 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
    void testEtudiantInequality() {
    @DisplayName("Tester l'inégalité entre deux étudiants avec des données différentes")
    @Test

    }
        assertEquals(etudiant1, etudiant2, "Deux étudiants avec les mêmes données doivent être égaux");

        Etudiant etudiant2 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        Etudiant etudiant1 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
    void testEtudiantEquality() {
    @DisplayName("Tester l'égalité entre deux étudiants avec les mêmes données")
    @Test

    }
        assertEquals(Option.DS, Option.valueOf("DS"), "DS doit être une option valide");
        assertEquals(Option.SAE, Option.valueOf("SAE"), "SAE doit être une option valide");
        assertEquals(Option.TWIN, Option.valueOf("TWIN"), "TWIN doit être une option valide");
        assertEquals(3, Option.values().length, "Il doit y avoir 3 options");
    void testOptionEnum() {
    @DisplayName("Tester les différentes options")
    @Test

    }
                "L'entité Etudiant doit implémenter Serializable");
        assertTrue(java.io.Serializable.class.isAssignableFrom(Etudiant.class),
    void testEtudiantIsSerializable() {
    @DisplayName("Tester la sérialisation de l'entité Etudiant")
    @Test

    }
        assertFalse(toStringResult.isEmpty(), "Le toString ne doit pas être vide");
        assertNotNull(toStringResult, "Le toString ne doit pas être null");
        String toStringResult = etudiant.toString();

        etudiant.setOpt(Option.TWIN);
        etudiant.setPrenomEtudiant("Jean");
        etudiant.setNomEtudiant("Dupont");
        etudiant.setIdEtudiant(1L);
    void testEtudiantToString() {
    @DisplayName("Tester le toString de l'entité Etudiant")
    @Test

    }
        assertEquals(Option.SAE, etudiant.getOpt(), "L'option doit être SAE");
        etudiant.setOpt(Option.SAE);
    void testSetAndGetOption() {
    @DisplayName("Tester le setter et getter pour l'option")
    @Test

    }
        assertEquals("Pierre", etudiant.getPrenomEtudiant(), "Le prénom doit être Pierre");
        etudiant.setPrenomEtudiant("Pierre");
    void testSetAndGetPrenomEtudiant() {
    @DisplayName("Tester le setter et getter pour le prénom")
    @Test

    }
        assertEquals("Martin", etudiant.getNomEtudiant(), "Le nom doit être Martin");
        etudiant.setNomEtudiant("Martin");
    void testSetAndGetNomEtudiant() {
    @DisplayName("Tester le setter et getter pour le nom")
    @Test

    }
        assertEquals(5L, etudiant.getIdEtudiant(), "L'ID doit être 5");
        etudiant.setIdEtudiant(5L);
    void testSetAndGetIdEtudiant() {
    @DisplayName("Tester le setter et getter pour l'ID")
    @Test

    }
        assertEquals(Option.TWIN, etudiant.getOpt(), "L'option doit être TWIN");
        assertEquals("Jean", etudiant.getPrenomEtudiant(), "Le prénom doit être Jean");
        assertEquals("Dupont", etudiant.getNomEtudiant(), "Le nom doit être Dupont");
        assertEquals(1L, etudiant.getIdEtudiant(), "L'ID doit être 1");

        Etudiant etudiant = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
    void testEtudiantAllArgsConstructor() {
    @DisplayName("Créer un étudiant avec le constructeur complet")
    @Test

    }
        assertNull(etudiant.getOpt(), "L'option doit être null initialement");
        assertNull(etudiant.getPrenomEtudiant(), "Le prénom doit être null initialement");
        assertNull(etudiant.getNomEtudiant(), "Le nom doit être null initialement");
        assertNull(etudiant.getIdEtudiant(), "L'ID doit être null initialement");
        assertNotNull(etudiant, "L'étudiant ne doit pas être null");
    void testEtudiantDefaultConstructor() {
    @DisplayName("Créer un étudiant avec le constructeur vide")
    @Test

    }
        etudiant = new Etudiant();
    void setUp() {
    @BeforeEach

    private Etudiant etudiant;

class EtudiantTest {
@DisplayName("Tests pour l'entité Etudiant")

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


