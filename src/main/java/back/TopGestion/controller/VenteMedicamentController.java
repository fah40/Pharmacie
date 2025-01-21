package back.TopGestion.controller;

import back.TopGestion.model.Categorie;
import back.TopGestion.model.Client;
import back.TopGestion.model.Medicament;
import back.TopGestion.model.VenteMedicament;

import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vente")
public class VenteMedicamentController {

    @GetMapping("/list")
    public String getAllVentes(Model model) {
        try {
            Medicament[] medicaments = Medicament.getAll();
            model.addAttribute("medicaments", medicaments);

            Client[] clients = Client.getAll();
            model.addAttribute("clients", clients);

            VenteMedicament[] ventes = VenteMedicament.getAll();
            model.addAttribute("ventes", ventes);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération des ventes : " + e.getMessage());
        }
        return "listVentes"; // Correspond au fichier JSP "listVentes.jsp"
    }

    @PostMapping("/create")
    public String createVente(@RequestParam int idMedicament,
                            @RequestParam int quantiteVendue,
                            @RequestParam(required = false) Integer idClient,
                            @RequestParam(required = false) String nom,
                            @RequestParam(required = false) String adresse,
                            @RequestParam(required = false) String telephone,
                            @RequestParam String date,
                            Model model) {
        try {
            // Vérification et insertion du client si nécessaire
            if (idClient == null || idClient == 0) {
                if (nom == null || adresse == null || telephone == null) {
                    model.addAttribute("error", "Veuillez fournir le nom, l'adresse et le téléphone du client.");
                    return "redirect:/vente/create";
                }
                Client nouveauClient = new Client();
                nouveauClient.setNom(nom);
                nouveauClient.setAdresse(adresse);
                nouveauClient.setTelephone(telephone);
                nouveauClient.insert(); // Supposant que la méthode insert() ajoute le client en base
                idClient = Client.getLast().getIdClient();
            }

            // Création et insertion de la vente
            VenteMedicament vente = new VenteMedicament();
            vente.setMedicament(Medicament.getById(idMedicament));
            vente.setPrixUnitaire(Medicament.getById(idMedicament).getPrix());
            vente.setQuantiteVendue(quantiteVendue);
            vente.setClient(Client.getById(idClient));
            vente.setDateVente(Date.valueOf(date));

            vente.insert();

            model.addAttribute("message", "Vente créée avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la vente : " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/vente/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        try {
            VenteMedicament vente = VenteMedicament.getById(id);
            if (vente != null) {
                model.addAttribute("update", vente);
            } else {
                model.addAttribute("error", "Vente introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la récupération de la vente : " + e.getMessage());
        }
        return "editVente"; // Correspond au fichier JSP "editVente.jsp"
    }

    @PostMapping("/update")
    public String updateVente(@RequestParam int id,
                               @RequestParam int idMedicament,
                               @RequestParam int quantiteVendue,
                               Model model) {
        try {
            VenteMedicament vente = VenteMedicament.getById(id);
            if (vente != null) {
                vente.setMedicament(Medicament.getById(idMedicament));
                vente.setQuantiteVendue(quantiteVendue);
                vente.update();
                model.addAttribute("message", "Vente mise à jour avec succès.");
            } else {
                model.addAttribute("error", "Vente introuvable.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour de la vente : " + e.getMessage());
        }
        return "redirect:/vente/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteVente(@PathVariable int id, Model model) {
        try {
            VenteMedicament.deleteById(id);
            model.addAttribute("message", "Vente supprimée avec succès.");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression de la vente : " + e.getMessage());
        }
        return "redirect:/vente/list";
    }

    @PostMapping("/search")
    public String searchVente(@RequestParam(defaultValue = "0") int type,
                                    @RequestParam(defaultValue = "0") int idCategorie,
                                    @RequestParam String date,
                                    Model model) {
        try {
            VenteMedicament[] venteMedicaments = VenteMedicament.search(type, idCategorie,date);
            model.addAttribute("ventes", venteMedicaments);

            Categorie[] categorie = Categorie.getAll();
            model.addAttribute("categories", categorie);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la recherche des médicaments : " + e.getMessage());
        }
        return "listVentes"; // Reuse the same view to display results
    }
}
