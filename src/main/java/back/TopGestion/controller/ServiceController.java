package back.TopGestion.controller;

import back.TopGestion.model.Maladie;
import back.TopGestion.model.Medicament;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/service")
public class ServiceController {

    @GetMapping("/list")
    public String getAllMedicaments(Model model) {
        try {
            Medicament[] medicaments = Medicament.getAll();
            model.addAttribute("medicaments", medicaments);

            Maladie[] maladies = Maladie.getAll();
            model.addAttribute("maladies", maladies);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des médicaments : " + e.getMessage());
        }
        return "services"; // Correspond au fichier JSP "listMedicaments.jsp"
    }

    @PostMapping("/search")
    public String searchMedicaments(@RequestParam(defaultValue = "0") int rmaladie,
                                    @RequestParam(defaultValue = "0") int ageMin,
                                    @RequestParam(defaultValue = "0") int ageMax,
                                    Model model) {
        try {
            Medicament[] medicaments = Medicament.search(rmaladie, ageMin, ageMax);
            model.addAttribute("medicaments", medicaments);

            Maladie[] maladies = Maladie.getAll();
            model.addAttribute("maladies", maladies);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la recherche des médicaments : " + e.getMessage());
        }
        return "services"; // Reuse the same view to display results
    }
}
