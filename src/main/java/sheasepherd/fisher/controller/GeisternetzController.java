package sheasepherd.fisher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GeisternetzController {

    @GetMapping("/melden")
    public String zeigeFormular(){
        return "melden";
    }

    @PostMapping("/melden")
    public String verarbeiteFormular(
        @RequestParam("latitude") double latitude,
        @RequestParam("longtitude") double longtitude,
        @RequestParam("groesse") String groesse,
        Model model){

            System.out.println("Geisternetz gemeldet: " + groesse + ", " + latitude + ", " + longtitude);

            model.addAttribute("latitude", latitude);
            model.addAttribute("longtitude", longtitude);
            model.addAttribute("groesse", groesse);

            return "bestaetigung";
    }
}
