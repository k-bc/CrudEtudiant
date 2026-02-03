package tn.esprit.spring.crudetudiant.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import tn.esprit.spring.crudetudiant.entities.Etudiant;
import tn.esprit.spring.crudetudiant.entities.Option;
import tn.esprit.spring.crudetudiant.services.IEtudiant;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(EtudiantController.class)
@DisplayName("Tests pour le controleur EtudiantController")
class EtudiantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiant iEtudiant;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        etudiant2 = new Etudiant(2L, "Martin", "Pierre", Option.SAE);
    }

    @Test
    @DisplayName("GET /afficherAllEtudiant - doit retourner tous les étudiants")
    void testAfficherAllEtudiant() throws Exception {
        // Arrangement
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);
        when(iEtudiant.afficherEtudiants()).thenReturn(etudiants);

        // Action & Assertion
        mockMvc.perform(get("/afficherAllEtudiant")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].idEtudiant", is(1)))
                .andExpect(jsonPath("$[0].nomEtudiant", is("Dupont")))
                .andExpect(jsonPath("$[0].prenomEtudiant", is("Jean")))
                .andExpect(jsonPath("$[1].idEtudiant", is(2)))
                .andExpect(jsonPath("$[1].nomEtudiant", is("Martin")))
                .andExpect(jsonPath("$[1].prenomEtudiant", is("Pierre")));

        verify(iEtudiant, times(1)).afficherEtudiants();
    }

    @Test
    @DisplayName("GET /afficherAllEtudiant - doit retourner une liste vide")
    void testAfficherAllEtudiant_EmptyList() throws Exception {
        // Arrangement
        when(iEtudiant.afficherEtudiants()).thenReturn(Collections.emptyList());

        // Action & Assertion
        mockMvc.perform(get("/afficherAllEtudiant")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(iEtudiant, times(1)).afficherEtudiants();
    }

    @Test
    @DisplayName("GET /afficheById/{id} - doit retourner un étudiant par son ID")
    void testAfficherEtudiantByID() throws Exception {
        // Arrangement
        when(iEtudiant.afficherEtudiantById(1L)).thenReturn(etudiant1);

        // Action & Assertion
        mockMvc.perform(get("/afficheById/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant", is(1)))
                .andExpect(jsonPath("$.nomEtudiant", is("Dupont")))
                .andExpect(jsonPath("$.prenomEtudiant", is("Jean")))
                .andExpect(jsonPath("$.opt", is("TWIN")));

        verify(iEtudiant, times(1)).afficherEtudiantById(1L);
    }

    @Test
    @DisplayName("GET /afficheById/{id} - doit retourner null si l'étudiant n'existe pas")
    void testAfficherEtudiantByID_NotFound() throws Exception {
        // Arrangement
        when(iEtudiant.afficherEtudiantById(999L)).thenReturn(null);

        // Action & Assertion
        mockMvc.perform(get("/afficheById/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(iEtudiant, times(1)).afficherEtudiantById(999L);
    }

    @Test
    @DisplayName("POST /ajouterEtudiant - doit ajouter un nouvel etudiant")
    void testAjouterEtudiant() throws Exception {
        // Arrangement
        Etudiant nouvelEtudiant = new Etudiant(null, "Thomas", "Luc", Option.DS);
        Etudiant etudiantSauvegarde = new Etudiant(3L, "Thomas", "Luc", Option.DS);
        when(iEtudiant.ajouterEtudiant(Mockito.any(Etudiant.class))).thenReturn(etudiantSauvegarde);

        // Action & Assertion
        mockMvc.perform(post("/ajouterEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nouvelEtudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant", is(3)))
                .andExpect(jsonPath("$.nomEtudiant", is("Thomas")))
                .andExpect(jsonPath("$.prenomEtudiant", is("Luc")))
                .andExpect(jsonPath("$.opt", is("DS")));

        verify(iEtudiant, times(1)).ajouterEtudiant(Mockito.any(Etudiant.class));
    }

    @Test
    @DisplayName("POST /ajouterEtudiant - doit ajouter un etudiant avec tous les champs")
    void testAjouterEtudiant_Complete() throws Exception {
        // Arrangement
        Etudiant nouvelEtudiant = new Etudiant(null, "Dupont", "Jean", Option.TWIN);
        Etudiant etudiantSauvegarde = new Etudiant(4L, "Dupont", "Jean", Option.TWIN);
        when(iEtudiant.ajouterEtudiant(Mockito.any(Etudiant.class))).thenReturn(etudiantSauvegarde);

        // Action & Assertion
        mockMvc.perform(post("/ajouterEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nouvelEtudiant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant", is(4)))
                .andExpect(jsonPath("$.nomEtudiant", is("Dupont")));

        verify(iEtudiant, times(1)).ajouterEtudiant(Mockito.any(Etudiant.class));
    }

    @Test
    @DisplayName("PUT /modifierEtudiant - doit modifier un etudiant existant")
    void testModifierEtudiant() throws Exception {
        // Arrangement
        Etudiant etudiantModifie = new Etudiant(1L, "Dupont", "Jean-Marie", Option.DS);
        when(iEtudiant.modifierEtudiant(Mockito.any(Etudiant.class))).thenReturn(etudiantModifie);

        // Action & Assertion
        mockMvc.perform(put("/modifierEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(etudiantModifie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant", is(1)))
                .andExpect(jsonPath("$.nomEtudiant", is("Dupont")))
                .andExpect(jsonPath("$.prenomEtudiant", is("Jean-Marie")))
                .andExpect(jsonPath("$.opt", is("DS")));

        verify(iEtudiant, times(1)).modifierEtudiant(Mockito.any(Etudiant.class));
    }

    @Test
    @DisplayName("PUT /modifierEtudiant - doit modifier le prenom d'un etudiant")
    void testModifierEtudiant_ChangePrenom() throws Exception {
        // Arrangement
        Etudiant etudiantModifie = new Etudiant(2L, "Martin", "Paul", Option.SAE);
        when(iEtudiant.modifierEtudiant(Mockito.any(Etudiant.class))).thenReturn(etudiantModifie);

        // Action & Assertion
        mockMvc.perform(put("/modifierEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(etudiantModifie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenomEtudiant", is("Paul")));

        verify(iEtudiant, times(1)).modifierEtudiant(Mockito.any(Etudiant.class));
    }

    @Test
    @DisplayName("DELETE /supprimer/{id} - doit supprimer un étudiant par son ID")
    void testSupprimerEtudiant() throws Exception {
        // Arrangement
        doNothing().when(iEtudiant).supprimerEtudiant(1L);

        // Action & Assertion
        mockMvc.perform(delete("/supprimer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(iEtudiant, times(1)).supprimerEtudiant(1L);
    }

    @Test
    @DisplayName("DELETE /supprimer/{id} - doit gérer la suppression d'un ID invalide")
    void testSupprimerEtudiant_InvalidId() throws Exception {
        // Arrangement
        doNothing().when(iEtudiant).supprimerEtudiant(999L);

        // Action & Assertion
        mockMvc.perform(delete("/supprimer/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(iEtudiant, times(1)).supprimerEtudiant(999L);
    }

    @Test
    @DisplayName("GET /afficherAllEtudiant - vérifier le type de contenu")
    void testAfficherAllEtudiant_ContentType() throws Exception {
        // Arrangement
        when(iEtudiant.afficherEtudiants()).thenReturn(Collections.singletonList(etudiant1));

        // Action & Assertion
        mockMvc.perform(get("/afficherAllEtudiant"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

