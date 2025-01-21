package back.TopGestion.controller;

import back.TopGestion.model.Laboratoire;
import back.TopGestion.model.Medicament;
import back.TopGestion.model.Production;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/production")
public class ProductionController {

    // Méthode pour afficher la liste des productions
    @GetMapping("/list")
    public String getAllProductions(Model model) {
        try {
            Medicament[] medicaments = Medicament.getAll();
            model.addAttribute("medicaments", medicaments);

            Production[] productions = Production.getAll();
            model.addAttribute("productions", productions);

            Laboratoire[] laboratoires = Laboratoire.getAll();
            model.addAttribute("laboratoires", laboratoires);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des productions : " + e.getMessage());
        }
        return "listProductions"; // Correspond au fichier JSP "listProductions.jsp"
    }

    // Méthode pour créer une nouvelle production
    @PostMapping("/create")
    public String createProduction(@RequestParam int idMedicament,
                                   @RequestParam int idLaboratoire,
                                   @RequestParam int quantiteProduite,
                                   @RequestParam double prix,
                                   Model model) {
        try {
            Production production = new Production();
            production.setIdMedicament(idMedicament);
            production.setQuantiteProduite(quantiteProduite);
            production.setLaboratoire(Laboratoire.getById(idLaboratoire));
            production.setPrix(prix);
            production.insert();
            model.addAttribute("message", "Production créée avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la production : " + e.getMessage());
        }
        return "redirect:/production/list";
    }

    // Méthode pour supprimer une production par son ID
    @PostMapping("/delete/{id}")
    public String deleteProduction(@PathVariable int id, Model model) {
        try {
            Production.deleteById(id);
            model.addAttribute("message", "Production supprimée avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression de la production : " + e.getMessage());
        }
        return "redirect:/production/list";
    }
}
