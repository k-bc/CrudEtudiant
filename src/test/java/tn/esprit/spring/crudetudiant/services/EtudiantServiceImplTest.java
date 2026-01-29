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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests pour le service EtudiantServiceImpl")
class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        etudiant2 = new Etudiant(2L, "Martin", "Pierre", Option.SAE);
    }

    @Test
    @DisplayName("Tester afficherEtudiants - doit retourner tous les étudiants")
    void testAfficherEtudiants() {
        // Arrangement
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        // Action
        List<Etudiant> resultat = etudiantService.afficherEtudiants();

        // Assertion
        assertNotNull(resultat, "La liste ne doit pas être null");
        assertEquals(2, resultat.size(), "La liste doit contenir 2 étudiants");
        assertEquals(etudiant1, resultat.get(0), "Le premier étudiant doit être etudiant1");
        assertEquals(etudiant2, resultat.get(1), "Le deuxième étudiant doit être etudiant2");
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Tester afficherEtudiants - doit retourner une liste vide")
    void testAfficherEtudiants_EmptyList() {
        // Arrangement
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList());

        // Action
        List<Etudiant> resultat = etudiantService.afficherEtudiants();

        // Assertion
        assertNotNull(resultat, "La liste ne doit pas être null");
        assertEquals(0, resultat.size(), "La liste doit être vide");
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Tester ajouterEtudiant - succès")
    void testAjouterEtudiant() {
        // Arrangement
        when(etudiantRepository.save(etudiant1)).thenReturn(etudiant1);

        // Action
        Etudiant resultat = etudiantService.ajouterEtudiant(etudiant1);

        // Assertion
        assertNotNull(resultat, "L'étudiant ne doit pas être null");
        assertEquals(etudiant1.getIdEtudiant(), resultat.getIdEtudiant(), "L'ID doit être 1");
        assertEquals("Dupont", resultat.getNomEtudiant(), "Le nom doit être Dupont");
        verify(etudiantRepository, times(1)).save(etudiant1);
    }

    @Test
    @DisplayName("Tester ajouterEtudiant avec null")
    void testAjouterEtudiant_WithNull() {
        // Arrangement
        when(etudiantRepository.save(null)).thenReturn(null);

        // Action & Assertion
        assertDoesNotThrow(() -> etudiantService.ajouterEtudiant(null),
                "Doit gérer l'ajout d'un étudiant null");
    }

    @Test
    @DisplayName("Tester modifierEtudiant - succès")
    void testModifierEtudiant() {
        // Arrangement
        Etudiant etudiantModifie = new Etudiant(1L, "Dupont", "Jean-Marie", Option.DS);
        when(etudiantRepository.save(etudiantModifie)).thenReturn(etudiantModifie);

        // Action
        Etudiant resultat = etudiantService.modifierEtudiant(etudiantModifie);

        // Assertion
        assertNotNull(resultat, "L'étudiant ne doit pas être null");
        assertEquals("Jean-Marie", resultat.getPrenomEtudiant(), "Le prénom doit être Jean-Marie");
        assertEquals(Option.DS, resultat.getOpt(), "L'option doit être DS");
        verify(etudiantRepository, times(1)).save(etudiantModifie);
    }

    @Test
    @DisplayName("Tester supprimerEtudiant - succès")
    void testSupprimerEtudiant() {
        // Arrangement
        Long id = 1L;
        doNothing().when(etudiantRepository).deleteById(id);

        // Action
        assertDoesNotThrow(() -> etudiantService.supprimerEtudiant(id),
                "La suppression ne doit pas lever d'exception");

        // Assertion
        verify(etudiantRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Tester supprimerEtudiant avec ID invalide")
    void testSupprimerEtudiant_InvalidId() {
        // Arrangement
        Long idInvalide = -1L;
        doNothing().when(etudiantRepository).deleteById(idInvalide);

        // Action
        assertDoesNotThrow(() -> etudiantService.supprimerEtudiant(idInvalide),
                "La suppression avec un ID invalide ne doit pas lever d'exception");

        // Assertion
        verify(etudiantRepository, times(1)).deleteById(idInvalide);
    }

    @Test
    @DisplayName("Tester afficherEtudiantById - étudiant trouvé")
    void testAfficherEtudiantById_Found() {
        // Arrangement
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant1));

        // Action
        Etudiant resultat = etudiantService.afficherEtudiantById(1L);

        // Assertion
        assertNotNull(resultat, "L'étudiant ne doit pas être null");
        assertEquals(1L, resultat.getIdEtudiant(), "L'ID doit être 1");
        assertEquals("Dupont", resultat.getNomEtudiant(), "Le nom doit être Dupont");
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Tester afficherEtudiantById - étudiant non trouvé")
    void testAfficherEtudiantById_NotFound() {
        // Arrangement
        when(etudiantRepository.findById(999L)).thenReturn(Optional.empty());

        // Action
        Etudiant resultat = etudiantService.afficherEtudiantById(999L);

        // Assertion
        assertNull(resultat, "L'étudiant doit être null");
        verify(etudiantRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Tester afficherEtudiantById avec ID zéro")
    void testAfficherEtudiantById_ZeroId() {
        // Arrangement
        when(etudiantRepository.findById(0L)).thenReturn(Optional.empty());

        // Action
        Etudiant resultat = etudiantService.afficherEtudiantById(0L);

        // Assertion
        assertNull(resultat, "L'étudiant doit être null pour un ID zéro");
        verify(etudiantRepository, times(1)).findById(0L);
    }

    @Test
    @DisplayName("Tester que le repository est injecté correctement")
    void testRepositoryInjection() {
        assertNotNull(etudiantService, "Le service ne doit pas être null");
    }
}

