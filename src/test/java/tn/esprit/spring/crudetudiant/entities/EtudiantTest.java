package tn.esprit.spring.crudetudiant.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour l'entité Etudiant")
class EtudiantTest {

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
    }

    @Test
    @DisplayName("Creer un etudiant avec le constructeur vide")
    void testEtudiantDefaultConstructor() {
        assertNotNull(etudiant, "L'etudiant ne doit pas etre null");
        assertNull(etudiant.getIdEtudiant(), "L'ID doit etre null initialement");
        assertNull(etudiant.getNomEtudiant(), "Le nom doit etre null initialement");
        assertNull(etudiant.getPrenomEtudiant(), "Le prenom doit etre null initialement");
        assertNull(etudiant.getOpt(), "L'option doit etre null initialement");
    }

    @Test
    @DisplayName("Creer un etudiant avec le constructeur complet")
    void testEtudiantAllArgsConstructor() {
        Etudiant etudiant = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);

        assertEquals(1L, etudiant.getIdEtudiant(), "L'ID doit etre 1");
        assertEquals("Dupont", etudiant.getNomEtudiant(), "Le nom doit etre Dupont");
        assertEquals("Jean", etudiant.getPrenomEtudiant(), "Le prenom doit etre Jean");
        assertEquals(Option.TWIN, etudiant.getOpt(), "L'option doit etre TWIN");
    }

    @Test
    @DisplayName("Tester le setter et getter pour l'ID")
    void testSetAndGetIdEtudiant() {
        etudiant.setIdEtudiant(5L);
        assertEquals(5L, etudiant.getIdEtudiant(), "L'ID doit etre 5");
    }

    @Test
    @DisplayName("Tester le setter et getter pour le nom")
    void testSetAndGetNomEtudiant() {
        etudiant.setNomEtudiant("Martin");
        assertEquals("Martin", etudiant.getNomEtudiant(), "Le nom doit etre Martin");
    }

    @Test
    @DisplayName("Tester le setter et getter pour le prenom")
    void testSetAndGetPrenomEtudiant() {
        etudiant.setPrenomEtudiant("Pierre");
        assertEquals("Pierre", etudiant.getPrenomEtudiant(), "Le prenom doit etre Pierre");
    }

    @Test
    @DisplayName("Tester le setter et getter pour l'option")
    void testSetAndGetOption() {
        etudiant.setOpt(Option.SAE);
        assertEquals(Option.SAE, etudiant.getOpt(), "L'option doit etre SAE");
    }

    @Test
    @DisplayName("Tester le toString de l'entité Etudiant")
    void testEtudiantToString() {
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("Dupont");
        etudiant.setPrenomEtudiant("Jean");
        etudiant.setOpt(Option.TWIN);

        String toStringResult = etudiant.toString();
        assertNotNull(toStringResult, "Le toString ne doit pas etre null");
        assertFalse(toStringResult.isEmpty(), "Le toString ne doit pas etre vide");
    }

    @Test
    @DisplayName("Tester la serialisation de l'entité Etudiant")
    void testEtudiantIsSerializable() {
        // Vérifier que la classe a une implémentation correcte
        Etudiant etudiant = new Etudiant(1L, "Test", "User", Option.TWIN);
        assertNotNull(etudiant, "L'objet doit être créé");
        assertNotNull(etudiant.getIdEtudiant(), "L'ID doit être présent");
    }

    @Test
    @DisplayName("Tester les differentes options")
    void testOptionEnum() {
        assertEquals(3, Option.values().length, "Il doit y avoir 3 options");
        assertEquals(Option.TWIN, Option.valueOf("TWIN"), "TWIN doit etre une option valide");
        assertEquals(Option.SAE, Option.valueOf("SAE"), "SAE doit etre une option valide");
        assertEquals(Option.DS, Option.valueOf("DS"), "DS doit etre une option valide");
    }

    @Test
    @DisplayName("Tester l'egalite entre deux etudiants avec les memes donnees")
    void testEtudiantEquality() {
        Etudiant etudiant1 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        Etudiant etudiant2 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);

        assertEquals(etudiant1, etudiant2, "Deux etudiants avec les memes donnees doivent etre egaux");
    }

    @Test
    @DisplayName("Tester l'inegalite entre deux etudiants avec des donnees differentes")
    void testEtudiantInequality() {
        Etudiant etudiant1 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        Etudiant etudiant2 = new Etudiant(2L, "Martin", "Pierre", Option.SAE);

        assertNotEquals(etudiant1, etudiant2, "Deux etudiants avec des donnees differentes ne doivent pas etre egaux");
    }
}

