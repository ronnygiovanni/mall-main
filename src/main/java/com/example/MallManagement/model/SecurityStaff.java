package com.example.MallManagement.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class SecurityStaff extends Staff {

    @NotBlank(message = "Badge number is required")
    private String badgeNo;

    public SecurityStaff() {}
    public SecurityStaff(String name, int salary, String badgeNo) {
        super(name, salary);
        this.badgeNo = badgeNo;
    }

    public String getBadgeNo() { return badgeNo; }
    public void setBadgeNo(String badgeNo) { this.badgeNo = badgeNo; }
}