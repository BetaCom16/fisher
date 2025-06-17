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
import java.util.List;

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

    @GetMapping("/bergung")
    public String zeigeBergbareNetze(Model model) {
        model.addAttribute("geisternetze", geisternetzRepository.findByStatusAndBergendePersonIsNull("Gemeldet"));
        return "bergung";
    }

    @PostMapping("/bergung")
    public String eintragenZurBergung(@RequestParam("netzId") int netzId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Mitglied mitglied = MitgliedService.findByEmail(auth.getName());

        Geisternetz netz = geisternetzRepository.findById((long) netzId).orElse(null);

        if (mitglied != null && netz != null && netz.getBergendePerson() == null) {
            netz.setBergendePerson(mitglied);
            netz.setStatus("Bergung bevorstehend");
            geisternetzRepository.save(netz);
        }

        return "redirect:/bergung";
    }

    @GetMapping("/meineBergungen")
    public String zeigeEigeneBergungen(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Mitglied mitglied = MitgliedService.findByEmail(auth.getName());

        if (mitglied != null) {
            List<Geisternetz> eigeneNetze = geisternetzRepository.findByBergendePersonAndStatusNot(mitglied, "Geborgen");
            model.addAttribute("meineNetze", eigeneNetze);
        }

        return "meineBergungen";
    }

    @PostMapping("/netz-als-geborgen")
    public String alsGeborgenMelden(@RequestParam("netzId") int netzId) {
        Geisternetz netz = geisternetzRepository.findById((long) netzId).orElse(null);

        if (netz != null && "Bergung bevorstehend".equals(netz.getStatus())) {
            netz.setStatus("Geborgen");
            geisternetzRepository.save(netz);
        }

        return "redirect:/meineBergungen";
    }

    @GetMapping("/geborgeneNetze")
    public String zeigeGeborgeneNetze(Model model) {
        model.addAttribute("geborgeneNetze", geisternetzRepository.findByStatus("Geborgen"));
        return "geborgeneNetze";
    }

    @PostMapping("/netz-freigeben")
    public String bergungAbgeben(@RequestParam("netzId") int netzId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Mitglied mitglied = MitgliedService.findByEmail(auth.getName());

        Geisternetz netz = geisternetzRepository.findById((long) netzId).orElse(null);

        if (netz != null && mitglied != null &&
                "Bergung bevorstehend".equals(netz.getStatus()) &&
                mitglied.equals(netz.getBergendePerson())) {

            netz.setBergendePerson(null);
            netz.setStatus("Gemeldet");
            geisternetzRepository.save(netz);
        }

        return "redirect:/meineBergungen";
    }

    @GetMapping("/verscholleneNetze")
    public String zeigeVerschollbareNetze(Model model) {
        System.out.println("Aufruf verschollen-melden");
        List<Geisternetz> alleNetze = geisternetzRepository.findAll();
        List<Geisternetz> verfuegbareNetze = alleNetze.stream()
                .filter(netz -> !"Verschollen".equals(netz.getStatus()) && !"Geborgen".equals(netz.getStatus()))
                .toList();
        System.out.println("Gefundene Netze: " + verfuegbareNetze.size());
        model.addAttribute("netze", verfuegbareNetze);
        return "verscholleneNetze";
    }

    @PostMapping("/netz-als-verschollen")
    public String alsVerschollenMelden(@RequestParam("netzId") long netzId) {
        Geisternetz netz = geisternetzRepository.findById(netzId).orElse(null);

        if (netz != null && !"Geborgen".equals(netz.getStatus()) && !"Verschollen".equals(netz.getStatus())) {
                    netz.setStatus("Verschollen");
                    netz.setBergendePerson(null);
                    geisternetzRepository.save(netz);
        }

        return "redirect:/verscholleneNetze";
    }


}
