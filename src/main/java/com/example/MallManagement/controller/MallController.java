package com.example.MallManagement.controller;

import com.example.MallManagement.model.Floor;
import com.example.MallManagement.model.Mall;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.MallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/malls")
public class MallController {

    private final MallService mallService;
    private final FloorService floorService;

    // We inject BOTH services now
    public MallController(MallService mallService, FloorService floorService) {
        this.mallService = mallService;
        this.floorService = floorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("malls", mallService.findAll());
        return "mall/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        // --- DEBUG LOGGING START ---
        System.out.println("\n========== DEBUG: MALL DETAILS ==========");
        System.out.println("Requested Mall ID: " + id);

        Mall mall = mallService.findById(id);
        if (mall == null) {
            System.out.println("ERROR: Mall object is NULL. ID not found in malls.json.");
        } else {
            System.out.println("Found Mall: " + mall.getName());
        }
        model.addAttribute("mall", mall);

        // Check ALL floors in the system
        List<Floor> allFloors = floorService.findAll();
        System.out.println("Total floors in database: " + allFloors.size());

        // Debug check: Print the first few floors to see their mallId
        if (!allFloors.isEmpty()) {
            System.out.println("First floor in DB -> ID: " + allFloors.get(0).getId() + ", MallID: '" + allFloors.get(0).getMallId() + "'");
        }

        // Filter for this specific mall
        List<Floor> floors = allFloors.stream()
                .filter(f -> f.getMallId() != null && f.getMallId().equals(id))
                .collect(Collectors.toList());

        System.out.println("Floors found for THIS mall (" + id + "): " + floors.size());
        System.out.println("=========================================\n");
        // --- DEBUG LOGGING END ---

        model.addAttribute("floors", floors);
        return "mall/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("mall", new Mall());
        return "mall/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("mall", mallService.findById(id));
        return "mall/form";
    }

    @PostMapping
    public String save(@ModelAttribute Mall mall) {
        mallService.add(mall);
        return "redirect:/malls";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        // Delete associated floors first
        floorService.findAll().stream()
                .filter(f -> f.getMallId() != null && f.getMallId().equals(id))
                .forEach(f -> floorService.delete(f.getId()));

        mallService.delete(id);
        return "redirect:/malls";
    }
}