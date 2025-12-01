package com.example.MallManagement.controller;

import com.example.MallManagement.model.ElectricalAsset;
import com.example.MallManagement.model.Floor;
import com.example.MallManagement.model.StaffAssignment;
import com.example.MallManagement.service.ElectricalAssetService;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.StaffAssignmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("asset", assetService.findById(id));
        return "electrical/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("asset", new ElectricalAsset());
        return "electrical/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("asset", assetService.findById(id));
        return "electrical/form";
    }

    // FIX: Added ("asset") to @ModelAttribute
    @PostMapping
    public String save(@Valid @ModelAttribute("asset") ElectricalAsset asset, BindingResult result,
                       @RequestParam("floorId") Long floorId,
                       @RequestParam(value = "assignmentId", required = false) Long assignmentId,
                       Model model) {

        if (result.hasErrors()) return "electrical/form";

        Floor floor = floorService.findById(floorId);
        if (floor == null) {
            model.addAttribute("error", "Floor ID " + floorId + " not found.");
            return "electrical/form";
        }
        asset.setFloor(floor);

        if (assignmentId != null) {
            StaffAssignment assignment = assignmentService.findById(assignmentId);
            if (assignment == null) {
                model.addAttribute("error", "Assignment ID " + assignmentId + " not found.");
                return "electrical/form";
            }
            asset.setAssignment(assignment);
        }

        assetService.save(asset);
        return "redirect:/assets";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        assetService.delete(id);
        return "redirect:/assets";
    }
}