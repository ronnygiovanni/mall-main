package com.example.MallManagement.controller;

import com.example.MallManagement.model.Mall;
import com.example.MallManagement.service.MallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/malls")
public class MallController {

    private final MallService service;

    public MallController(MallService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("malls", service.findAll());
        return "mall/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("mall", new Mall());
        return "mall/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("mall", service.findById(id));
        return "mall/form";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("mall", service.findById(id));
        return "mall/details";
    }

    @PostMapping
    public String save(@ModelAttribute Mall mall) {
        service.add(mall);
        return "redirect:/malls";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/malls";
    }
}