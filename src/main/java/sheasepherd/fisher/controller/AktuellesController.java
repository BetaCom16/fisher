package sheasepherd.fisher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AktuellesController {

    @GetMapping("aktuelles")
    public String aktuelles(){
        return "aktuelles";
    }

}
