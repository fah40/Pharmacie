package back.TopGestion.controller;

import back.TopGestion.model.Categorie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorie")
public class CategorieController {

    @GetMapping("/list")
    public String getAllCategories(Model model) {
        try {
            Categorie[] categories = Categorie.getAll();
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des Categories : " + e.getMessage());
        }
        return "listCategories"; // Correspond au fichier JSP "listCategories.jsp"
    }

    @PostMapping("/create")
    public String createCategorie(@RequestParam String nom,
                                 Model model) {
        try {
            Categorie categorie = new Categorie();
            categorie.setNom(nom);
            categorie.insert();
            model.addAttribute("message", "categorie créée avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création du categorie : " + e.getMessage());
        }
        return "redirect:/categorie/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            Categorie categorie = Categorie.getById(id);
            if (categorie != null) {
                model.addAttribute("update", categorie);

                Categorie[] categories = Categorie.getAll();
                model.addAttribute("Categories", categories);
            } else {
                model.addAttribute("error", "categorie introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération du categorie : " + e.getMessage());
        }
        return "listCategories";
    }

    @PostMapping("/update")
    public String updateCategorie(@RequestParam int id,
                                 @RequestParam String nom,
                                 Model model) {
        try {
            Categorie categorie = Categorie.getById(id);
            if (categorie != null) {
                categorie.setNom(nom);
                categorie.update();
                model.addAttribute("message", "categorie mise à jour avec succès.");
            } else {
                model.addAttribute("error", "categorie introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour du categorie : " + e.getMessage());
        }
        return "redirect:/categorie/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategorie(@PathVariable int id, Model model) {
        try {
            Categorie.deleteById(id);
            model.addAttribute("message", "Categorie supprimée avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression du Categorie : " + e.getMessage());
        }
        return "redirect:/Categorie/list";
    }
}
