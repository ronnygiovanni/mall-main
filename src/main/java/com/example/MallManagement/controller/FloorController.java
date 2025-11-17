package com.example.MallManagement.controller;

import com.example.MallManagement.model.Floor;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.MallService;
import com.example.MallManagement.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/floors")
public class FloorController {

    private final FloorService floorService;
    private final MallService mallService;
    private final ShopService shopService;

    public FloorController(FloorService floorService, MallService mallService, ShopService shopService) {
        this.floorService = floorService;
        this.mallService = mallService;
        this.shopService = shopService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("floors", floorService.findAll());

        // FIX: Create a Map of [Mall ID -> Mall Name] for the View
        Map<String, String> mallNames = mallService.findAll().stream()
                .collect(Collectors.toMap(mall -> mall.getId(), mall -> mall.getName()));
        model.addAttribute("mallNames", mallNames);

        return "floor/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Floor floor = floorService.findById(id);
        model.addAttribute("floor", floor);
        model.addAttribute("mall", mallService.findById(floor.getMallId()));
        model.addAttribute("shops", shopService.findAll().stream()
                .filter(s -> s.getFloorId() != null && s.getFloorId().equals(id))
                .toList());
        return "floor/details";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("floor", new Floor());
        model.addAttribute("malls", mallService.findAll());
        return "floor/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("floor", floorService.findById(id));
        model.addAttribute("malls", mallService.findAll());
        return "floor/form";
    }

    @PostMapping
    public String save(@ModelAttribute Floor floor) {
        floorService.add(floor);
        return "redirect:/floors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        floorService.delete(id);
        return "redirect:/floors";
    }
}