package sheasepherd.fisher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sheasepherd.fisher.entitys.Mitglied;
import sheasepherd.fisher.services.MitgliedService;

@Controller
public class MitgliedController {

    @Autowired
    private MitgliedService mitgliedService;

    @GetMapping("/werdeMitglied")
    public String mitglied(Model model){
        model.addAttribute("mitglied", new Mitglied());
        return "werdeMitglied";
    }

    @PostMapping("/werdeMitglied")
    public String registrieMitglied(@ModelAttribute Mitglied mitglied, Model model){
        if(mitgliedService.findByEmail(mitglied.getEmail()) != null){
            model.addAttribute("error", "Email bereits vergeben");
            return "werdeMitglied";
        }
        mitgliedService.save(mitglied);
        return "redirect:/login";
    }

}
