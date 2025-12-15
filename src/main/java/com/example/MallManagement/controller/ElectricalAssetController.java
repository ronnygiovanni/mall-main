package com.example.MallManagement.controller;

import com.example.MallManagement.model.ElectricalAsset;
import com.example.MallManagement.model.Floor;
import com.example.MallManagement.model.StaffAssignment;
import com.example.MallManagement.service.ElectricalAssetService;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.StaffAssignmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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
    public String list(Model model,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       @RequestParam(required = false) ElectricalAsset.Type type,
                       @RequestParam(required = false) ElectricalAsset.Status status,
                       @RequestParam(required = false) Long floorId,
                       @RequestParam(required = false) Long assignmentId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        model.addAttribute("assets", assetService.findAll(type, status, floorId, assignmentId, sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("type", type);
        model.addAttribute("status", status);
        model.addAttribute("floorId", floorId);
        model.addAttribute("assignmentId", assignmentId);
        model.addAttribute("types", ElectricalAsset.Type.values());
        model.addAttribute("statuses", ElectricalAsset.Status.values());
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("assignments", assignmentService.findAll());
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
        model.addAttribute("types", ElectricalAsset.Type.values());
        model.addAttribute("statuses", ElectricalAsset.Status.values());
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("assignments", assignmentService.findAll());
        return "electrical/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("asset", assetService.findById(id));
        model.addAttribute("types", ElectricalAsset.Type.values());
        model.addAttribute("statuses", ElectricalAsset.Status.values());
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("assignments", assignmentService.findAll());
        return "electrical/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("asset") ElectricalAsset asset, BindingResult result,
                       @RequestParam(value = "assignmentId", required = false) Long assignmentId,
                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("types", ElectricalAsset.Type.values());
            model.addAttribute("statuses", ElectricalAsset.Status.values());
            model.addAttribute("floors", floorService.findAll());
            model.addAttribute("assignments", assignmentService.findAll());
            return "electrical/form";
        }

        if (assignmentId != null) {
            StaffAssignment assignment = assignmentService.findById(assignmentId);
            if (assignment == null) {
                model.addAttribute("error", "Assignment ID " + assignmentId + " not found.");
                model.addAttribute("floors", floorService.findAll());
                model.addAttribute("assignments", assignmentService.findAll());
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