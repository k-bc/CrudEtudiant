package tn.esprit.spring.crudetudiant.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tn.esprit.spring.crudetudiant.entities.Etudiant;
import tn.esprit.spring.crudetudiant.entities.Option;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests pour le repository EtudiantRepository")
class EtudiantRepositoryTest {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant(null, "Dupont", "Jean", Option.TWIN);
        etudiant2 = new Etudiant(null, "Martin", "Pierre", Option.SAE);
    }

    @Test
    @DisplayName("Tester save - ajouter un etudiant")
    void testSaveEtudiant() {
        // Arrangement
        // Action
        Etudiant saved = etudiantRepository.save(etudiant1);

        // Assertion
        assertNotNull(saved, "L'etudiant sauvegarde ne doit pas etre null");
        assertNotNull(saved.getIdEtudiant(), "L'ID doit etre genere");
        assertEquals("Dupont", saved.getNomEtudiant(), "Le nom doit etre Dupont");
    }

    @Test
    @DisplayName("Tester findAll - recuperer tous les etudiants")
    void testFindAllEtudiants() {
        // Arrangement
        etudiantRepository.save(etudiant1);
        etudiantRepository.save(etudiant2);

        // Action
        List<Etudiant> etudiants = etudiantRepository.findAll();

        // Assertion
        assertNotNull(etudiants, "La liste ne doit pas etre null");
        assertEquals(2, etudiants.size(), "La liste doit contenir 2 etudiants");
    }

    @Test
    @DisplayName("Tester findById - recuperer un etudiant par son ID")
    void testFindByIdEtudiant() {
        // Arrangement
        Etudiant saved = etudiantRepository.save(etudiant1);

        // Action
        Optional<Etudiant> found = etudiantRepository.findById(saved.getIdEtudiant());

        // Assertion
        assertTrue(found.isPresent(), "L'etudiant doit etre trouve");
        assertEquals("Dupont", found.get().getNomEtudiant(), "Le nom doit correspondre");
    }

    @Test
    @DisplayName("Tester findById avec ID qui n'existe pas")
    void testFindByIdNotFound() {
        // Action
        Optional<Etudiant> found = etudiantRepository.findById(999L);

        // Assertion
        assertFalse(found.isPresent(), "L'Optional doit etre vide");
    }

    @Test
    @DisplayName("Tester update - modifier un etudiant")
    void testUpdateEtudiant() {
        // Arrangement
        Etudiant saved = etudiantRepository.save(etudiant1);
        saved.setPrenomEtudiant("Jean-Marie");
        saved.setOpt(Option.DS);

        // Action
        Etudiant updated = etudiantRepository.save(saved);

        // Assertion
        assertEquals("Jean-Marie", updated.getPrenomEtudiant(), "Le prenom doit etre modifie");
        assertEquals(Option.DS, updated.getOpt(), "L'option doit etre modifiee");
    }

    @Test
    @DisplayName("Tester deleteById - supprimer un etudiant")
    void testDeleteById() {
        // Arrangement
        Etudiant saved = etudiantRepository.save(etudiant1);
        Long id = saved.getIdEtudiant();

        // Action
        etudiantRepository.deleteById(id);
        Optional<Etudiant> deleted = etudiantRepository.findById(id);

        // Assertion
        assertFalse(deleted.isPresent(), "L'etudiant doit etre supprime");
    }

    @Test
    @DisplayName("Tester count - compter le nombre d'etudiants")
    void testCountEtudiants() {
        // Arrangement
        etudiantRepository.save(etudiant1);
        etudiantRepository.save(etudiant2);

        // Action
        long count = etudiantRepository.count();

        // Assertion
        assertEquals(2, count, "Il doit y avoir 2 etudiants");
    }

    @Test
    @DisplayName("Tester existsById")
    void testExistsById() {
        // Arrangement
        Etudiant saved = etudiantRepository.save(etudiant1);

        // Action
        boolean exists = etudiantRepository.existsById(saved.getIdEtudiant());

        // Assertion
        assertTrue(exists, "L'etudiant doit exister");
    }

    @Test
    @DisplayName("Tester existsById avec ID qui n'existe pas")
    void testExistsByIdNotFound() {
        // Action
        boolean exists = etudiantRepository.existsById(999L);

        // Assertion
        assertFalse(exists, "L'etudiant ne doit pas exister");
    }

    @Test
    @DisplayName("Tester save avec tous les champs remplis")
    void testSaveWithAllFields() {
        // Arrangement
        Etudiant etudiant = new Etudiant(null, "Bernard", "Claude", Option.TWIN);

        // Action
        Etudiant saved = etudiantRepository.save(etudiant);

        // Assertion
        assertNotNull(saved.getIdEtudiant(), "L'ID doit etre genere");
        assertEquals("Bernard", saved.getNomEtudiant(), "Le nom doit etre Bernard");
        assertEquals("Claude", saved.getPrenomEtudiant(), "Le prenom doit etre Claude");
        assertEquals(Option.TWIN, saved.getOpt(), "L'option doit etre TWIN");
    }

    @Test
    @DisplayName("Tester delete")
    void testDeleteEtudiant() {
        // Arrangement
        Etudiant saved = etudiantRepository.save(etudiant1);

        // Action
        etudiantRepository.delete(saved);
        Optional<Etudiant> deleted = etudiantRepository.findById(saved.getIdEtudiant());

        // Assertion
        assertFalse(deleted.isPresent(), "L'etudiant doit etre supprime");
    }
}

