package back.TopGestion.controller;

import back.TopGestion.model.Matiere;
import back.TopGestion.model.Unite;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matiere")
public class MatiereController {

    @GetMapping("/list")
    public String getAllMatieres(Model model) {
        try {
            Matiere[] matieres = Matiere.getAll();
            model.addAttribute("matieres", matieres);
            
            Unite[] unites= Unite.getAll();
            model.addAttribute("unites", unites);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des matières : " + e.getMessage());
        }
        return "listMatieres"; // Correspond au fichier JSP "listMatieres.jsp"
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        return "createMatiere"; // Correspond au fichier JSP "createMatiere.jsp"
    }
    
    @PostMapping("/create")
    public String createMatiere(@RequestParam String nom,
                                @RequestParam int idUnite,
                                Model model) {
        try {
            Matiere matiere = new Matiere();
            matiere.setNom(nom);
            matiere.setIdUnite(idUnite);
            matiere.insert();
            model.addAttribute("message", "Matière créée avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la matière : " + e.getMessage());
        }
        return "redirect:/matiere/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            Matiere matiere = Matiere.getById(id);
            if (matiere != null) {
                model.addAttribute("update", matiere);
            } else {
                model.addAttribute("error", "Matière introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération de la matière : " + e.getMessage());
        }
        return "editMatiere"; // Correspond au fichier JSP "editMatiere.jsp"
    }

    @PostMapping("/update")
    public String updateMatiere(@RequestParam int id,
                                @RequestParam String nom,
                                @RequestParam double quantiteStock,
                                @RequestParam int idUnite,
                                Model model) {
        try {
            Matiere matiere = Matiere.getById(id);
            if (matiere != null) {
                matiere.setNom(nom);
                matiere.setQuantiteStock(quantiteStock);
                matiere.setIdUnite(idUnite);
                matiere.update();
                model.addAttribute("message", "Matière mise à jour avec succès.");
            } else {
                model.addAttribute("error", "Matière introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour de la matière : " + e.getMessage());
        }
        return "redirect:/matiere/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteMatiere(@PathVariable int id, Model model) {
        try {
            Matiere.deleteById(id);
            model.addAttribute("message", "Matière supprimée avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression de la matière : " + e.getMessage());
        }
        return "redirect:/matiere/list";
    }
}
