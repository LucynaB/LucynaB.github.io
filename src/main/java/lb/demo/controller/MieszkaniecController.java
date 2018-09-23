package lb.demo.controller;

import lb.demo.model.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Controller
public class MieszkaniecController {
    private MieszkaniecRepository mieszkaniecRepository;
    private MieszkanieRepository mieszkanieRepository;

    public MieszkaniecController(MieszkaniecRepository mieszkaniecRepository, MieszkanieRepository mieszkanieRepository) {
        this.mieszkaniecRepository = mieszkaniecRepository;
        this.mieszkanieRepository = mieszkanieRepository;
    }

    @GetMapping("/mieszkancy")
    public String listaMieszkancow(Model model,
                                   @RequestParam(required = false) String sort,
                                   @RequestParam(required = false) String order) {
        if (sort != null) {
            Sort.Direction direction = Sort.Direction.fromString(order);
            Sort s = Sort.by(direction, sort);

            List<Mieszkaniec> sortedList = mieszkaniecRepository.findAll(s);
            model.addAttribute("mieszkancy", sortedList);

        } else {
            List<Mieszkaniec> mieszkancyList = mieszkaniecRepository.findAll();
            model.addAttribute("mieszkancy", mieszkancyList);
        }
        return "mieszkancy";
    }


    @GetMapping("/removeMieszkaniec")
    public String delete(@RequestParam Long id) {
        mieszkaniecRepository.deleteById(id);
        return "redirect:/mieszkancy";
    }

    @GetMapping("/addMieszkaniec")
    public String add(Model model) {
        List<Mieszkanie> mieszkanieList = mieszkanieRepository.findAll();
        model.addAttribute("mieszkaniec", new Mieszkaniec());
        model.addAttribute("mieszkania", mieszkanieList);
        return "addMieszkaniec";
    }

    @PostMapping("/addMieszkaniec")
    public String add(Mieszkaniec mieszkaniec) {
        mieszkaniecRepository.save(mieszkaniec);
        return "redirect:/mieszkancy";
    }

    @GetMapping("/editMieszkaniec")
    public String edit(@RequestParam Long id, Model model) {
        Optional<Mieszkaniec> optional = mieszkaniecRepository.findById(id);
        if (optional.isPresent()) {
            Mieszkaniec mieszkaniec = optional.get();
            model.addAttribute("mieszkaniec", mieszkaniec);
            model.addAttribute("mieszkania", mieszkanieRepository.findAll());
            return "editMieszkaniec";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/editMieszkaniec")
    public String edit(Mieszkaniec mieszkaniec) {
        mieszkaniecRepository.save(mieszkaniec);
        return "redirect:/mieszkancy";
    }


}
