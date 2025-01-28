package back.TopGestion.controller;

import back.TopGestion.model.Etat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/etat")
public class EtatController {

    @GetMapping("/list")
    public String getEtat(Model model) {
        try {
            Etat[] etats = Etat.getAll();
            model.addAttribute("etats", etats);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des commission : " + e.getMessage());
        }
        return "etatCommission"; // Correspond au fichier JSP "listMaladies.jsp"
    }

    @PostMapping("/search")
    public String searche(Model model,
                          @RequestParam(required = false) String datemin,
                          @RequestParam(required = false) String datemax) {
        try {
            Etat[] etats = Etat.search(datemin,datemax);
            model.addAttribute("etats", etats);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des commission : " + e.getMessage());
        }
        return "etatCommission"; // Correspond au fichier JSP "listMaladies.jsp"
    }

}
