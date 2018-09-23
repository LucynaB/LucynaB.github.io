package lb.demo.controller;


import lb.demo.model.Mieszkanie;
import lb.demo.model.Mieszkaniec;
import lb.demo.model.Wspolnota;
import lb.demo.model.WspolnotaRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class WspolnotaController {
    private WspolnotaRepository wspolnotaRepository;

    public WspolnotaController(WspolnotaRepository wspolnotaRepository) {
        this.wspolnotaRepository = wspolnotaRepository;
    }

    @GetMapping("/wspolnoty")
    public String listaWpolnot(Model model) {
        List<Wspolnota> wspolnotaList = wspolnotaRepository.findAll();
        model.addAttribute("wspolnoty", wspolnotaList);
        return "wspolnoty";
    }

    @GetMapping("/removeWspolnota")
    public String delete(@RequestParam Long id) {
        Optional<Wspolnota> optional = wspolnotaRepository.findById(id);
        Wspolnota wspolnota = optional.get();
        if (wspolnota.getMieszkania().isEmpty()) {
            wspolnotaRepository.deleteById(id);
            return "redirect:/wspolnoty";
        } else return "message";

    }

    @GetMapping("/addWspolnota")
    public String add(Model model) {
        model.addAttribute("wspolnota", new Wspolnota());
        return "addWspolnota";
    }

    @PostMapping("/addWspolnota")
    public String add(Wspolnota wspolnota) {
        if (wspolnota.getName().equals("")) {
            wspolnota.setName(wspolnota.getAdress() + " " + wspolnota.getNumber());
        }
        wspolnotaRepository.save(wspolnota);
        return "redirect:/wspolnoty";
    }

    @GetMapping("/editWspolnota")
    public String edit(@RequestParam Long id, Model model) {
        Optional<Wspolnota> optional = wspolnotaRepository.findById(id);
        if (optional.isPresent()) {
            Wspolnota wspolnota = optional.get();
            model.addAttribute("wspolnota", wspolnota);
            return "editWspolnota";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/editWspolnota")
    public String edit(Wspolnota wspolnota) {
        wspolnotaRepository.save(wspolnota);
        return "redirect:/wspolnoty";
    }


    @GetMapping("/wspolnota/{id}")
    public String info(@PathVariable Long id, Model model) {
        Optional<Wspolnota> optional = wspolnotaRepository.findById(id);
        Wspolnota wspolnota = optional.get();
        List<Mieszkanie> mieszkanieList = wspolnota.getMieszkania();
        List<Mieszkaniec> mieszkancy = new ArrayList<>();
        for (Mieszkanie mieszkanie : mieszkanieList) {
            for (Mieszkaniec mieszkaniec : mieszkanie.getMieszkancy()) {
                mieszkancy.add(mieszkaniec);
            }
        }
        model.addAttribute("wspolnota", wspolnota);
        model.addAttribute("mieszkania", mieszkanieList);
        model.addAttribute("mieszkancy", mieszkancy);
        model.addAttribute("sumaPowierzchni", wspolnotaRepository.getSumArea(wspolnota));
        return "wspolnotaInfo";
    }


}
