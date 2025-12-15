package com.example.MallManagement.controller;

import com.example.MallManagement.model.Mall;
import com.example.MallManagement.service.MallService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/malls")
public class MallController {

    private final MallService mallService;

    public MallController(MallService mallService) {
        this.mallService = mallService;
    }

    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       @RequestParam(defaultValue = "") String name,
                       @RequestParam(defaultValue = "") String city) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        model.addAttribute("malls", mallService.findAll(name, city, sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("name", name);
        model.addAttribute("city", city);
        return "mall/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("mall", mallService.findById(id));
        return "mall/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("mall", new Mall());
        return "mall/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("mall", mallService.findById(id));
        return "mall/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Mall mall, BindingResult result) {
        if (result.hasErrors()) {
            return "mall/form";
        }
        mallService.save(mall);
        return "redirect:/malls";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        mallService.delete(id);
        return "redirect:/malls";
    }
}