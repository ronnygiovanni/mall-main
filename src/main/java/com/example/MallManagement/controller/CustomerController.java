package com.example.MallManagement.controller;

import com.example.MallManagement.model.Customer;
import com.example.MallManagement.model.Purchase;
import com.example.MallManagement.service.CustomerService;
import com.example.MallManagement.service.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final PurchaseService purchaseService; // Needed to find purchases

    public CustomerController(CustomerService customerService, PurchaseService purchaseService) {
        this.customerService = customerService;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        // Find all purchases made by this customer
        List<Purchase> purchases = purchaseService.findAll().stream()
                .filter(p -> p.getCustomerId() != null && p.getCustomerId().equals(id))
                .toList();
        model.addAttribute("purchases", purchases);
        return "customer/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "customer/form";
    }

    @PostMapping
    public String save(@ModelAttribute Customer customer) {
        customerService.add(customer);
        return "redirect:/customers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        customerService.delete(id);
        return "redirect:/customers";
    }
}