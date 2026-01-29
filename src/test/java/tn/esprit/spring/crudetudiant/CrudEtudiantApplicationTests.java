package tn.esprit.spring.crudetudiant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.ApplicationContext;
import tn.esprit.spring.crudetudiant.controllers.EtudiantController;
import tn.esprit.spring.crudetudiant.services.EtudiantServiceImpl;
import tn.esprit.spring.crudetudiant.repository.EtudiantRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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

	@BeforeEach
	void setUp() {
		assertNotNull(applicationContext, "Le contexte Spring doit être chargé");
	}

	@Test
	@DisplayName("Le contexte Spring doit se charger correctement")
	void contextLoads() {
		assertNotNull(applicationContext, "Le contexte Spring doit être chargé");
	}

	@Test
	@DisplayName("L'application doit démarrer sans erreur")
	void applicationStartsSuccessfully() {
		assertDoesNotThrow(() -> CrudEtudiantApplication.main(new String[]{}),
				"L'application doit démarrer sans erreur");
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
		mockMvc.perform(get("/afficherAllEtudiant"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("La méthode main doit exister")
	void mainMethodExists() {
		assertDoesNotThrow(() -> {
			CrudEtudiantApplication.class.getMethod("main", String[].class);
		}, "La méthode main doit exister");
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
