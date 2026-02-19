package tn.esprit.spring.crudetudiant.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.crudetudiant.entities.Etudiant;
import tn.esprit.spring.crudetudiant.services.IEtudiant;

import java.util.List;

@RestController
@AllArgsConstructor
public class EtudiantController {

    IEtudiant iEtudiant;
    @GetMapping("/afficherAllEtudiant")
    public List<Etudiant> afficherAllEtudiant(){
        List<Etudiant> etudiants = iEtudiant.afficherEtudiants();
        System.out.println("✅ SUCCESS: Récupération de tous les étudiants - " + etudiants.size() + " étudiant(s) trouvé(s)");
        return etudiants;
    }
    @GetMapping("/afficheById/{id}")
    public Etudiant afficherEtudiantByID(@PathVariable("id") Long id){
        return iEtudiant.afficherEtudiantById(id);
    }
    @PostMapping("/ajouterEtudiant")
    public Etudiant ajouterEtudiant(@RequestBody  Etudiant e){
        Etudiant etudiantAjoute = iEtudiant.ajouterEtudiant(e);
        System.out.println("✅ SUCCESS: Étudiant ajouté avec succès - ID: " + etudiantAjoute.getIdEtudiant() + ", Nom: " + etudiantAjoute.getPrenomEtudiant() + " " + etudiantAjoute.getNomEtudiant());
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
