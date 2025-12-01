package com.example.MallManagement.controller;

import com.example.MallManagement.model.SecurityStaff;
import com.example.MallManagement.service.SecurityStaffService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/security-staff")
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
    public String createForm(Model model) {
        model.addAttribute("staff", new SecurityStaff());
        return "security-staff/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("staff", service.findById(id));
        return "security-staff/form";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("staff", service.findById(id));
        return "security-staff/details";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("staff") SecurityStaff staff, BindingResult result) {
        if (result.hasErrors()) {
            return "security-staff/form";
        }
        service.save(staff);
        return "redirect:/security-staff";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/security-staff";
    }
}