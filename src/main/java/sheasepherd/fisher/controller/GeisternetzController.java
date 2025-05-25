package sheasepherd.fisher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sheasepherd.fisher.entitys.Geisternetz;
import sheasepherd.fisher.entitys.Mitglied;
import sheasepherd.fisher.repositorys.GeisternetzRepository;
import sheasepherd.fisher.services.MitgliedService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class GeisternetzController {

    Date currentTime = new Date();
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @Autowired
    private GeisternetzRepository geisternetzRepository;

    @Autowired
    private MitgliedService MitgliedService;

    @GetMapping("/melden")
    public String zeigeFormular(){
        return "melden";
    }

    @PostMapping("/gemeldeteGeisternetze")
    public String zeigeGemeldeteGeisternetze(){
        return "gemeldeteGeisternetze";
    }

    @PostMapping("/melden")
    public String verarbeiteFormular(
        @RequestParam("latitude") double latitude,
        @RequestParam("longitude") double longitude,
        @RequestParam("groesse") String groesse,
        @RequestParam(value = "anonym", defaultValue = "false", required = false) boolean anonym,
        Model model){

            Geisternetz geisternetz = new Geisternetz();

            geisternetz.setLatitude(latitude);
            geisternetz.setLongitude(longitude);
            geisternetz.setSize(groesse);
            geisternetz.setStatus("Gemeldet");
            geisternetz.setAnonym(anonym);
            geisternetz.setDate(dateFormatter.format(currentTime));

            if(!anonym){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String email = authentication.getName();
                Mitglied mitglied = MitgliedService.findByEmail(email);
                if(mitglied != null){
                    geisternetz.setMeldendePerson(mitglied);
                }
            }

            geisternetzRepository.save(geisternetz);

            System.out.println("Geisternetz gemeldet: " + groesse + ", " + latitude + ", " + longitude);

            model.addAttribute("latitude", latitude);
            model.addAttribute("longitude", longitude);
            model.addAttribute("groesse", groesse);
            model.addAttribute("anonym", anonym);
            model.addAttribute("date", geisternetz.getDate());

            return "bestaetigung";
    }
}
