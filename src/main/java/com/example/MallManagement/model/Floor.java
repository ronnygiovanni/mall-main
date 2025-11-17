package com.example.MallManagement.model;
public class Floor implements Identifiable {
    private String id;
    private int number;
    private String mallId; // Foreign Key

    public Floor() {}
    public Floor(String id, int number, String mallId) {
        this.id = id; this.number = number; this.mallId = mallId;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public String getMallId() { return mallId; }
    public void setMallId(String mallId) { this.mallId = mallId; }
}