package com.example.MallManagement.controller;

import com.example.MallManagement.model.Floor;
import com.example.MallManagement.model.Mall;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.MallService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/floors")
public class FloorController {

    private final FloorService floorService;
    private final MallService mallService;

    public FloorController(FloorService floorService, MallService mallService) {
        this.floorService = floorService;
        this.mallService = mallService;
    }

    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       @RequestParam(required = false) Integer number,
                       @RequestParam(required = false) Long mallId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        model.addAttribute("floors", floorService.findAll(number, mallId, sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("number", number);
        model.addAttribute("mallId", mallId);
        model.addAttribute("malls", mallService.findAll());
        return "floor/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("floor", floorService.findById(id));
        return "floor/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("floor", new Floor());
        model.addAttribute("malls", mallService.findAll());
        return "floor/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("floor", floorService.findById(id));
        model.addAttribute("malls", mallService.findAll());
        return "floor/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Floor floor, BindingResult result,
                       @RequestParam("mallId") Long mallId, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("malls", mallService.findAll());
            return "floor/form";
        }

        Mall mall = mallService.findById(mallId);
        if (mall == null) {
            model.addAttribute("error", "Mall ID " + mallId + " not found.");
            model.addAttribute("malls", mallService.findAll());
            return "floor/form";
        }

        floor.setMall(mall);
        floorService.save(floor);
        return "redirect:/floors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        floorService.delete(id);
        return "redirect:/floors";
    }
}