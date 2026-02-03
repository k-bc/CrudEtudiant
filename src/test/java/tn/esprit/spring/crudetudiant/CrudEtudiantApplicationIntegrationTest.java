package tn.esprit.spring.crudetudiant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.crudetudiant.entities.Etudiant;
import tn.esprit.spring.crudetudiant.entities.Option;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Tests d'intégration pour l'application CrudEtudiant")
class CrudEtudiantApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Etudiant testEtudiant;

    @BeforeEach
    void setUp() {
        testEtudiant = new Etudiant(null, "IntegrationTest", "User", Option.TWIN);
    }

    @Test
    @DisplayName("GET /afficherAllEtudiant - doit retourner OK")
    void testGetAllEtudiants() {
        ResponseEntity<Etudiant[]> response = restTemplate.getForEntity(
                "/afficherAllEtudiant", Etudiant[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("POST /ajouterEtudiant - ajouter un étudiant")
    void testAddEtudiant() {
        ResponseEntity<Etudiant> response = restTemplate.postForEntity(
                "/ajouterEtudiant", testEtudiant, Etudiant.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getIdEtudiant());
        assertEquals("IntegrationTest", response.getBody().getNomEtudiant());
    }

    @Test
    @DisplayName("GET /afficheById/{id} - récupérer un étudiant par ID")
    void testGetEtudiantById() {
        // Ajouter d'abord un étudiant
        ResponseEntity<Etudiant> addResponse = restTemplate.postForEntity(
                "/ajouterEtudiant", testEtudiant, Etudiant.class);

        assertNotNull(addResponse.getBody(), "La réponse ne doit pas être null");
        Long id = addResponse.getBody().getIdEtudiant();

        // Récupérer l'étudiant
        ResponseEntity<Etudiant> getResponse = restTemplate.getForEntity(
                "/afficheById/" + id, Etudiant.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody(), "La réponse ne doit pas être null");
        assertEquals(id, getResponse.getBody().getIdEtudiant());
    }

    @Test
    @DisplayName("PUT /modifierEtudiant - modifier un étudiant")
    void testModifyEtudiant() {
        // Ajouter d'abord un étudiant
        ResponseEntity<Etudiant> addResponse = restTemplate.postForEntity(
                "/ajouterEtudiant", testEtudiant, Etudiant.class);

        assertNotNull(addResponse.getBody(), "La réponse ne doit pas être null");
        Etudiant added = addResponse.getBody();
        Long id = added.getIdEtudiant();

        // Modifier l'étudiant
        added.setPrenomEtudiant("ModifiedPrenom");
        added.setOpt(Option.SAE);

        restTemplate.put("/modifierEtudiant", added);

        // Vérifier les modifications
        ResponseEntity<Etudiant> getResponse = restTemplate.getForEntity(
                "/afficheById/" + id, Etudiant.class);

        assertNotNull(getResponse.getBody(), "La réponse ne doit pas être null");
        assertEquals("ModifiedPrenom", getResponse.getBody().getPrenomEtudiant());
        assertEquals(Option.SAE, getResponse.getBody().getOpt());
    }

    @Test
    @DisplayName("DELETE /supprimer/{id} - supprimer un étudiant")
    void testDeleteEtudiant() {
        // Ajouter d'abord un étudiant
        ResponseEntity<Etudiant> addResponse = restTemplate.postForEntity(
                "/ajouterEtudiant", testEtudiant, Etudiant.class);

        assertNotNull(addResponse.getBody(), "La réponse ne doit pas être null");
        Long id = addResponse.getBody().getIdEtudiant();

        // Supprimer l'étudiant
        restTemplate.delete("/supprimer/" + id);

        // Vérifier que l'étudiant n'existe plus
        ResponseEntity<Etudiant> getResponse = restTemplate.getForEntity(
                "/afficheById/" + id, Etudiant.class);

        assertNull(getResponse.getBody());
    }

    @Test
    @DisplayName("Workflow complet: Ajouter, Lire, Modifier, Supprimer")
    void testCompleteWorkflow() {
        // 1. Ajouter
        ResponseEntity<Etudiant> addResponse = restTemplate.postForEntity(
                "/ajouterEtudiant",
                new Etudiant(null, "Workflow", "Test", Option.TWIN),
                Etudiant.class);

        assertEquals(HttpStatus.OK, addResponse.getStatusCode());
        assertNotNull(addResponse.getBody(), "La réponse ne doit pas être null");
        Long id = addResponse.getBody().getIdEtudiant();

        // 2. Lire
        ResponseEntity<Etudiant> getResponse = restTemplate.getForEntity(
                "/afficheById/" + id, Etudiant.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody(), "La réponse ne doit pas être null");
        assertEquals("Workflow", getResponse.getBody().getNomEtudiant());

        // 3. Modifier
        Etudiant toModify = getResponse.getBody();
        toModify.setPrenomEtudiant("Modified");
        toModify.setOpt(Option.DS);
        restTemplate.put("/modifierEtudiant", toModify);

        // Vérifier la modification
        ResponseEntity<Etudiant> verifyModify = restTemplate.getForEntity(
                "/afficheById/" + id, Etudiant.class);

        assertNotNull(verifyModify.getBody(), "La réponse ne doit pas être null");
        assertEquals("Modified", verifyModify.getBody().getPrenomEtudiant());
        assertEquals(Option.DS, verifyModify.getBody().getOpt());

        // 4. Supprimer
        restTemplate.delete("/supprimer/" + id);

        // Vérifier la suppression
        ResponseEntity<Etudiant> verifyDelete = restTemplate.getForEntity(
                "/afficheById/" + id, Etudiant.class);

        assertNull(verifyDelete.getBody());
    }

    @Test
    @DisplayName("Ajouter plusieurs étudiants avec différentes options")
    void testAddMultipleEtudiantsWithDifferentOptions() {
        // TWIN
        Etudiant twin = new Etudiant(null, "Twin", "Test", Option.TWIN);
        ResponseEntity<Etudiant> twinResponse = restTemplate.postForEntity(
                "/ajouterEtudiant", twin, Etudiant.class);
        assertEquals(HttpStatus.OK, twinResponse.getStatusCode());

        // SAE
        Etudiant sae = new Etudiant(null, "Sae", "Test", Option.SAE);
        ResponseEntity<Etudiant> saeResponse = restTemplate.postForEntity(
                "/ajouterEtudiant", sae, Etudiant.class);
        assertEquals(HttpStatus.OK, saeResponse.getStatusCode());

        // DS
        Etudiant ds = new Etudiant(null, "Ds", "Test", Option.DS);
        ResponseEntity<Etudiant> dsResponse = restTemplate.postForEntity(
                "/ajouterEtudiant", ds, Etudiant.class);
        assertEquals(HttpStatus.OK, dsResponse.getStatusCode());
    }

    @Test
    @DisplayName("Ajouter et récupérer tous les étudiants")
    void testAddAndGetAllEtudiants() {
        // Ajouter plusieurs étudiants
        Etudiant etudiant1 = new Etudiant(null, "User1", "Test", Option.TWIN);
        Etudiant etudiant2 = new Etudiant(null, "User2", "Test", Option.SAE);

        restTemplate.postForEntity("/ajouterEtudiant", etudiant1, Etudiant.class);
        restTemplate.postForEntity("/ajouterEtudiant", etudiant2, Etudiant.class);

        // Récupérer tous
        ResponseEntity<Etudiant[]> response = restTemplate.getForEntity(
                "/afficherAllEtudiant", Etudiant[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 2);
    }
}

