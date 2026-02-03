package tn.esprit.spring.crudetudiant.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.crudetudiant.entities.Etudiant;
import tn.esprit.spring.crudetudiant.entities.Option;
import tn.esprit.spring.crudetudiant.repository.EtudiantRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests avec cas limites pour EtudiantServiceImpl")
class EtudiantServiceImplEdgeCaseTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Test
    @DisplayName("AfficherEtudiants avec une liste très grande")
    void testAfficherEtudiants_LargeList() {
        List<Etudiant> largeList = new java.util.ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeList.add(new Etudiant((long)i, "Etudiant" + i, "User" + i, Option.TWIN));
        }
        when(etudiantRepository.findAll()).thenReturn(largeList);

        List<Etudiant> result = etudiantService.afficherEtudiants();

        assertEquals(1000, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("AfficherEtudiantById avec ID très grand")
    void testAfficherEtudiantById_LargeId() {
        Long largeId = Long.MAX_VALUE;
        Etudiant etudiant = new Etudiant(largeId, "Test", "User", Option.TWIN);
        when(etudiantRepository.findById(largeId)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.afficherEtudiantById(largeId);

        assertNotNull(result);
        assertEquals(largeId, result.getIdEtudiant());
    }

    @Test
    @DisplayName("AfficherEtudiantById avec ID zéro")
    void testAfficherEtudiantById_ZeroId() {
        when(etudiantRepository.findById(0L)).thenReturn(Optional.empty());

        Etudiant result = etudiantService.afficherEtudiantById(0L);

        assertNull(result);
    }

    @Test
    @DisplayName("AfficherEtudiantById avec ID négatif")
    void testAfficherEtudiantById_NegativeId() {
        when(etudiantRepository.findById(-1L)).thenReturn(Optional.empty());

        Etudiant result = etudiantService.afficherEtudiantById(-1L);

        assertNull(result);
    }

    @Test
    @DisplayName("AjouterEtudiant avec tous les options")
    void testAjouterEtudiant_AllOptions() {
        // Test avec TWIN
        Etudiant twinStudent = new Etudiant(null, "Twin", "Test", Option.TWIN);
        Etudiant savedTwin = new Etudiant(1L, "Twin", "Test", Option.TWIN);
        when(etudiantRepository.save(twinStudent)).thenReturn(savedTwin);
        assertEquals(Option.TWIN, etudiantService.ajouterEtudiant(twinStudent).getOpt());

        // Test avec SAE
        Etudiant saeStudent = new Etudiant(null, "Sae", "Test", Option.SAE);
        Etudiant savedSae = new Etudiant(2L, "Sae", "Test", Option.SAE);
        when(etudiantRepository.save(saeStudent)).thenReturn(savedSae);
        assertEquals(Option.SAE, etudiantService.ajouterEtudiant(saeStudent).getOpt());

        // Test avec DS
        Etudiant dsStudent = new Etudiant(null, "Ds", "Test", Option.DS);
        Etudiant savedDs = new Etudiant(3L, "Ds", "Test", Option.DS);
        when(etudiantRepository.save(dsStudent)).thenReturn(savedDs);
        assertEquals(Option.DS, etudiantService.ajouterEtudiant(dsStudent).getOpt());
    }

    @Test
    @DisplayName("ModifierEtudiant avec changement partiel")
    void testModifierEtudiant_PartialChange() {
        Etudiant modified = new Etudiant(1L, "Modified", "User", Option.TWIN);

        when(etudiantRepository.save(modified)).thenReturn(modified);

        Etudiant result = etudiantService.modifierEtudiant(modified);

        assertEquals("Modified", result.getNomEtudiant());
        assertEquals("User", result.getPrenomEtudiant());
        assertEquals(Option.TWIN, result.getOpt());
    }

    @Test
    @DisplayName("SupprimerEtudiant avec ID zéro")
    void testSupprimerEtudiant_ZeroId() {
        doNothing().when(etudiantRepository).deleteById(0L);

        assertDoesNotThrow(() -> etudiantService.supprimerEtudiant(0L));
        verify(etudiantRepository, times(1)).deleteById(0L);
    }

    @Test
    @DisplayName("SupprimerEtudiant avec ID très grand")
    void testSupprimerEtudiant_LargeId() {
        Long largeId = Long.MAX_VALUE;
        doNothing().when(etudiantRepository).deleteById(largeId);

        assertDoesNotThrow(() -> etudiantService.supprimerEtudiant(largeId));
        verify(etudiantRepository, times(1)).deleteById(largeId);
    }

    @Test
    @DisplayName("AfficherEtudiants vérifier le type de retour")
    void testAfficherEtudiants_ReturnType() {
        List<Etudiant> etudiants = Arrays.asList(
                new Etudiant(1L, "Test1", "User1", Option.TWIN),
                new Etudiant(2L, "Test2", "User2", Option.SAE)
        );
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.afficherEtudiants();

        assertNotNull(result);
        assertInstanceOf(List.class, result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("AjouterEtudiant avec exception SQL")
    void testAjouterEtudiant_DatabaseError() {
        Etudiant etudiant = new Etudiant(null, "Test", "User", Option.TWIN);
        when(etudiantRepository.save(etudiant)).thenThrow(
                new RuntimeException("Database connection failed"));

        assertThrows(RuntimeException.class, () -> etudiantService.ajouterEtudiant(etudiant));
    }

    @Test
    @DisplayName("ModifierEtudiant avec exception SQL")
    void testModifierEtudiant_DatabaseError() {
        Etudiant etudiant = new Etudiant(1L, "Test", "User", Option.TWIN);
        when(etudiantRepository.save(etudiant)).thenThrow(
                new RuntimeException("Database connection failed"));

        assertThrows(RuntimeException.class, () -> etudiantService.modifierEtudiant(etudiant));
    }

    @Test
    @DisplayName("SupprimerEtudiant avec exception SQL")
    void testSupprimerEtudiant_DatabaseError() {
        doThrow(new RuntimeException("Database connection failed"))
                .when(etudiantRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> etudiantService.supprimerEtudiant(1L));
    }

    @Test
    @DisplayName("AfficherEtudiantById avec exception SQL")
    void testAfficherEtudiantById_DatabaseError() {
        when(etudiantRepository.findById(1L)).thenThrow(
                new RuntimeException("Database connection failed"));

        assertThrows(RuntimeException.class, () -> etudiantService.afficherEtudiantById(1L));
    }

    @Test
    @DisplayName("Appels successifs au service")
    void testMultipleServiceCalls() {
        when(etudiantRepository.findAll()).thenReturn(Collections.emptyList());

        etudiantService.afficherEtudiants();
        etudiantService.afficherEtudiants();
        etudiantService.afficherEtudiants();

        verify(etudiantRepository, times(3)).findAll();
    }

    @Test
    @DisplayName("Service retourne le même objet")
    void testServiceReturnsSameObject() {
        Etudiant original = new Etudiant(1L, "Test", "User", Option.TWIN);
        when(etudiantRepository.save(original)).thenReturn(original);

        Etudiant result = etudiantService.ajouterEtudiant(original);

        assertSame(original, result);
    }

    @Test
    @DisplayName("Vérifier que le service utilise le repository")
    void testServiceUsesRepository() {
        etudiantService.afficherEtudiants();
        verify(etudiantRepository, times(1)).findAll();

        Etudiant etudiant = new Etudiant(1L, "Test", "User", Option.TWIN);
        etudiantService.ajouterEtudiant(etudiant);
        verify(etudiantRepository, times(1)).save(etudiant);

        etudiantService.modifierEtudiant(etudiant);
        verify(etudiantRepository, times(2)).save(etudiant);

        etudiantService.supprimerEtudiant(1L);
        verify(etudiantRepository, times(1)).deleteById(1L);

        etudiantService.afficherEtudiantById(1L);
        verify(etudiantRepository, times(1)).findById(1L);
    }
}

