package com.example.MallManagement.controller;

import com.example.MallManagement.model.ElectricalAsset;
import com.example.MallManagement.service.ElectricalAssetService;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.StaffAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assets")
public class ElectricalAssetController {

    private final ElectricalAssetService assetService;
    private final FloorService floorService;
    private final StaffAssignmentService assignmentService;

    public ElectricalAssetController(ElectricalAssetService assetService, FloorService floorService, StaffAssignmentService assignmentService) {
        this.assetService = assetService;
        this.floorService = floorService;
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("assets", assetService.findAll());
        return "electrical/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        ElectricalAsset asset = assetService.findById(id);
        model.addAttribute("asset", asset);
        model.addAttribute("floor", floorService.findById(asset.getFloorId()));
        if(asset.getAssignmentId() != null) {
            model.addAttribute("assignment", assignmentService.findById(asset.getAssignmentId()));
        }
        return "electrical/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("asset", new ElectricalAsset());
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("assignments", assignmentService.findAll());
        return "electrical/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("asset", assetService.findById(id));
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("assignments", assignmentService.findAll());
        return "electrical/form";
    }

    @PostMapping
    public String save(@ModelAttribute ElectricalAsset asset) {
        assetService.add(asset);
        return "redirect:/assets";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        assetService.delete(id);
        return "redirect:/assets";
    }
}