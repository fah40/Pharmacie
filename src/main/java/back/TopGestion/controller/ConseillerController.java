package back.TopGestion.controller;

import back.TopGestion.model.Conseiller;
import back.TopGestion.model.Medicament;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/conseiller")
public class ConseillerController {

    @GetMapping("/list")
    public String getAllConseillers(Model model) {
        try {
            Conseiller[] conseillers = Conseiller.getAll();
            model.addAttribute("conseillers", conseillers);

            Medicament[] medicaments = Medicament.getAll();
            model.addAttribute("medicaments", medicaments);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des conseillers : " + e.getMessage());
        }
        return "listConseillers"; // Correspond au fichier JSP "listConseillers.jsp"
    }

    @PostMapping("/create")
    public String createConseiller(@RequestParam int idMedicament,
                                    @RequestParam String date,
                                    Model model) {
        try {
            Conseiller conseiller = new Conseiller();
            conseiller.setMedicament(Medicament.getById(idMedicament));
            conseiller.setDate(date);
            conseiller.insert();
            System.out.println("-- "+ idMedicament+ " -- " +date);
            model.addAttribute("message", "Conseiller créé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création du conseiller : " + e.getMessage());
        }
        return "redirect:/conseiller/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            Conseiller conseiller = Conseiller.getById(id);
            if (conseiller != null) {
                model.addAttribute("update", conseiller);
            } else {
                model.addAttribute("error", "Conseiller introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération du conseiller : " + e.getMessage());
        }
        return "editConseiller"; // Correspond au fichier JSP "editConseiller.jsp"
    }

    @PostMapping("/update")
    public String updateConseiller(@RequestParam int id,
                                    @RequestParam int idMedicament,
                                    @RequestParam String date,
                                    Model model) {
        try {
            Conseiller conseiller = Conseiller.getById(id);
            if (conseiller != null) {
                conseiller.setMedicament(Medicament.getById(idMedicament));
                conseiller.setDate(date);
                conseiller.update();
                model.addAttribute("message", "Conseiller mis à jour avec succès.");
            } else {
                model.addAttribute("error", "Conseiller introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour du conseiller : " + e.getMessage());
        }
        return "redirect:/conseiller/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteConseiller(@PathVariable int id, Model model) {
        try {
            Conseiller.deleteById(id);
            model.addAttribute("message", "Conseiller supprimé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression du conseiller : " + e.getMessage());
        }
        return "redirect:/conseiller/list";
    }

    @PostMapping("/search")
    public String searchConseiller(@RequestParam String date,@RequestParam(defaultValue = "0") int annee,Model model) {
        try {
            System.err.println(date);
            Conseiller[] consiller = Conseiller.search(date,annee);
            model.addAttribute("conseillers", consiller);

            Medicament[] medicaments = Medicament.getAll();
            model.addAttribute("medicaments", medicaments);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la recherche des médicaments : " + e.getMessage());
        }
        return "listConseillers"; // Reuse the same view to display results
    }
}

