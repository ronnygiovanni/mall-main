package com.example.MallManagement.controller;

import com.example.MallManagement.model.Floor;
import com.example.MallManagement.model.MaintenanceTask;
import com.example.MallManagement.model.StaffAssignment;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.MaintenanceTaskService;
import com.example.MallManagement.service.StaffAssignmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceTaskController {

    private final MaintenanceTaskService taskService;
    private final FloorService floorService;
    private final StaffAssignmentService assignmentService;

    public MaintenanceTaskController(MaintenanceTaskService taskService, FloorService floorService, StaffAssignmentService assignmentService) {
        this.taskService = taskService;
        this.floorService = floorService;
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "maintenance/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        return "maintenance/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("task", new MaintenanceTask());
        return "maintenance/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        return "maintenance/form";
    }

    // FIX: Added ("task") to @ModelAttribute
    @PostMapping
    public String save(@Valid @ModelAttribute("task") MaintenanceTask task, BindingResult result,
                       @RequestParam("floorId") Long floorId,
                       @RequestParam(value = "assignmentId", required = false) Long assignmentId,
                       Model model) {
        if (result.hasErrors()) return "maintenance/form";

        Floor floor = floorService.findById(floorId);
        if (floor == null) {
            model.addAttribute("error", "Floor ID " + floorId + " not found.");
            return "maintenance/form";
        }
        task.setFloor(floor);

        if (assignmentId != null) {
            StaffAssignment assignment = assignmentService.findById(assignmentId);
            if (assignment == null) {
                model.addAttribute("error", "Assignment ID " + assignmentId + " not found.");
                return "maintenance/form";
            }
            task.setAssignment(assignment);
        }

        taskService.save(task);
        return "redirect:/maintenance";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        taskService.delete(id);
        return "redirect:/maintenance";
    }
}