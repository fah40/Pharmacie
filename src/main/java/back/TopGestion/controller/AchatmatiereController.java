package back.TopGestion.controller;

import back.TopGestion.model.Achatmatiere;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/achatmatiere")
public class AchatmatiereController {

    @GetMapping("/list")
    public String getAllAchats(Model model) {
        try {
            Achatmatiere[] achats = Achatmatiere.getAll().toArray(new Achatmatiere[0]);
            model.addAttribute("achats", achats);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des achats : " + e.getMessage());
        }
        return "listAchats"; // Correspond au fichier JSP "listAchats.jsp"
    }

    @PostMapping("/create")
    public String createAchat(@RequestParam int idmatiere,
                               @RequestParam double quantiteAchetee,
                               @RequestParam double prixUnitaire,
                               Model model) {
        try {
            Achatmatiere achat = new Achatmatiere();
            achat.setIdmatiere(idmatiere);
            achat.setQuantiteAchetee(quantiteAchetee);
            achat.setPrixUnitaire(prixUnitaire);
            achat.insert();
            model.addAttribute("message", "Achat de matière créé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de l'achat : " + e.getMessage());
        }
        return "redirect:/achatmatiere/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            Achatmatiere achat = Achatmatiere.getById(id);
            if (achat != null) {
                model.addAttribute("update", achat);
            } else {
                model.addAttribute("error", "Achat introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération de l'achat : " + e.getMessage());
        }
        return "editAchat"; // Correspond au fichier JSP "editAchat.jsp"
    }

    @PostMapping("/update")
    public String updateAchat(@RequestParam int id,
                               @RequestParam int idmatiere,
                               @RequestParam double quantiteAchetee,
                               @RequestParam double prixUnitaire,
                               Model model) {
        try {
            Achatmatiere achat = Achatmatiere.getById(id);
            if (achat != null) {
                achat.setIdmatiere(idmatiere);
                achat.setQuantiteAchetee(quantiteAchetee);
                achat.setPrixUnitaire(prixUnitaire);
                achat.update();
                model.addAttribute("message", "Achat mis à jour avec succès.");
            } else {
                model.addAttribute("error", "Achat introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour de l'achat : " + e.getMessage());
        }
        return "redirect:/achatmatiere/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteAchat(@PathVariable int id, Model model) {
        try {
            Achatmatiere.deleteById(id);
            model.addAttribute("message", "Achat supprimé avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression de l'achat : " + e.getMessage());
        }
        return "redirect:/achatmatiere/list";
    }
}
