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
@DisplayName("Tests avancés pour le contrôleur EtudiantController")
class EtudiantControllerAdvancedTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiant iEtudiant;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Etudiant etudiant1;
    private Etudiant etudiant2;
    private Etudiant etudiant3;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant(1L, "Dupont", "Jean", Option.TWIN);
        etudiant2 = new Etudiant(2L, "Martin", "Pierre", Option.SAE);
        etudiant3 = new Etudiant(3L, "Bernard", "Claude", Option.DS);
    }

    @Test
    @DisplayName("POST /ajouterEtudiant - ajouter avec option TWIN")
    void testAjouterEtudiant_OptionTWIN() throws Exception {
        Etudiant newStudent = new Etudiant(null, "Test", "Twin", Option.TWIN);
        Etudiant saved = new Etudiant(4L, "Test", "Twin", Option.TWIN);
        when(iEtudiant.ajouterEtudiant(Mockito.any())).thenReturn(saved);

        mockMvc.perform(post("/ajouterEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opt", is("TWIN")));
    }

    @Test
    @DisplayName("POST /ajouterEtudiant - ajouter avec option SAE")
    void testAjouterEtudiant_OptionSAE() throws Exception {
        Etudiant newStudent = new Etudiant(null, "Test", "Sae", Option.SAE);
        Etudiant saved = new Etudiant(5L, "Test", "Sae", Option.SAE);
        when(iEtudiant.ajouterEtudiant(Mockito.any())).thenReturn(saved);

        mockMvc.perform(post("/ajouterEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opt", is("SAE")));
    }

    @Test
    @DisplayName("POST /ajouterEtudiant - ajouter avec option DS")
    void testAjouterEtudiant_OptionDS() throws Exception {
        Etudiant newStudent = new Etudiant(null, "Test", "Ds", Option.DS);
        Etudiant saved = new Etudiant(6L, "Test", "Ds", Option.DS);
        when(iEtudiant.ajouterEtudiant(Mockito.any())).thenReturn(saved);

        mockMvc.perform(post("/ajouterEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opt", is("DS")));
    }

    @Test
    @DisplayName("PUT /modifierEtudiant - changer tous les champs")
    void testModifierEtudiant_ChangeAllFields() throws Exception {
        Etudiant modified = new Etudiant(1L, "NewName", "NewPrenom", Option.DS);
        when(iEtudiant.modifierEtudiant(Mockito.any())).thenReturn(modified);

        mockMvc.perform(put("/modifierEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modified)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant", is("NewName")))
                .andExpect(jsonPath("$.prenomEtudiant", is("NewPrenom")))
                .andExpect(jsonPath("$.opt", is("DS")));
    }

    @Test
    @DisplayName("GET /afficherAllEtudiant - avec 3 étudiants")
    void testAfficherAllEtudiant_ThreeStudents() throws Exception {
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2, etudiant3);
        when(iEtudiant.afficherEtudiants()).thenReturn(etudiants);

        mockMvc.perform(get("/afficherAllEtudiant")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].nomEtudiant", is("Dupont")))
                .andExpect(jsonPath("$[1].nomEtudiant", is("Martin")))
                .andExpect(jsonPath("$[2].nomEtudiant", is("Bernard")));
    }

    @Test
    @DisplayName("GET /afficheById - avec différents IDs")
    void testAfficherEtudiantByID_DifferentIds() throws Exception {
        when(iEtudiant.afficherEtudiantById(1L)).thenReturn(etudiant1);
        when(iEtudiant.afficherEtudiantById(2L)).thenReturn(etudiant2);
        when(iEtudiant.afficherEtudiantById(3L)).thenReturn(etudiant3);

        // Test ID 1
        mockMvc.perform(get("/afficheById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant", is("Dupont")));

        // Test ID 2
        mockMvc.perform(get("/afficheById/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant", is("Martin")));

        // Test ID 3
        mockMvc.perform(get("/afficheById/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant", is("Bernard")));
    }

    @Test
    @DisplayName("DELETE /supprimer - supprimer 3 étudiants")
    void testSupprimerEtudiant_DeleteMultiple() throws Exception {
        doNothing().when(iEtudiant).supprimerEtudiant(1L);
        doNothing().when(iEtudiant).supprimerEtudiant(2L);
        doNothing().when(iEtudiant).supprimerEtudiant(3L);

        mockMvc.perform(delete("/supprimer/1")).andExpect(status().isOk());
        mockMvc.perform(delete("/supprimer/2")).andExpect(status().isOk());
        mockMvc.perform(delete("/supprimer/3")).andExpect(status().isOk());

        verify(iEtudiant, times(1)).supprimerEtudiant(1L);
        verify(iEtudiant, times(1)).supprimerEtudiant(2L);
        verify(iEtudiant, times(1)).supprimerEtudiant(3L);
    }

    @Test
    @DisplayName("GET /afficherAllEtudiant - vérifier les appels de service")
    void testAfficherAllEtudiant_VerifyMultipleCalls() throws Exception {
        when(iEtudiant.afficherEtudiants()).thenReturn(Collections.singletonList(etudiant1));

        // Faire plusieurs appels
        mockMvc.perform(get("/afficherAllEtudiant")).andExpect(status().isOk());
        mockMvc.perform(get("/afficherAllEtudiant")).andExpect(status().isOk());
        mockMvc.perform(get("/afficherAllEtudiant")).andExpect(status().isOk());

        // Vérifier que le service a été appelé 3 fois
        verify(iEtudiant, times(3)).afficherEtudiants();
    }

    @Test
    @DisplayName("POST /ajouterEtudiant - vérifier les appels de service")
    void testAjouterEtudiant_VerifyMultipleCalls() throws Exception {
        Etudiant student1 = new Etudiant(null, "Test1", "User1", Option.TWIN);
        Etudiant saved1 = new Etudiant(7L, "Test1", "User1", Option.TWIN);
        when(iEtudiant.ajouterEtudiant(Mockito.any())).thenReturn(saved1);

        mockMvc.perform(post("/ajouterEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().isOk());

        verify(iEtudiant, times(1)).ajouterEtudiant(Mockito.any());
    }

    @Test
    @DisplayName("PUT /modifierEtudiant - vérifier les appels de service")
    void testModifierEtudiant_VerifyMultipleCalls() throws Exception {
        Etudiant modified = new Etudiant(1L, "Modified", "Name", Option.SAE);
        when(iEtudiant.modifierEtudiant(Mockito.any())).thenReturn(modified);

        // Faire plusieurs appels
        mockMvc.perform(put("/modifierEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modified)))
                .andExpect(status().isOk());

        verify(iEtudiant, times(1)).modifierEtudiant(Mockito.any());
    }

    @Test
    @DisplayName("DELETE /supprimer - vérifier les appels de service")
    void testSupprimerEtudiant_VerifyMultipleCalls() throws Exception {
        doNothing().when(iEtudiant).supprimerEtudiant(Mockito.anyLong());

        // Faire plusieurs appels
        mockMvc.perform(delete("/supprimer/1")).andExpect(status().isOk());
        mockMvc.perform(delete("/supprimer/2")).andExpect(status().isOk());

        verify(iEtudiant, times(2)).supprimerEtudiant(Mockito.anyLong());
    }

    @Test
    @DisplayName("POST /ajouterEtudiant - retourner les bons codes HTTP")
    void testAjouterEtudiant_HttpStatus() throws Exception {
        Etudiant newStudent = new Etudiant(null, "Test", "User", Option.TWIN);
        Etudiant saved = new Etudiant(8L, "Test", "User", Option.TWIN);
        when(iEtudiant.ajouterEtudiant(Mockito.any())).thenReturn(saved);

        mockMvc.perform(post("/ajouterEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /afficheById - retourner les bons codes HTTP")
    void testAfficherEtudiantByID_HttpStatus() throws Exception {
        when(iEtudiant.afficherEtudiantById(1L)).thenReturn(etudiant1);

        mockMvc.perform(get("/afficheById/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /modifierEtudiant - retourner les bons codes HTTP")
    void testModifierEtudiant_HttpStatus() throws Exception {
        Etudiant modified = new Etudiant(1L, "Modified", "Name", Option.DS);
        when(iEtudiant.modifierEtudiant(Mockito.any())).thenReturn(modified);

        mockMvc.perform(put("/modifierEtudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modified)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /supprimer - retourner les bons codes HTTP")
    void testSupprimerEtudiant_HttpStatus() throws Exception {
        doNothing().when(iEtudiant).supprimerEtudiant(1L);

        mockMvc.perform(delete("/supprimer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /afficherAllEtudiant - vérifier la structure JSON complète")
    void testAfficherAllEtudiant_CompleteJsonStructure() throws Exception {
        when(iEtudiant.afficherEtudiants()).thenReturn(Collections.singletonList(etudiant1));

        mockMvc.perform(get("/afficherAllEtudiant")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEtudiant", is(1)))
                .andExpect(jsonPath("$[0].nomEtudiant", is("Dupont")))
                .andExpect(jsonPath("$[0].prenomEtudiant", is("Jean")))
                .andExpect(jsonPath("$[0].opt", is("TWIN")));
    }
}

