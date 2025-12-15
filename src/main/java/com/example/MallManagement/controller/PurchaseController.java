package com.example.MallManagement.controller;

import com.example.MallManagement.model.Customer;
import com.example.MallManagement.model.Purchase;
import com.example.MallManagement.model.Shop;
import com.example.MallManagement.service.CustomerService;
import com.example.MallManagement.service.PurchaseService;
import com.example.MallManagement.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String list(Model model,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       @RequestParam(required = false) Double minAmount,
                       @RequestParam(required = false) Double maxAmount,
                       @RequestParam(required = false) Long customerId,
                       @RequestParam(required = false) Long shopId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        model.addAttribute("purchases", purchaseService.findAll(minAmount, maxAmount, customerId, shopId, sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("minAmount", minAmount);
        model.addAttribute("maxAmount", maxAmount);
        model.addAttribute("customerId", customerId);
        model.addAttribute("shopId", shopId);
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("shops", shopService.findAll());
        return "purchase/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("purchase", purchaseService.findById(id));
        return "purchase/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("purchase", new Purchase());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("shops", shopService.findAll());
        return "purchase/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("purchase", purchaseService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("shops", shopService.findAll());
        return "purchase/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Purchase purchase, BindingResult result,
                       @RequestParam("customerId") Long customerId,
                       @RequestParam("shopId") Long shopId, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("shops", shopService.findAll());
            return "purchase/form";
        }

        Customer customer = customerService.findById(customerId);
        if (customer == null) {
            model.addAttribute("error", "Customer ID " + customerId + " not found.");
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("shops", shopService.findAll());
            return "purchase/form";
        }

        Shop shop = shopService.findById(shopId);
        if (shop == null) {
            model.addAttribute("error", "Shop ID " + shopId + " not found.");
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("shops", shopService.findAll());
            return "purchase/form";
        }

        purchase.setCustomer(customer);
        purchase.setShop(shop);
        purchaseService.save(purchase);
        return "redirect:/purchases";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        purchaseService.delete(id);
        return "redirect:/purchases";
    }
}