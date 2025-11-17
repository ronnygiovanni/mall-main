package com.example.MallManagement.controller;

import com.example.MallManagement.model.SecurityStaff;
import com.example.MallManagement.service.SecurityStaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/security-staff") // Unique URL for security staff
public class SecurityStaffController {

    private final SecurityStaffService service;

    public SecurityStaffController(SecurityStaffService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("staffList", service.findAll());
        return "security-staff/index";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        // Create a new SecurityStaff with empty values
        model.addAttribute("staff", new SecurityStaff(null, "", "", 0));
        return "security-staff/form";
    }

    @PostMapping
    public String create(@ModelAttribute SecurityStaff staff) {
        // We don't need complex logic anymore, just save the specific object
        service.add(staff);
        return "redirect:/security-staff";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/security-staff";
    }
}