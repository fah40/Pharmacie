package back.TopGestion.controller;

import back.TopGestion.model.Laboratoire;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/laboratoire")
public class LaboratoireController {

    @GetMapping("/list")
    public String getAllLaboratoires(Model model) {
        try {
            Laboratoire[] laboratoires = Laboratoire.getAll();
            model.addAttribute("laboratoires", laboratoires);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des laboratoires : " + e.getMessage());
        }
        return "listLaboratoires"; // Correspond au fichier JSP "listLaboratoires.jsp"
    }

    @PostMapping("/create")
    public String createLaboratoire(@RequestParam String nom, Model model) {
        try {
            Laboratoire laboratoire = new Laboratoire();
            laboratoire.setNom(nom);
            laboratoire.insert();
            model.addAttribute("message", "Laboratoire créé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création du laboratoire : " + e.getMessage());
        }
        return "redirect:/laboratoire/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            Laboratoire laboratoire = Laboratoire.getById(id);
            if (laboratoire != null) {
                model.addAttribute("update", laboratoire);

                Laboratoire[] laboratoires = Laboratoire.getAll();
                model.addAttribute("laboratoires", laboratoires);
            } else {
                model.addAttribute("error", "Laboratoire introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération du laboratoire : " + e.getMessage());
        }
        return "listLaboratoires";
    }

    @PostMapping("/update")
    public String updateLaboratoire(@RequestParam int id, @RequestParam String nom, Model model) {
        try {
            Laboratoire laboratoire = Laboratoire.getById(id);
            if (laboratoire != null) {
                laboratoire.setNom(nom);
                laboratoire.update();
                model.addAttribute("message", "Laboratoire mis à jour avec succès.");
            } else {
                model.addAttribute("error", "Laboratoire introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour du laboratoire : " + e.getMessage());
        }
        return "redirect:/laboratoire/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteLaboratoire(@PathVariable int id, Model model) {
        try {
            Laboratoire.deleteById(id);
            model.addAttribute("message", "Laboratoire supprimé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression du laboratoire : " + e.getMessage());
        }
        return "redirect:/laboratoire/list";
    }
}
