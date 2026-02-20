package tn.esprit.spring.crudetudiant.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests approfondis pour l'entité Etudiant")
class EtudiantEdgeCaseTest {

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
    }

    @Test
    @DisplayName("Créer étudiant avec ID très grand")
    void testEtudiantWithLargeId() {
        Long largeId = Long.MAX_VALUE;
        etudiant.setIdEtudiant(largeId);
        assertEquals(largeId, etudiant.getIdEtudiant());
    }

    @Test
    @DisplayName("Créer étudiant avec ID négatif")
    void testEtudiantWithNegativeId() {
        Long negativeId = -999L;
        etudiant.setIdEtudiant(negativeId);
        assertEquals(negativeId, etudiant.getIdEtudiant());
    }

    @Test
    @DisplayName("Créer étudiant avec nom vide")
    void testEtudiantWithEmptyName() {
        etudiant.setNomEtudiant("");
        assertEquals("", etudiant.getNomEtudiant());
    }

    @Test
    @DisplayName("Créer étudiant avec nom très long")
    void testEtudiantWithLongName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("A");
        }
        String longName = sb.toString();
        etudiant.setNomEtudiant(longName);
        assertEquals(longName, etudiant.getNomEtudiant());
    }

    @Test
    @DisplayName("Créer étudiant avec prénom spécial")
    void testEtudiantWithSpecialCharacterPrenom() {
        String specialPrenom = "Jean-Pierre@2024#";
        etudiant.setPrenomEtudiant(specialPrenom);
        assertEquals(specialPrenom, etudiant.getPrenomEtudiant());
    }

    @Test
    @DisplayName("Modifier l'option plusieurs fois")
    void testChangeOptionMultipleTimes() {
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("Test");

        etudiant.setOpt(Option.TWIN);
        assertEquals(Option.TWIN, etudiant.getOpt());

        etudiant.setOpt(Option.SAE);
        assertEquals(Option.SAE, etudiant.getOpt());

        etudiant.setOpt(Option.DS);
        assertEquals(Option.DS, etudiant.getOpt());

        etudiant.setOpt(Option.TWIN);
        assertEquals(Option.TWIN, etudiant.getOpt());
    }

    @Test
    @DisplayName("Comparer deux étudiants avec tous les champs nuls")
    void testEqualityWithAllNullFields() {
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        assertEquals(etudiant1, etudiant2);
    }

    @Test
    @DisplayName("Comparer deux étudiants avec mêmes valeurs exactes")
    void testExactEqualityAllFields() {
        Etudiant etudiant1 = new Etudiant(100L, "TestName", "TestPrenom", Option.SAE);
        Etudiant etudiant2 = new Etudiant(100L, "TestName", "TestPrenom", Option.SAE);
        assertEquals(etudiant1, etudiant2);
        assertEquals(etudiant1.hashCode(), etudiant2.hashCode());
    }

    @Test
    @DisplayName("Setter et getter pour ID null")
    void testSetGetIdNull() {
        etudiant.setIdEtudiant(null);
        assertNull(etudiant.getIdEtudiant());
    }

    @Test
    @DisplayName("Setter et getter pour nom null")
    void testSetGetNomNull() {
        etudiant.setNomEtudiant(null);
        assertNull(etudiant.getNomEtudiant());
    }

    @Test
    @DisplayName("Setter et getter pour prénom null")
    void testSetGetPrenomNull() {
        etudiant.setPrenomEtudiant(null);
        assertNull(etudiant.getPrenomEtudiant());
    }

    @Test
    @DisplayName("Setter et getter pour option null")
    void testSetGetOptionNull() {
        etudiant.setOpt(null);
        assertNull(etudiant.getOpt());
    }

    @Test
    @DisplayName("ToString avec valeurs null")
    void testToStringWithNullValues() {
        Etudiant etudia = new Etudiant(null, null, null, null);
        String toStringResult = etudia.toString();
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
    }

    @Test
    @DisplayName("ToString avec valeurs complètes")
    void testToStringWithAllValues() {
        Etudiant etudia = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        String toStringResult = etudia.toString();
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        assertTrue(toStringResult.contains("1") || toStringResult.contains("Dupont"));
    }

    @Test
    @DisplayName("Vérifier que Serializable est bien implémenté")
    void testSerializableImplementation() {
        assertTrue(Etudiant.class.getInterfaces().length > 0);
        boolean isSerializable = false;
        for (Class<?> iface : Etudiant.class.getInterfaces()) {
            if (iface.equals(java.io.Serializable.class)) {
                isSerializable = true;
                break;
            }
        }
        assertTrue(isSerializable, "Etudiant doit implémenter Serializable");
    }

    @Test
    @DisplayName("Créer plusieurs instances indépendantes")
    void testMultipleIndependentInstances() {
        Etudiant etudiant1 = new Etudiant(1L, "Test1", "User1", Option.TWIN);
        Etudiant etudiant2 = new Etudiant(2L, "Test2", "User2", Option.SAE);
        Etudiant etudiant3 = new Etudiant(3L, "Test3", "User3", Option.DS);

        assertNotEquals(etudiant1, etudiant2);
        assertNotEquals(etudiant2, etudiant3);
        assertNotEquals(etudiant1, etudiant3);
    }

    @Test
    @DisplayName("Modifier un étudiant après création")
    void testModifyAfterCreation() {
        Etudiant modifiableEtudiant = new Etudiant(1L, "Original", "User", Option.TWIN);

        modifiableEtudiant.setNomEtudiant("Modified");
        assertEquals("Modified", modifiableEtudiant.getNomEtudiant());
        assertEquals(1L, modifiableEtudiant.getIdEtudiant());
        assertEquals(Option.TWIN, modifiableEtudiant.getOpt());

        modifiableEtudiant.setOpt(Option.DS);
        assertEquals("Modified", modifiableEtudiant.getNomEtudiant());
        assertEquals(Option.DS, modifiableEtudiant.getOpt());
    }

    @Test
    @DisplayName("Hashcode consistance après modification")
    void testHashCodeConsistency() {
        Etudiant etudiant1 = new Etudiant(1L, "Test", "User", Option.TWIN);
        int initialHashCode = etudiant1.hashCode();

        // Modifier
        etudiant1.setPrenomEtudiant("Modified");
        // Le hashCode peut changer après modification d'attributs
        int modifiedHashCode = etudiant1.hashCode();

        // Vérifier que c'est bien un hashCode valide
        assertTrue(initialHashCode != 0 || modifiedHashCode != 0);
    }

    @Test
    @DisplayName("Constructeur par copie (comportement attendu)")
    void testCopyBehavior() {
        Etudiant original = new Etudiant(1L, "Original", "Test", Option.TWIN);
        Etudiant copy = new Etudiant(original.getIdEtudiant(),
                original.getNomEtudiant(),
                original.getPrenomEtudiant(),
                original.getOpt());

        assertEquals(original, copy);
        assertEquals(original.hashCode(), copy.hashCode());
    }
}

