package back.TopGestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    @GetMapping("/bobo")
    public String home(Model model) {
        model.addAttribute("message", "Bienvenue sur Spring Boot avec JSP !");
        return "index"; // Correspond à /WEB-INF/views/index.jsp
    }
}