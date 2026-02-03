package tn.esprit.spring.crudetudiant.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.crudetudiant.entities.Etudiant;
import tn.esprit.spring.crudetudiant.entities.Option;
import tn.esprit.spring.crudetudiant.repository.EtudiantRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests avancés pour le service EtudiantServiceImpl")
class EtudiantServiceImplAdvancedTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
    }

    @Test
    @DisplayName("Afficher étudiant par ID - étudiant présent")
    void testAfficherEtudiantById_StudentExists() {
        // Arrangement
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Action
        Etudiant result = etudiantService.afficherEtudiantById(1L);

        // Assertion
        assertNotNull(result, "L'étudiant ne doit pas être null");
        assertEquals("Dupont", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Afficher étudiant par ID - étudiant inexistant")
    void testAfficherEtudiantById_StudentNotFound() {
        // Arrangement
        when(etudiantRepository.findById(999L)).thenReturn(Optional.empty());

        // Action
        Etudiant result = etudiantService.afficherEtudiantById(999L);

        // Assertion
        assertNull(result, "Le résultat doit être null si l'étudiant n'existe pas");
        verify(etudiantRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Ajouter un étudiant avec tous les champs remplis")
    void testAjouterEtudiant_AllFieldsFilled() {
        // Arrangement
        Etudiant newStudent = new Etudiant(null, "Martin", "Pierre", Option.SAE);
        Etudiant savedStudent = new Etudiant(2L, "Martin", "Pierre", Option.SAE);
        when(etudiantRepository.save(newStudent)).thenReturn(savedStudent);

        // Action
        Etudiant result = etudiantService.ajouterEtudiant(newStudent);

        // Assertion
        assertNotNull(result);
        assertEquals(2L, result.getIdEtudiant());
        assertEquals("Martin", result.getNomEtudiant());
        assertEquals("Pierre", result.getPrenomEtudiant());
        assertEquals(Option.SAE, result.getOpt());
        verify(etudiantRepository, times(1)).save(newStudent);
    }

    @Test
    @DisplayName("Modifier un étudiant avec changement d'option")
    void testModifierEtudiant_ChangeOption() {
        // Arrangement
        Etudiant modifiedStudent = new Etudiant(1L, "Dupont", "Jean", Option.DS);
        when(etudiantRepository.save(modifiedStudent)).thenReturn(modifiedStudent);

        // Action
        Etudiant result = etudiantService.modifierEtudiant(modifiedStudent);

        // Assertion
        assertEquals(Option.DS, result.getOpt(), "L'option doit être changée en DS");
        verify(etudiantRepository, times(1)).save(modifiedStudent);
    }

    @Test
    @DisplayName("Modifier un étudiant avec changement de nom et prénom")
    void testModifierEtudiant_ChangeNames() {
        // Arrangement
        Etudiant modifiedStudent = new Etudiant(1L, "Martin", "Marie", Option.TWIN);
        when(etudiantRepository.save(modifiedStudent)).thenReturn(modifiedStudent);

        // Action
        Etudiant result = etudiantService.modifierEtudiant(modifiedStudent);

        // Assertion
        assertEquals("Martin", result.getNomEtudiant());
        assertEquals("Marie", result.getPrenomEtudiant());
        verify(etudiantRepository, times(1)).save(modifiedStudent);
    }

    @Test
    @DisplayName("Supprimer un étudiant avec succès")
    void testSupprimerEtudiant_Success() {
        // Arrangement
        Long id = 1L;
        doNothing().when(etudiantRepository).deleteById(id);

        // Action
        assertDoesNotThrow(() -> etudiantService.supprimerEtudiant(id));

        // Assertion
        verify(etudiantRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Supprimer un étudiant avec ID null")
    void testSupprimerEtudiant_NullId() {
        // Arrangement
        Long nullId = null;
        doNothing().when(etudiantRepository).deleteById(nullId);

        // Action & Assertion
        assertDoesNotThrow(() -> etudiantService.supprimerEtudiant(nullId));
        verify(etudiantRepository, times(1)).deleteById(nullId);
    }

    @Test
    @DisplayName("Ajouter un étudiant avec exception du repository")
    void testAjouterEtudiant_Exception() {
        // Arrangement
        when(etudiantRepository.save(any())).thenThrow(new RuntimeException("DB Error"));

        // Action & Assertion
        assertThrows(RuntimeException.class, () -> etudiantService.ajouterEtudiant(etudiant));
    }

    @Test
    @DisplayName("Afficher étudiant par ID avec exception du repository")
    void testAfficherEtudiantById_Exception() {
        // Arrangement
        when(etudiantRepository.findById(1L)).thenThrow(new RuntimeException("DB Error"));

        // Action & Assertion
        assertThrows(RuntimeException.class, () -> etudiantService.afficherEtudiantById(1L));
    }

    @Test
    @DisplayName("Modifier un étudiant avec exception du repository")
    void testModifierEtudiant_Exception() {
        // Arrangement
        when(etudiantRepository.save(any())).thenThrow(new RuntimeException("DB Error"));

        // Action & Assertion
        assertThrows(RuntimeException.class, () -> etudiantService.modifierEtudiant(etudiant));
    }

    @Test
    @DisplayName("Supprimer un étudiant avec exception du repository")
    void testSupprimerEtudiant_Exception() {
        // Arrangement
        doThrow(new RuntimeException("DB Error")).when(etudiantRepository).deleteById(1L);

        // Action & Assertion
        assertThrows(RuntimeException.class, () -> etudiantService.supprimerEtudiant(1L));
    }

    @Test
    @DisplayName("Ajouter un étudiant avec tous les options")
    void testAjouterEtudiant_AllOptions() {
        // Test TWIN
        Etudiant twin = new Etudiant(null, "Test", "Twin", Option.TWIN);
        Etudiant savedTwin = new Etudiant(1L, "Test", "Twin", Option.TWIN);
        when(etudiantRepository.save(twin)).thenReturn(savedTwin);
        assertEquals(Option.TWIN, etudiantService.ajouterEtudiant(twin).getOpt());

        // Test SAE
        Etudiant sae = new Etudiant(null, "Test", "Sae", Option.SAE);
        Etudiant savedSae = new Etudiant(2L, "Test", "Sae", Option.SAE);
        when(etudiantRepository.save(sae)).thenReturn(savedSae);
        assertEquals(Option.SAE, etudiantService.ajouterEtudiant(sae).getOpt());

        // Test DS
        Etudiant ds = new Etudiant(null, "Test", "Ds", Option.DS);
        Etudiant savedDs = new Etudiant(3L, "Test", "Ds", Option.DS);
        when(etudiantRepository.save(ds)).thenReturn(savedDs);
        assertEquals(Option.DS, etudiantService.ajouterEtudiant(ds).getOpt());
    }
}

