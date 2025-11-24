package com.example.MallManagement.controller;

import com.example.MallManagement.model.MaintenanceTask;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.MaintenanceTaskService;
import com.example.MallManagement.service.StaffAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String details(@PathVariable String id, Model model) {
        MaintenanceTask task = taskService.findById(id);
        model.addAttribute("task", task);
        model.addAttribute("floor", floorService.findById(task.getFloorId()));
        if (task.getAssignmentId() != null) {
            model.addAttribute("assignment", assignmentService.findById(task.getAssignmentId()));
        }
        return "maintenance/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("task", new MaintenanceTask());
        return "maintenance/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        return "maintenance/form";
    }

    @PostMapping
    public String save(@ModelAttribute MaintenanceTask task, Model model) {

        if (floorService.findById(task.getFloorId()) == null) {
            model.addAttribute("error", "Floor with ID '" + task.getFloorId() + "' not found.");
            return "maintenance/form";
        }

        if (task.getAssignmentId() != null && !task.getAssignmentId().isEmpty()) {
            if (assignmentService.findById(task.getAssignmentId()) == null) {
                model.addAttribute("error", "Assignment with ID '" + task.getAssignmentId() + "' not found.");
                return "maintenance/form";
            }
        }

        taskService.add(task);
        return "redirect:/maintenance";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        taskService.delete(id);
        return "redirect:/maintenance";
    }
}