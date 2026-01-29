package tn.esprit.spring.crudetudiant.repository;
}
    }
        assertFalse(deleted.isPresent(), "L'étudiant doit être supprimé");
        // Assertion

        Optional<Etudiant> deleted = etudiantRepository.findById(saved.getIdEtudiant());
        etudiantRepository.delete(saved);
        // Action

        Etudiant saved = etudiantRepository.save(etudiant1);
        // Arrangement
    void testDeleteEtudiant() {
    @DisplayName("Tester delete")
    @Test

    }
        assertEquals(Option.TWIN, saved.getOpt(), "L'option doit être TWIN");
        assertEquals("Claude", saved.getPrenomEtudiant(), "Le prénom doit être Claude");
        assertEquals("Bernard", saved.getNomEtudiant(), "Le nom doit être Bernard");
        assertNotNull(saved.getIdEtudiant(), "L'ID doit être généré");
        // Assertion

        Etudiant saved = etudiantRepository.save(etudiant);
        // Action

        Etudiant etudiant = new Etudiant(null, "Bernard", "Claude", Option.TWIN);
        // Arrangement
    void testSaveWithAllFields() {
    @DisplayName("Tester save avec tous les champs remplis")
    @Test

    }
        assertFalse(exists, "L'étudiant ne doit pas exister");
        // Assertion

        boolean exists = etudiantRepository.existsById(999L);
        // Action
    void testExistsByIdNotFound() {
    @DisplayName("Tester existsById avec ID qui n'existe pas")
    @Test

    }
        assertTrue(exists, "L'étudiant doit exister");
        // Assertion

        boolean exists = etudiantRepository.existsById(saved.getIdEtudiant());
        // Action

        Etudiant saved = etudiantRepository.save(etudiant1);
        // Arrangement
    void testExistsById() {
    @DisplayName("Tester existsById")
    @Test

    }
        assertEquals(2, count, "Il doit y avoir 2 étudiants");
        // Assertion

        long count = etudiantRepository.count();
        // Action

        etudiantRepository.save(etudiant2);
        etudiantRepository.save(etudiant1);
        // Arrangement
    void testCountEtudiants() {
    @DisplayName("Tester count - compter le nombre d'étudiants")
    @Test

    }
        assertFalse(deleted.isPresent(), "L'étudiant doit être supprimé");
        // Assertion

        Optional<Etudiant> deleted = etudiantRepository.findById(id);
        etudiantRepository.deleteById(id);
        // Action

        Long id = saved.getIdEtudiant();
        Etudiant saved = etudiantRepository.save(etudiant1);
        // Arrangement
    void testDeleteById() {
    @DisplayName("Tester deleteById - supprimer un étudiant")
    @Test

    }
        assertEquals(Option.DS, updated.getOpt(), "L'option doit être modifiée");
        assertEquals("Jean-Marie", updated.getPrenomEtudiant(), "Le prénom doit être modifié");
        // Assertion

        Etudiant updated = etudiantRepository.save(saved);
        // Action

        saved.setOpt(Option.DS);
        saved.setPrenomEtudiant("Jean-Marie");
        Etudiant saved = etudiantRepository.save(etudiant1);
        // Arrangement
    void testUpdateEtudiant() {
    @DisplayName("Tester update - modifier un étudiant")
    @Test

    }
        assertFalse(found.isPresent(), "L'Optional doit être vide");
        // Assertion

        Optional<Etudiant> found = etudiantRepository.findById(999L);
        // Action
    void testFindByIdNotFound() {
    @DisplayName("Tester findById avec ID qui n'existe pas")
    @Test

    }
        assertEquals("Dupont", found.get().getNomEtudiant(), "Le nom doit correspondre");
        assertTrue(found.isPresent(), "L'étudiant doit être trouvé");
        // Assertion

        Optional<Etudiant> found = etudiantRepository.findById(saved.getIdEtudiant());
        // Action

        Etudiant saved = etudiantRepository.save(etudiant1);
        // Arrangement
    void testFindByIdEtudiant() {
    @DisplayName("Tester findById - récupérer un étudiant par son ID")
    @Test

    }
        assertEquals(2, etudiants.size(), "La liste doit contenir 2 étudiants");
        assertNotNull(etudiants, "La liste ne doit pas être null");
        // Assertion

        List<Etudiant> etudiants = etudiantRepository.findAll();
        // Action

        etudiantRepository.save(etudiant2);
        etudiantRepository.save(etudiant1);
        // Arrangement
    void testFindAllEtudiants() {
    @DisplayName("Tester findAll - récupérer tous les étudiants")
    @Test

    }
        assertEquals("Dupont", saved.getNomEtudiant(), "Le nom doit être Dupont");
        assertNotNull(saved.getIdEtudiant(), "L'ID doit être généré");
        assertNotNull(saved, "L'étudiant sauvegardé ne doit pas être null");
        // Assertion

        Etudiant saved = etudiantRepository.save(etudiant1);
        // Action
    void testSaveEtudiant() {
    @DisplayName("Tester save - ajouter un étudiant")
    @Test

    }
        etudiant2 = new Etudiant(null, "Martin", "Pierre", Option.SAE);
        etudiant1 = new Etudiant(null, "Dupont", "Jean", Option.TWIN);
    void setUp() {
    @BeforeEach

    private Etudiant etudiant2;
    private Etudiant etudiant1;

    private TestEntityManager entityManager;
    @Autowired

    private EtudiantRepository etudiantRepository;
    @Autowired

class EtudiantRepositoryTest {
@DisplayName("Tests pour le repository EtudiantRepository")
@DataJpaTest

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;

import tn.esprit.spring.crudetudiant.entities.Option;
import tn.esprit.spring.crudetudiant.entities.Etudiant;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


