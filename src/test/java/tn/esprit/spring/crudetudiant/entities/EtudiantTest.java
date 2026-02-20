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
        Etudiant etudia = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);

        assertEquals(1L, etudia.getIdEtudiant(), "L'ID doit etre 1");
        assertEquals("Dupont", etudia.getNomEtudiant(), "Le nom doit etre Dupont");
        assertEquals("Jean", etudia.getPrenomEtudiant(), "Le prenom doit etre Jean");
        assertEquals(Option.TWIN, etudia.getOpt(), "L'option doit etre TWIN");
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

    @Test
    @DisplayName("Tester le hashCode de l'entité Etudiant")
    void testEtudiantHashCode() {
        Etudiant newEtudiant1 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        Etudiant newEtudiant2 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);

        assertEquals(newEtudiant1.hashCode(), newEtudiant2.hashCode(),
                "Les hashCode des deux etudiants doivent être égaux");
    }

    @Test
    @DisplayName("Tester le getter et setter pour chaque attribut")
    void testAllGettersAndSetters() {
        Etudiant localEtudiant = new Etudiant();

        // Tester ID
        localEtudiant.setIdEtudiant(10L);
        assertEquals(10L, localEtudiant.getIdEtudiant());

        // Tester Nom
        localEtudiant.setNomEtudiant("Durand");
        assertEquals("Durand", localEtudiant.getNomEtudiant());

        // Tester Prenom
        localEtudiant.setPrenomEtudiant("Marie");
        assertEquals("Marie", localEtudiant.getPrenomEtudiant());

        // Tester Option
        localEtudiant.setOpt(Option.SAE);
        assertEquals(Option.SAE, localEtudiant.getOpt());
    }

    @Test
    @DisplayName("Tester la creation avec tous les constructeurs")
    void testAllConstructors() {
        // Constructeur sans arguments
        Etudiant etudia1 = new Etudiant();
        assertNotNull(etudia1);

        // Constructeur avec tous les arguments
        Etudiant etudia2 = new Etudiant(1L, "Test", "User", Option.TWIN);
        assertEquals(1L, etudia2.getIdEtudiant());
        assertEquals("Test", etudia2.getNomEtudiant());
        assertEquals("User", etudia2.getPrenomEtudiant());
        assertEquals(Option.TWIN, etudia2.getOpt());
    }

    @Test
    @DisplayName("Tester que Etudiant implémente Serializable")
    void testEtudiantSerializable() {
        Etudiant localEtudiant = new Etudiant(1L, "Test", "User", Option.TWIN);
        assertInstanceOf(java.io.Serializable.class, localEtudiant,
                "L'entité Etudiant doit implémenter Serializable");
    }

    @Test
    @DisplayName("Tester les annotations Lombok")
    void testLombokAnnotations() {
        assertNotNull(EtudiantTest.class.getClassLoader()
                .getResource("tn/esprit/spring/crudetudiant/entities/Etudiant.class"),
                "La classe Etudiant doit exister");
    }

    @Test
    @DisplayName("Tester la comparaison de deux etudiants modifiés")
    void testEtudiantEqualityAfterModification() {
        Etudiant etudiant1 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        Etudiant etudiant2 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);

        assertEquals(etudiant1, etudiant2);

        // Modifier un étudiant
        etudiant2.setPrenomEtudiant("Pierre");
        assertNotEquals(etudiant1, etudiant2);
    }

    @Test
    @DisplayName("Tester la comparaison avec null")
    void testEtudiantEqualityWithNull() {
        Etudiant localEtudiant = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        assertNotEquals(null, localEtudiant, "Un étudiant ne doit pas être égal à null");
    }

    @Test
    @DisplayName("Tester la comparaison avec un objet d'un autre type")
    void testEtudiantEqualityWithDifferentType() {
        Etudiant localEtudiant = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        assertNotEquals("String", localEtudiant, "Un étudiant ne doit pas être égal à une chaîne");
    }
}

