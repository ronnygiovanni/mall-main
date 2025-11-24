package com.example.MallManagement.controller;

import com.example.MallManagement.model.Purchase;
import com.example.MallManagement.service.CustomerService;
import com.example.MallManagement.service.PurchaseService;
import com.example.MallManagement.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final CustomerService customerService;
    private final ShopService shopService;

    public PurchaseController(PurchaseService purchaseService, CustomerService customerService, ShopService shopService) {
        this.purchaseService = purchaseService;
        this.customerService = customerService;
        this.shopService = shopService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("purchases", purchaseService.findAll());
        return "purchase/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Purchase purchase = purchaseService.findById(id);
        model.addAttribute("purchase", purchase);
        model.addAttribute("customer", customerService.findById(purchase.getCustomerId()));
        model.addAttribute("shop", shopService.findById(purchase.getShopId()));
        return "purchase/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("purchase", new Purchase());
        return "purchase/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("purchase", purchaseService.findById(id));
        return "purchase/form";
    }

    @PostMapping
    public String save(@ModelAttribute Purchase purchase, Model model) {

        if (customerService.findById(purchase.getCustomerId()) == null) {
            model.addAttribute("error", "Customer with ID '" + purchase.getCustomerId() + "' not found.");
            return "purchase/form";
        }

        if (shopService.findById(purchase.getShopId()) == null) {
            model.addAttribute("error", "Shop with ID '" + purchase.getShopId() + "' not found.");
            return "purchase/form";
        }

        purchaseService.add(purchase);
        return "redirect:/purchases";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        purchaseService.delete(id);
        return "redirect:/purchases";
    }
}