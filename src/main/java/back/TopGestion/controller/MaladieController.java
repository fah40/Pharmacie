package back.TopGestion.controller;

import back.TopGestion.model.Maladie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/maladie")
public class MaladieController {

    @GetMapping("/list")
    public String getAllMaladies(Model model) {
        try {
            Maladie[] maladies = Maladie.getAll();
            model.addAttribute("maladies", maladies);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des maladies : " + e.getMessage());
        }
        return "listMaladies"; // Correspond au fichier JSP "listMaladies.jsp"
    }

    @PostMapping("/create")
    public String createMaladie(@RequestParam String nom,
                                 @RequestParam String description,
                                 Model model) {
        try {
            Maladie maladie = new Maladie();
            maladie.setNom(nom);
            maladie.setDescription(description);
            maladie.insert();
            model.addAttribute("message", "Maladie créée avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la maladie : " + e.getMessage());
        }
        return "redirect:/maladie/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            Maladie maladie = Maladie.getById(id);
            if (maladie != null) {
                model.addAttribute("update", maladie);
                
                Maladie[] maladies = Maladie.getAll();
                model.addAttribute("maladies", maladies);
            } else {
                model.addAttribute("error", "Maladie introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération de la maladie : " + e.getMessage());
        }
        return "listMaladies";
    }

    @PostMapping("/update")
    public String updateMaladie(@RequestParam int id,
                                 @RequestParam String nom,
                                 @RequestParam String description,
                                 Model model) {
        try {
            Maladie maladie = Maladie.getById(id);
            if (maladie != null) {
                maladie.setNom(nom);
                maladie.setDescription(description);
                maladie.update();
                model.addAttribute("message", "Maladie mise à jour avec succès.");
            } else {
                model.addAttribute("error", "Maladie introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour de la maladie : " + e.getMessage());
        }
        return "redirect:/maladie/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteMaladie(@PathVariable int id, Model model) {
        try {
            Maladie.deleteById(id);
            model.addAttribute("message", "Maladie supprimée avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression de la maladie : " + e.getMessage());
        }
        return "redirect:/maladie/list";
    }
}
