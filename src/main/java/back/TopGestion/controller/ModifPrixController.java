package back.TopGestion.controller;

import back.TopGestion.model.ModifPrix;
import jakarta.servlet.http.HttpSession;
import back.TopGestion.model.Medicament;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/modifPrix")
public class ModifPrixController {

    Medicament[] medicaments = null;

    // Méthode pour afficher la liste des historiques de prix
    @GetMapping("/list")
    public String getAllHisto(Model model) {
        try {
            medicaments = Medicament.getAll();
            model.addAttribute("medicaments", medicaments);

            ModifPrix[] histos = ModifPrix.getAll(); // Récupérer tous les historiques
            model.addAttribute("modifPrixs", histos);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des historiques : " + e.getMessage());
        }
        return "listHistoriquePrix"; // Correspond au fichier JSP "listHistoriquePrix.jsp"
    }

    // Méthode pour créer une modification de prix
    @PostMapping("/create")
    public String createModifPrix(@RequestParam int idMedicament,
                                   @RequestParam double prix,
                                   @RequestParam String dateModif,
                                   HttpSession session,
                                   Model model) {
                                    
        try {

            Medicament medicament = Medicament.getById(idMedicament); // Récupérer le médicament par ID
            if (medicament == null) {
                throw new Exception("Le médicament avec l'ID spécifié n'existe pas.");
            }
            session.setAttribute("code","non");
            ModifPrix modifPrix = new ModifPrix();
            modifPrix.setMedicament(medicament);
            modifPrix.setPrix(prix);
            modifPrix.setDateModif(dateModif);
            modifPrix.insert();

            model.addAttribute("message", "Historique de prix créé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de l'historique de prix : " + e.getMessage());
        }
        return "redirect:/modifPrix/list";
    }

    // Méthode pour supprimer un historique de prix par ID
    @PostMapping("/delete/{id}")
    public String deleteModifPrix(@PathVariable int id, Model model) {
        try {
            ModifPrix.deleteById(id); // Supprimer l'historique par ID
            model.addAttribute("message", "Historique de prix supprimé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression de l'historique de prix : " + e.getMessage());
        }
        return "redirect:/modifPrix/list";
    }
}
