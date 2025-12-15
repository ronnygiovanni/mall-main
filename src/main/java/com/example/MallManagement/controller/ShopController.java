package com.example.MallManagement.controller;

import com.example.MallManagement.model.Floor;
import com.example.MallManagement.model.Shop;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;
    private final FloorService floorService;

    public ShopController(ShopService shopService, FloorService floorService) {
        this.shopService = shopService;
        this.floorService = floorService;
    }

    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       @RequestParam(defaultValue = "") String name,
                       @RequestParam(defaultValue = "") String ownerName,
                       @RequestParam(required = false) Long floorId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        model.addAttribute("shops", shopService.findAll(name, ownerName, floorId, sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("name", name);
        model.addAttribute("ownerName", ownerName);
        model.addAttribute("floorId", floorId);
        model.addAttribute("floors", floorService.findAll());
        return "shop/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("shop", shopService.findById(id));
        return "shop/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("shop", new Shop());
        model.addAttribute("floors", floorService.findAll());
        return "shop/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("shop", shopService.findById(id));
        model.addAttribute("floors", floorService.findAll());
        return "shop/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Shop shop, BindingResult result,
                       @RequestParam("floorId") Long floorId, Model model) {

        // 1. Validate Form Fields
        if (result.hasErrors()) {
            model.addAttribute("floors", floorService.findAll());
            return "shop/form";
        }

        // 2. Validate Foreign Key (Floor)
        Floor floor = floorService.findById(floorId);
        if (floor == null) {
            model.addAttribute("error", "Floor with ID " + floorId + " does not exist.");
            model.addAttribute("floors", floorService.findAll());
            return "shop/form"; // Return to form with error message
        }

        shop.setFloor(floor);
        shopService.save(shop);
        return "redirect:/shops";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        shopService.delete(id);
        return "redirect:/shops";
    }
}