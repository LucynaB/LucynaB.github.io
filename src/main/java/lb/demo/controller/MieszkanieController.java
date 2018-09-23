package lb.demo.controller;

import lb.demo.model.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MieszkanieController {
    private MieszkanieRepository mieszkanieRepository;
    private WspolnotaRepository wspolnotaRepository;
    private MieszkaniecRepository mieszkaniecRepository;

    public MieszkanieController(MieszkanieRepository mieszkanieRepository, WspolnotaRepository wspolnotaRepository, MieszkaniecRepository mieszkaniecRepository) {
        this.mieszkanieRepository = mieszkanieRepository;
        this.wspolnotaRepository = wspolnotaRepository;
        this.mieszkaniecRepository = mieszkaniecRepository;
    }

    @GetMapping("/mieszkania")
    public String listaMieszkan(Model model,
                                @RequestParam(required = false) String sort,
                                @RequestParam(required = false) String order) {
        if (sort != null) {
            Sort.Direction direction = Sort.Direction.fromString(order);
            Sort s = Sort.by(direction, sort);

            List<Mieszkanie> sortedList = mieszkanieRepository.findAll(s);
            model.addAttribute("mieszkania", sortedList);

        } else {
            List<Mieszkanie> mieszkanieList = mieszkanieRepository.findAll();
            model.addAttribute("mieszkania", mieszkanieList);
        }
        return "mieszkania";
    }

    @GetMapping("/removeMieszkanie")
    public String delete(@RequestParam Long id) {
        Optional<Mieszkanie> optional = mieszkanieRepository.findById(id);
        Mieszkanie mieszkanie = optional.get();
        if (mieszkanie.getMieszkancy().isEmpty()) {
            mieszkanieRepository.deleteById(id);
            return "redirect:/mieszkania";
        } else return "message";

    }

    @GetMapping("/addMieszkanie")
    public String add(Model model) {
        List<Wspolnota> wspolnotaList = wspolnotaRepository.findAll();
        model.addAttribute("wspolnoty", wspolnotaList);
        model.addAttribute("mieszkanie", new Mieszkanie());
        return "addMieszkanie";
    }

    @PostMapping("/addMieszkanie")
    public String add(Mieszkanie mieszkanie) {
        mieszkanieRepository.save(mieszkanie);
        return "redirect:/mieszkania";
    }

    @GetMapping("/editMieszkanie")
    public String edit(@RequestParam Long id, Model model) {
        Optional<Mieszkanie> optional = mieszkanieRepository.findById(id);
        if (optional.isPresent()) {
            Mieszkanie mieszkanie = optional.get();
            model.addAttribute("mieszkanie", mieszkanie);
            model.addAttribute("wspolnoty", wspolnotaRepository.findAll());
            return "editMieszkanie";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/editMieszkanie")
    public String edit(Mieszkanie mieszkanie) {
        mieszkanieRepository.save(mieszkanie);
        return "redirect:/mieszkania";
    }

    @GetMapping("/mieszkanie/{id}")
    public String info(@PathVariable Long id, Model model) {
        Optional<Mieszkanie> optional = mieszkanieRepository.findById(id);
        Mieszkanie mieszkanie = optional.get();
        model.addAttribute("mieszkanie", mieszkanie);
        model.addAttribute("mieszkancy", mieszkanie.getMieszkancy());
        return "mieszkanieInfo";
    }

    @GetMapping("/dodajMieszkanca")
    public String addMieszkaniec(@RequestParam Long id, Model model) {
        Optional<Mieszkanie> optional = mieszkanieRepository.findById(id);
        Mieszkanie mieszkanie = optional.get();
        List<Mieszkaniec> mieszkaniecList = mieszkaniecRepository.findAll();
        model.addAttribute("mieszkancy", mieszkaniecList);
        model.addAttribute("mieszkanie", mieszkanie);
        return "dodajMieszkanca";
    }

    @PostMapping("/dodajMieszkanca")
    public String addMieszkaniec(@RequestParam Long id, @RequestParam Mieszkaniec mieszkaniec) {
        Optional<Mieszkanie> optional = mieszkanieRepository.findById(id);
        Mieszkanie mieszkanie = optional.get();
        mieszkaniec.setMieszkanie(mieszkanie);
        mieszkaniecRepository.save(mieszkaniec);
        return "redirect:/mieszkania";
    }
}
