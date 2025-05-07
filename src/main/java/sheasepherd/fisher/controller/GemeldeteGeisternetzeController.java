package sheasepherd.fisher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sheasepherd.fisher.services.GeisternetzService;

@Controller
public class GemeldeteGeisternetzeController {

    @Autowired
    private GeisternetzService geisternetzService;

    @GetMapping("/gemeldeteGeisternetze")
    public String getGemeldeteGeisternetze(Model model){
        model.addAttribute("geisternetze", geisternetzService.zeigeAlleGeisternetze());
        return "gemeldeteGeisternetze";
    }


}
