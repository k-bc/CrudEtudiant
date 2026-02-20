package tn.esprit.spring.crudetudiant.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.crudetudiant.entities.Etudiant;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour l'interface IEtudiant")
class IEtudiantInterfaceTest {

    /**
     * Test de validation que l'interface définit toutes les méthodes requises
     */
    @Test
    @DisplayName("Vérifier que l'interface IEtudiant a toutes les méthodes")
    void testInterfaceHasRequiredMethods() {
        try {
            // Vérifier que toutes les méthodes existent
            IEtudiant.class.getMethod("afficherEtudiants");
            IEtudiant.class.getMethod("ajouterEtudiant", Etudiant.class);
            IEtudiant.class.getMethod("modifierEtudiant", Etudiant.class);
            IEtudiant.class.getMethod("supprimerEtudiant", Long.class);
            IEtudiant.class.getMethod("afficherEtudiantById", long.class);
        } catch (NoSuchMethodException e) {
            fail("L'interface IEtudiant doit avoir toutes les méthodes requises");
        }
    }

    @Test
    @DisplayName("Vérifier que afficherEtudiants retourne List")
    void testAfficherEtudiantsReturnType() throws NoSuchMethodException {
        Method method = IEtudiant.class.getMethod("afficherEtudiants");
        assertTrue(List.class.isAssignableFrom(method.getReturnType()),
                "afficherEtudiants doit retourner une List");
    }

    @Test
    @DisplayName("Vérifier que ajouterEtudiant retourne Etudiant")
    void testAjouterEtudiantReturnType() throws NoSuchMethodException {
        Method method = IEtudiant.class.getMethod("ajouterEtudiant", Etudiant.class);
        assertEquals(Etudiant.class, method.getReturnType());
    }

    @Test
    @DisplayName("Vérifier que modifierEtudiant retourne Etudiant")
    void testModifierEtudiantReturnType() throws NoSuchMethodException {
        Method method = IEtudiant.class.getMethod("modifierEtudiant", Etudiant.class);
        assertEquals(Etudiant.class, method.getReturnType());
    }

    @Test
    @DisplayName("Vérifier que supprimerEtudiant retourne void")
    void testSupprimerEtudiantReturnType() throws NoSuchMethodException {
        Method method = IEtudiant.class.getMethod("supprimerEtudiant", Long.class);
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    @DisplayName("Vérifier que afficherEtudiantById retourne Etudiant")
    void testAfficherEtudiantByIdReturnType() throws NoSuchMethodException {
        Method method = IEtudiant.class.getMethod("afficherEtudiantById", long.class);
        assertEquals(Etudiant.class, method.getReturnType());
    }

    @Test
    @DisplayName("Vérifier que EtudiantServiceImpl implémente IEtudiant")
    void testServiceImplementsInterface() {
        Class<?>[] interfaces = EtudiantServiceImpl.class.getInterfaces();
        boolean implementsInterface = false;
        for (Class<?> iface : interfaces) {
            if (iface.equals(IEtudiant.class)) {
                implementsInterface = true;
                break;
            }
        }
        assertTrue(implementsInterface, "EtudiantServiceImpl doit implémenter IEtudiant");
    }

    @Test
    @DisplayName("Vérifier les signatures des méthodes")
    void testMethodSignatures() {
        Method[] methods = IEtudiant.class.getMethods();
        assertTrue(methods.length >= 5, "L'interface doit avoir au moins 5 méthodes");
    }
}

