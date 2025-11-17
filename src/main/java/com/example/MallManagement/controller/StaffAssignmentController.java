package com.example.MallManagement.controller;

import com.example.MallManagement.model.StaffAssignment;
import com.example.MallManagement.service.StaffAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff-assignments")
public class StaffAssignmentController {

    private final StaffAssignmentService assignmentService;

    public StaffAssignmentController(StaffAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public String listAssignments(Model model) {
        model.addAttribute("assignments", assignmentService.findAll());
        return "staff-assignment/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // Initialize with empty values.
        // Note: Staff ID and Floor ID are Strings in your model.
        model.addAttribute("assignment", new StaffAssignment(null, "", "", null));

        // Pass the Shift options to the view for the dropdown
        model.addAttribute("shifts", StaffAssignment.Shift.values());

        return "staff-assignment/form";
    }

    @PostMapping
    public String createAssignment(@ModelAttribute StaffAssignment assignment) {
        try {
            assignmentService.add(assignment);
        } catch (IllegalArgumentException e) {
            // Your service throws an error if the Staff ID doesn't exist.
            // For this simple project, we'll just redirect back (or you could show an error page).
            return "redirect:/staff-assignments/new?error=InvalidStaffId";
        }
        return "redirect:/staff-assignments";
    }

    @PostMapping("/{id}/delete")
    public String deleteAssignment(@PathVariable String id) {
        assignmentService.delete(id);
        return "redirect:/staff-assignments";
    }
}