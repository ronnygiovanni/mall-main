package com.example.MallManagement.controller;

import com.example.MallManagement.model.MaintenanceStaff;
import com.example.MallManagement.service.MaintenanceStaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/maintenance-staff")
public class MaintenanceStaffController {

    private final MaintenanceStaffService service;

    public MaintenanceStaffController(MaintenanceStaffService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("staffList", service.findAll());
        return "maintenance-staff/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("staff", new MaintenanceStaff());
        return "maintenance-staff/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("staff", service.findById(id));
        return "maintenance-staff/form";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("staff", service.findById(id));
        return "maintenance-staff/details";
    }

    @PostMapping
    public String save(@ModelAttribute MaintenanceStaff staff) {
        service.add(staff);
        return "redirect:/maintenance-staff";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/maintenance-staff";
    }
}