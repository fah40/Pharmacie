package back.TopGestion.controller;

import back.TopGestion.model.Categorie;
import back.TopGestion.model.Maladie;
import back.TopGestion.model.Medicament;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medicament")
public class MedicamentController {

    @GetMapping("/list")
    public String getAllMedicaments(Model model) {
        try {
            Medicament[] medicaments = Medicament.getAll();
            model.addAttribute("medicaments", medicaments);

            Maladie[] maladies = Maladie.getAll();
            model.addAttribute("maladies", maladies);

            Categorie[] categories = Categorie.getAll();
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des médicaments : " + e.getMessage());
        }
        return "listMedicaments"; // Correspond au fichier JSP "listMedicaments.jsp"
    }

    @PostMapping("/create")
    public String createMedicament(@RequestParam String nom,
                                   @RequestParam int idMaladie,
                                   @RequestParam String description,
                                   @RequestParam int ageMin,
                                   @RequestParam int ageMax,
                                   @RequestParam int idCategorie,
                                   Model model) {
        try {
            Medicament medicament = new Medicament();
            medicament.setNom(nom);
            medicament.setMaladie(Maladie.getById(idMaladie));
            medicament.setDescription(description);
            medicament.setAgeMin(ageMin);
            medicament.setAgeMax(ageMax);
            medicament.setCategorie(Categorie.getById(idCategorie));
            medicament.insert();
            model.addAttribute("message", "Médicament créé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création du médicament : " + e.getMessage());
        }
        return "redirect:/medicament/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            Medicament medicament = Medicament.getById(id);

            Medicament[] medicaments = Medicament.getAll();
            model.addAttribute("medicaments", medicaments);

            Maladie[] maladies = Maladie.getAll();
            model.addAttribute("maladies", maladies);

            Categorie[] categories = Categorie.getAll();
            model.addAttribute("categories", categories);
            if (medicament != null) {
                model.addAttribute("update", medicament);
            } else {
                model.addAttribute("error", "Médicament introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération du médicament : " + e.getMessage());
        }
        return "listMedicaments"; // Correspond au fichier JSP "editMedicament.jsp"
    }

    @PostMapping("/update")
    public String updateMedicament(@RequestParam int id,
                                   @RequestParam String nom,
                                   @RequestParam int idMaladie,
                                   @RequestParam String description,
                                   @RequestParam int ageMin,
                                   @RequestParam int ageMax,
                                   @RequestParam int idCategorie,
                                   Model model) {
        try {
            Medicament medicament = Medicament.getById(id);
            if (medicament != null) {
                medicament.setNom(nom);
                medicament.setMaladie(Maladie.getById(idMaladie));
                medicament.setDescription(description);
                medicament.setAgeMin(ageMin);
                medicament.setAgeMax(ageMax);
                medicament.setCategorie(Categorie.getById(idCategorie));
                medicament.update();
                model.addAttribute("message", "Médicament mis à jour avec succès.");
            } else {
                model.addAttribute("error", "Médicament introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour du médicament : " + e.getMessage());
        }
        return "redirect:/medicament/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteMedicament(@PathVariable int id, Model model) {
        try {
            Medicament.deleteById(id);
            model.addAttribute("message", "Médicament supprimé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression du médicament : " + e.getMessage());
        }
        return "redirect:/medicament/list";
    }
}
