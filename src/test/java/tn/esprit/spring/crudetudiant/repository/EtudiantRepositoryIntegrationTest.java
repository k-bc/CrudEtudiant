package tn.esprit.spring.crudetudiant.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.crudetudiant.entities.Etudiant;
import tn.esprit.spring.crudetudiant.entities.Option;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests d'intégration pour EtudiantRepository")
class EtudiantRepositoryIntegrationTest {

    @Autowired
    private EtudiantRepository etudiantRepository;

    private Etudiant etudiant1;
    private Etudiant etudiant2;
    private Etudiant etudiant3;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant(null, "Dupont", "Jean", Option.TWIN);
        etudiant2 = new Etudiant(null, "Martin", "Pierre", Option.SAE);
        etudiant3 = new Etudiant(null, "Bernard", "Marie", Option.DS);
    }

    @Test
    @DisplayName("Sauvegarder et récupérer un étudiant")
    void testSaveAndFindById() {
        // Arrangement & Action
        Etudiant saved = etudiantRepository.save(etudiant1);

        // Assertion
        assertNotNull(saved.getIdEtudiant(), "L'ID doit être généré après la sauvegarde");
        Optional<Etudiant> found = etudiantRepository.findById(saved.getIdEtudiant());
        assertTrue(found.isPresent(), "L'étudiant doit être trouvé");
        assertEquals("Dupont", found.get().getNomEtudiant());
    }

    @Test
    @DisplayName("Sauvegarder plusieurs étudiants et vérifier findAll")
    void testFindAll() {
        // Arrangement
        etudiantRepository.saveAll(Arrays.asList(etudiant1, etudiant2, etudiant3));

        // Action
        List<Etudiant> all = etudiantRepository.findAll();

        // Assertion
        assertNotNull(all, "La liste ne doit pas être null");
        assertFalse(all.isEmpty(), "La liste ne doit pas être vide");
        assertTrue(all.size() >= 3, "La liste doit contenir au moins 3 étudiants");
    }

    @Test
    @DisplayName("Mettre à jour un étudiant")
    void testUpdate() {
        // Arrangement
        Etudiant saved = etudiantRepository.save(etudiant1);
        Long id = saved.getIdEtudiant();

        // Action
        saved.setPrenomEtudiant("Jean-Marie");
        saved.setOpt(Option.SAE);
        etudiantRepository.save(saved);

        // Assertion
        Optional<Etudiant> updated = etudiantRepository.findById(id);
        assertTrue(updated.isPresent());
        assertEquals("Jean-Marie", updated.get().getPrenomEtudiant());
        assertEquals(Option.SAE, updated.get().getOpt());
    }

    @Test
    @DisplayName("Supprimer un étudiant par ID")
    void testDeleteById() {
        // Arrangement
        Etudiant saved = etudiantRepository.save(etudiant1);
        Long id = saved.getIdEtudiant();

        // Action
        etudiantRepository.deleteById(id);

        // Assertion
        Optional<Etudiant> deleted = etudiantRepository.findById(id);
        assertFalse(deleted.isPresent(), "L'étudiant ne doit pas être trouvé après suppression");
    }

    @Test
    @DisplayName("Compter les étudiants")
    void testCount() {
        // Arrangement
        long countBefore = etudiantRepository.count();
        etudiantRepository.saveAll(Arrays.asList(etudiant1, etudiant2, etudiant3));

        // Action
        long countAfter = etudiantRepository.count();

        // Assertion
        assertTrue(countAfter > countBefore, "Le nombre d'étudiants doit augmenter");
    }

    @Test
    @DisplayName("Vérifier l'existence d'un étudiant par ID")
    void testExistsById() {
        // Arrangement
        Etudiant saved = etudiantRepository.save(etudiant1);

        // Action & Assertion
        assertTrue(etudiantRepository.existsById(saved.getIdEtudiant()),
                "L'étudiant doit exister");
        assertFalse(etudiantRepository.existsById(9999L),
                "Un étudiant inexistant ne doit pas exister");
    }

    @Test
    @DisplayName("Récupérer un étudiant inexistant")
    void testFindNonExistentById() {
        // Action
        Optional<Etudiant> found = etudiantRepository.findById(9999L);

        // Assertion
        assertFalse(found.isPresent(), "L'Optional ne doit pas être présent pour un ID inexistant");
    }

    @Test
    @DisplayName("Supprimer tous les étudiants")
    void testDeleteAll() {
        // Arrangement
        etudiantRepository.saveAll(Arrays.asList(etudiant1, etudiant2, etudiant3));

        // Action
        etudiantRepository.deleteAll();

        // Assertion
        assertEquals(0, etudiantRepository.count(), "La table ne doit pas avoir d'étudiants");
    }

    @Test
    @DisplayName("Sauvegarder un étudiant avec tous les options")
    void testSaveWithDifferentOptions() {
        // Test TWIN
        Etudiant twin = new Etudiant(null, "Test1", "Twin", Option.TWIN);
        Etudiant savedTwin = etudiantRepository.save(twin);
        assertEquals(Option.TWIN, savedTwin.getOpt());

        // Test SAE
        Etudiant sae = new Etudiant(null, "Test2", "Sae", Option.SAE);
        Etudiant savedSae = etudiantRepository.save(sae);
        assertEquals(Option.SAE, savedSae.getOpt());

        // Test DS
        Etudiant ds = new Etudiant(null, "Test3", "Ds", Option.DS);
        Etudiant savedDs = etudiantRepository.save(ds);
        assertEquals(Option.DS, savedDs.getOpt());
    }
}

