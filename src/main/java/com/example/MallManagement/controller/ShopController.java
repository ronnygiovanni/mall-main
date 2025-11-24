package com.example.MallManagement.controller;

import com.example.MallManagement.model.Shop;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.stream.Collectors;

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
    public String list(Model model) {
        model.addAttribute("shops", shopService.findAll());
        Map<String, Integer> floorNumbers = floorService.findAll().stream()
                .collect(Collectors.toMap(floor -> floor.getId(), floor -> floor.getNumber()));
        model.addAttribute("floorNumbers", floorNumbers);
        return "shop/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Shop shop = shopService.findById(id);
        model.addAttribute("shop", shop);
        if(shop != null && shop.getFloorId() != null) {
            model.addAttribute("floor", floorService.findById(shop.getFloorId()));
        }
        return "shop/details";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("shop", new Shop());
        return "shop/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("shop", shopService.findById(id));
        return "shop/form";
    }

    @PostMapping
    public String save(@ModelAttribute Shop shop, Model model) {

        if (floorService.findById(shop.getFloorId()) == null) {
            model.addAttribute("error", "The Floor with ID '" + shop.getFloorId() + "' does not exist.");
            return "shop/form";
        }
        shopService.add(shop);
        return "redirect:/shops";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        shopService.delete(id);
        return "redirect:/shops";
    }
}