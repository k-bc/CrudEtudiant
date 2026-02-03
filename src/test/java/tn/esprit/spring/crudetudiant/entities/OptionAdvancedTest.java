package tn.esprit.spring.crudetudiant.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour l'enum Option")
class OptionAdvancedTest {

    @Test
    @DisplayName("Vérifier que Option.TWIN existe")
    void testOptionTWINExists() {
        assertNotNull(Option.TWIN);
        assertEquals("TWIN", Option.TWIN.name());
    }

    @Test
    @DisplayName("Vérifier que Option.SAE existe")
    void testOptionSAEExists() {
        assertNotNull(Option.SAE);
        assertEquals("SAE", Option.SAE.name());
    }

    @Test
    @DisplayName("Vérifier que Option.DS existe")
    void testOptionDSExists() {
        assertNotNull(Option.DS);
        assertEquals("DS", Option.DS.name());
    }

    @Test
    @DisplayName("Vérifier le nombre d'options")
    void testOptionCount() {
        Option[] options = Option.values();
        assertEquals(3, options.length, "Il doit y avoir exactement 3 options");
    }

    @Test
    @DisplayName("Vérifier que toutes les options sont accessibles")
    void testAllOptionsAccessible() {
        assertDoesNotThrow(() -> Option.valueOf("TWIN"));
        assertDoesNotThrow(() -> Option.valueOf("SAE"));
        assertDoesNotThrow(() -> Option.valueOf("DS"));
    }

    @Test
    @DisplayName("Vérifier que l'option invalide lève une exception")
    void testInvalidOptionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Option.valueOf("INVALID"));
    }

    @Test
    @DisplayName("Vérifier la comparaison d'options")
    void testOptionEquality() {
        // Tester que les options distinctes ne sont pas égales
        assertNotEquals(Option.TWIN, Option.SAE);
        assertNotEquals(Option.SAE, Option.DS);
        assertNotEquals(Option.DS, Option.TWIN);
        // Tester que valueOf retourne la même instance
        assertSame(Option.TWIN, Option.valueOf("TWIN"));
        assertSame(Option.SAE, Option.valueOf("SAE"));
        assertSame(Option.DS, Option.valueOf("DS"));
    }

    @Test
    @DisplayName("Vérifier le hashCode des options")
    void testOptionHashCode() {
        int twinHash = Option.TWIN.hashCode();
        int saeHash = Option.SAE.hashCode();
        int dsHash = Option.DS.hashCode();

        assertNotEquals(twinHash, saeHash);
        assertNotEquals(saeHash, dsHash);
        assertNotEquals(dsHash, twinHash);
    }

    @Test
    @DisplayName("Vérifier le toString des options")
    void testOptionToString() {
        assertEquals("TWIN", Option.TWIN.toString());
        assertEquals("SAE", Option.SAE.toString());
        assertEquals("DS", Option.DS.toString());
    }

    @Test
    @DisplayName("Vérifier l'ordinale des options")
    void testOptionOrdinal() {
        assertEquals(0, Option.TWIN.ordinal());
        assertEquals(1, Option.SAE.ordinal());
        assertEquals(2, Option.DS.ordinal());
    }

    @Test
    @DisplayName("Vérifier l'itération sur les options")
    void testIterateOverOptions() {
        int count = 0;
        for (Option opt : Option.values()) {
            assertNotNull(opt);
            count++;
        }
        assertEquals(3, count);
    }
}

