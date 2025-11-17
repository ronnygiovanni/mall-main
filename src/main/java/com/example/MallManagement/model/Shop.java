package com.example.MallManagement.model;
public class Shop implements Identifiable {
    private String id, name, ownerName;
    private double areaSqm;
    private int rating;
    private String floorId; // Foreign Key

    public Shop() {}
    public Shop(String id, String name, String ownerName, double areaSqm, int rating, String floorId) {
        this.id = id; this.name = name; this.ownerName = ownerName;
        this.areaSqm = areaSqm; this.rating = rating; this.floorId = floorId;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public double getAreaSqm() { return areaSqm; }
    public void setAreaSqm(double areaSqm) { this.areaSqm = areaSqm; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getFloorId() { return floorId; }
    public void setFloorId(String floorId) { this.floorId = floorId; }
}