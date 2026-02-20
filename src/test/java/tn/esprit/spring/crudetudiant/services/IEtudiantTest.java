package tn.esprit.spring.crudetudiant.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import tn.esprit.spring.crudetudiant.entities.Etudiant;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests pour l'interface IEtudiant")
class IEtudiantTest {

    @Test
    @DisplayName("Vérifier que l'interface IEtudiant contient les méthodes requises")
    void testInterfaceMethods() {
        // Vérifier que les méthodes existent dans l'interface
        try {
            IEtudiant.class.getDeclaredMethod("afficherEtudiants");
            IEtudiant.class.getDeclaredMethod("ajouterEtudiant", Etudiant.class);
            IEtudiant.class.getDeclaredMethod("modifierEtudiant", Etudiant.class);
            IEtudiant.class.getDeclaredMethod("supprimerEtudiant", Long.class);
            IEtudiant.class.getDeclaredMethod("afficherEtudiantById", long.class);

            // Si on arrive ici, toutes les méthodes existent
            assertTrue(true, "Toutes les méthodes requises existent dans l'interface");
        } catch (NoSuchMethodException e) {
            fail("Une méthode requise manque dans l'interface: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Vérifier que l'interface IEtudiant est bien définie")
    void testInterfaceDefinition() {
        assertTrue(IEtudiant.class.isInterface(), "IEtudiant doit être une interface");
    }
}

