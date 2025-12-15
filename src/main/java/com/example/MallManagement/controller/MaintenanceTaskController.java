package com.example.MallManagement.controller;

import com.example.MallManagement.model.Floor;
import com.example.MallManagement.model.MaintenanceTask;
import com.example.MallManagement.model.StaffAssignment;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.MaintenanceTaskService;
import com.example.MallManagement.service.StaffAssignmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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
    public String list(Model model,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       @RequestParam(defaultValue = "") String description,
                       @RequestParam(required = false) MaintenanceTask.Status status,
                       @RequestParam(required = false) Long floorId,
                       @RequestParam(required = false) Long assignmentId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        model.addAttribute("tasks", taskService.findAll(description, status, floorId, assignmentId, sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("description", description);
        model.addAttribute("status", status);
        model.addAttribute("floorId", floorId);
        model.addAttribute("assignmentId", assignmentId);
        model.addAttribute("statuses", MaintenanceTask.Status.values());
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("assignments", assignmentService.findAll());
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
        model.addAttribute("statuses", MaintenanceTask.Status.values());
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("assignments", assignmentService.findAll());
        return "maintenance/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("statuses", MaintenanceTask.Status.values());
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("assignments", assignmentService.findAll());
        return "maintenance/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("task") MaintenanceTask task, BindingResult result,
                       @RequestParam(value = "assignmentId", required = false) Long assignmentId,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", MaintenanceTask.Status.values());
            model.addAttribute("floors", floorService.findAll());
            model.addAttribute("assignments", assignmentService.findAll());
            return "maintenance/form";
        }

        if (assignmentId != null) {
            StaffAssignment assignment = assignmentService.findById(assignmentId);
            if (assignment == null) {
                model.addAttribute("error", "Assignment ID " + assignmentId + " not found.");
                model.addAttribute("floors", floorService.findAll());
                model.addAttribute("assignments", assignmentService.findAll());
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