package com.example.MallManagement.controller;

import com.example.MallManagement.model.Staff;
import com.example.MallManagement.model.StaffAssignment;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.MaintenanceStaffService;
import com.example.MallManagement.service.SecurityStaffService;
import com.example.MallManagement.service.StaffAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/staff-assignments")
public class StaffAssignmentController {

    private final StaffAssignmentService service;
    private final FloorService floorService;
    private final SecurityStaffService secService;
    private final MaintenanceStaffService maintService;

    public StaffAssignmentController(StaffAssignmentService s, FloorService f, SecurityStaffService sec, MaintenanceStaffService maint) {
        this.service = s;
        this.floorService = f;
        this.secService = sec;
        this.maintService = maint;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("assignments", service.findAll());
        return "staff-assignment/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        StaffAssignment assignment = service.findById(id);
        model.addAttribute("assignment", assignment);
        model.addAttribute("floor", floorService.findById(assignment.getFloorId()));

        Staff staff = secService.findById(assignment.getStaffId());
        if (staff == null) {
            staff = maintService.findById(assignment.getStaffId());
        }
        model.addAttribute("staff", staff);
        return "staff-assignment/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("assignment", new StaffAssignment());
        model.addAttribute("shifts", StaffAssignment.Shift.values());
        return "staff-assignment/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("assignment", service.findById(id));
        model.addAttribute("shifts", StaffAssignment.Shift.values());
        return "staff-assignment/form";
    }

    @PostMapping
    public String save(@ModelAttribute StaffAssignment sa, Model model) {
        // PREPARE SHIFTS FOR RELOAD (in case of error)
        model.addAttribute("shifts", StaffAssignment.Shift.values());


        if (floorService.findById(sa.getFloorId()) == null) {
            model.addAttribute("error", "Floor with ID '" + sa.getFloorId() + "' not found.");
            return "staff-assignment/form";
        }


        boolean isSecurity = secService.findById(sa.getStaffId()) != null;
        boolean isMaintenance = maintService.findById(sa.getStaffId()) != null;

        if (!isSecurity && !isMaintenance) {
            model.addAttribute("error", "Staff Member with ID '" + sa.getStaffId() + "' not found in Security or Maintenance.");
            return "staff-assignment/form";
        }

        service.add(sa);
        return "redirect:/staff-assignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/staff-assignments";
    }
}