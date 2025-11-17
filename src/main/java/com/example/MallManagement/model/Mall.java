package com.example.MallManagement.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;

public class Mall implements Identifiable {
    private String id, name, city;
    @JsonIgnore private List<Floor> floors = new ArrayList<>(); // Ignored for JSON

    public Mall() {}
    public Mall(String id, String name, String city) {
        this.id = id; this.name = name; this.city = city;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public List<Floor> getFloors() { return floors; }
    public void setFloors(List<Floor> floors) { this.floors = floors; }
}