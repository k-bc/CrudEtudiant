package tn.esprit.spring.crudetudiant.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.crudetudiant.entities.Etudiant;
import tn.esprit.spring.crudetudiant.services.IEtudiant;

import java.util.List;

@RestController
@AllArgsConstructor
public class EtudiantController {

    private static final Logger logger = LoggerFactory.getLogger(EtudiantController.class);
    IEtudiant iEtudiant;
    @GetMapping("/afficherAllEtudiant")
    public List<Etudiant> afficherAllEtudiant(){
        List<Etudiant> etudiants = iEtudiant.afficherEtudiants();
        logger.info("✅ SUCCESS: Récupération de tous les étudiants - {} étudiant(s) trouvé(s)", etudiants.size());
        return etudiants;
    }
    @GetMapping("/afficheById/{id}")
    public Etudiant afficherEtudiantByID(@PathVariable("id") Long id){
        return iEtudiant.afficherEtudiantById(id);
    }
    @PostMapping("/ajouterEtudiant")
    public Etudiant ajouterEtudiant(@RequestBody  Etudiant e){
        Etudiant etudiantAjoute = iEtudiant.ajouterEtudiant(e);
        logger.info("✅ SUCCESS: Étudiant ajouté avec succès - ID: {}, Nom: {} {}", etudiantAjoute.getIdEtudiant(), etudiantAjoute.getPrenomEtudiant(), etudiantAjoute.getNomEtudiant());
        return etudiantAjoute;
    }
    @PutMapping("/modifierEtudiant")
    public Etudiant modifierEtudiant(@RequestBody Etudiant e){
        return  iEtudiant.modifierEtudiant(e);
    }
    @DeleteMapping("/supprimer/{id}")
    public void supprimerEtudiant(@PathVariable("id") long id){
        iEtudiant.supprimerEtudiant(id);
    }

}
