package tn.esprit.spring.crudetudiant.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour l'énumération Option")
class OptionTest {

    @Test
    @DisplayName("Tester que TWIN est une option valide")
    void testTwinOption() {
        assertEquals(Option.TWIN, Option.valueOf("TWIN"), "TWIN doit être une option valide");
        assertNotNull(Option.TWIN, "TWIN ne doit pas être null");
    }

    @Test
    @DisplayName("Tester que SAE est une option valide")
    void testSaeOption() {
        assertEquals(Option.SAE, Option.valueOf("SAE"), "SAE doit être une option valide");
        assertNotNull(Option.SAE, "SAE ne doit pas être null");
    }

    @Test
    @DisplayName("Tester que DS est une option valide")
    void testDsOption() {
        assertEquals(Option.DS, Option.valueOf("DS"), "DS doit être une option valide");
        assertNotNull(Option.DS, "DS ne doit pas être null");
    }

    @Test
    @DisplayName("Tester le nombre d'options")
    void testNumberOfOptions() {
        assertEquals(3, Option.values().length, "Il doit y avoir 3 options");
    }

    @Test
    @DisplayName("Tester que toutes les options sont distinctes")
    void testOptionsAreDistinct() {
        Option[] options = Option.values();
        assertNotEquals(Option.TWIN, Option.SAE, "TWIN et SAE ne doivent pas être égaux");
        assertNotEquals(Option.TWIN, Option.DS, "TWIN et DS ne doivent pas être égaux");
        assertNotEquals(Option.SAE, Option.DS, "SAE et DS ne doivent pas être égaux");
    }

    @Test
    @DisplayName("Tester values() retourne toutes les options")
    void testValuesMethod() {
        Option[] options = Option.values();
        assertTrue(contains(options, Option.TWIN), "TWIN doit être dans values()");
        assertTrue(contains(options, Option.SAE), "SAE doit être dans values()");
        assertTrue(contains(options, Option.DS), "DS doit être dans values()");
    }

    @Test
    @DisplayName("Tester valueOf avec option valide")
    void testValueOfValidOption() {
        assertDoesNotThrow(() -> Option.valueOf("TWIN"), "valueOf ne doit pas lever d'exception");
        assertEquals(Option.TWIN, Option.valueOf("TWIN"));
    }

    @Test
    @DisplayName("Tester valueOf avec option invalide")
    void testValueOfInvalidOption() {
        assertThrows(IllegalArgumentException.class,
                () -> Option.valueOf("INVALID"),
                "valueOf doit lever une IllegalArgumentException pour une option invalide");
    }

    // Méthode utilitaire
    private boolean contains(Option[] array, Option value) {
        for (Option option : array) {
            if (option.equals(value)) {
                return true;
            }
        }
        return false;
    }
}

