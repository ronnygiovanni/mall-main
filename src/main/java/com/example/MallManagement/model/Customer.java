package com.example.MallManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Identifiable {
    private String id;
    private String name;
    private String currency;

    // We ignore the list for JSON storage to prevent data duplication.
    // We will find purchases by searching the PurchaseRepository for this customerId.
    @JsonIgnore
    private List<String> purchases = new ArrayList<>();

    public Customer() {}

    public Customer(String id, String name, String currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public List<String> getPurchases() { return purchases; }
    public void setPurchases(List<String> purchases) { this.purchases = purchases; }
}