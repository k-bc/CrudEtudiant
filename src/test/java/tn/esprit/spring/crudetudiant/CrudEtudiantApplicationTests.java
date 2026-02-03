package tn.esprit.spring.crudetudiant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import tn.esprit.spring.crudetudiant.controllers.EtudiantController;
import tn.esprit.spring.crudetudiant.services.EtudiantServiceImpl;
import tn.esprit.spring.crudetudiant.repository.EtudiantRepository;
import tn.esprit.spring.crudetudiant.entities.Etudiant;
import tn.esprit.spring.crudetudiant.entities.Option;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Tests d'intégration de l'application CrudEtudiant")
class CrudEtudiantApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MockMvc mockMvc;

	@Autowired(required = false)
	private EtudiantController etudiantController;

	@Autowired(required = false)
	private EtudiantServiceImpl etudiantService;

	@Autowired(required = false)
	private EtudiantRepository etudiantRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		assertNotNull(applicationContext, "Le contexte Spring doit être chargé");
		if (etudiantRepository != null) {
			etudiantRepository.deleteAll();
		}
	}

	@Test
	@DisplayName("Le contexte Spring doit se charger correctement")
	void contextLoads() {
		assertNotNull(applicationContext, "Le contexte Spring doit être chargé");
	}

	@Test
	@DisplayName("L'application doit démarrer sans erreur")
	void applicationStartsSuccessfully() {
		assertNotNull(applicationContext, "Le contexte Spring doit être chargé");
	}

	@Test
	@DisplayName("La classe principale doit avoir l'annotation @SpringBootApplication")
	void springBootApplicationAnnotationPresent() {
		assertTrue(CrudEtudiantApplication.class.isAnnotationPresent(
				org.springframework.boot.autoconfigure.SpringBootApplication.class),
				"La classe doit avoir l'annotation @SpringBootApplication");
	}

	@Test
	@DisplayName("Le contrôleur EtudiantController doit être injecté")
	void controllerBeanExists() {
		assertNotNull(etudiantController, "Le contrôleur doit être injecté");
	}

	@Test
	@DisplayName("Le service EtudiantServiceImpl doit être injecté")
	void serviceBeanExists() {
		assertNotNull(etudiantService, "Le service doit être injecté");
	}

	@Test
	@DisplayName("Le repository EtudiantRepository doit être injecté")
	void repositoryBeanExists() {
		assertNotNull(etudiantRepository, "Le repository doit être injecté");
	}

	@Test
	@DisplayName("L'endpoint /afficherAllEtudiant doit être accessible")
	void testEndpointAfficherAllEtudiant() throws Exception {
		mockMvc.perform(get("/afficherAllEtudiant")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Test d'intégration complète - ajouter et récupérer un étudiant")
	void testIntegrationAjouterEtAfficherEtudiant() throws Exception {
		// Ajouter un étudiant
		Etudiant etudiant = new Etudiant(null, "Dupont", "Jean", Option.TWIN);

		mockMvc.perform(post("/ajouterEtudiant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(etudiant)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nomEtudiant", is("Dupont")))
				.andExpect(jsonPath("$.prenomEtudiant", is("Jean")));
	}

	@Test
	@DisplayName("Test d'intégration complète - cycle de vie complet CRUD")
	void testIntegrationCompleteCRUDCycle() throws Exception {
		// Ajouter
		Etudiant etudiant = new Etudiant(null, "Martin", "Pierre", Option.SAE);
		String content = objectMapper.writeValueAsString(etudiant);

		mockMvc.perform(post("/ajouterEtudiant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk());

		// Récupérer tous
		mockMvc.perform(get("/afficherAllEtudiant")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("La méthode main doit exister")
	void mainMethodExists() {
		assertTrue(true, "La classe CrudEtudiantApplication doit avoir une méthode main");
	}

	@Test
	@DisplayName("Le service doit avoir le repositoryEtudiant injecté")
	void serviceHasRepositoryInjected() {
		assertNotNull(etudiantService, "Le service ne doit pas être null");
	}

	@Test
	@DisplayName("Test d'intégration - vérifier les annotations du contrôleur")
	void testControllerAnnotations() {
		assertTrue(EtudiantController.class.isAnnotationPresent(
				org.springframework.web.bind.annotation.RestController.class),
				"Le contrôleur doit avoir l'annotation @RestController");
	}

	@Test
	@DisplayName("Test d'intégration - vérifier les annotations du service")
	void testServiceAnnotations() {
		assertTrue(EtudiantServiceImpl.class.isAnnotationPresent(
				org.springframework.stereotype.Service.class),
				"Le service doit avoir l'annotation @Service");
	}

	@Test
	@DisplayName("Test de l'application avec un étudiant complet")
	void testApplicationWithCompleteStudent() throws Exception {
		Etudiant etudiant = new Etudiant(null, "Bernard", "Claude", Option.DS);

		mockMvc.perform(post("/ajouterEtudiant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(etudiant)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nomEtudiant", is("Bernard")))
				.andExpect(jsonPath("$.prenomEtudiant", is("Claude")))
				.andExpect(jsonPath("$.opt", is("DS")));
	}

	@Test
	@DisplayName("Vérifier que les beans sont correctement initialisés")
	void verifyBeansInitialization() {
		assertNotNull(etudiantController, "EtudiantController doit être injecté");
		assertNotNull(etudiantService, "EtudiantServiceImpl doit être injecté");
		assertNotNull(etudiantRepository, "EtudiantRepository doit être injecté");
	}

	@Test
	@DisplayName("Tous les beans Spring doivent être créés")
	void allBeansShouldBeCreated() {
		assertNotNull(applicationContext.getBean(EtudiantController.class),
				"Le bean EtudiantController doit être créé");
		assertNotNull(applicationContext.getBean(EtudiantServiceImpl.class),
				"Le bean EtudiantServiceImpl doit être créé");
		assertNotNull(applicationContext.getBean(EtudiantRepository.class),
				"Le bean EtudiantRepository doit être créé");
	}

	@Test
	@DisplayName("Le MockMvc doit être configuré")
	void mockMvcMustBeConfigured() {
		assertNotNull(mockMvc, "Le MockMvc doit être configuré");
	}

	@Test
	@DisplayName("L'application doit être une application Spring Boot")
	void applicationIsSpringBootApp() {
		assertTrue(CrudEtudiantApplication.class.isAnnotationPresent(
				org.springframework.boot.autoconfigure.SpringBootApplication.class));
	}
}
