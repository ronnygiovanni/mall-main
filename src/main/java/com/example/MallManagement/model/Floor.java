package com.example.MallManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Floor number is required")
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "mall_id", nullable = false)
    private Mall mall;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
    private List<Shop> shops;

    public Floor() {}
    public Floor(Integer number, Mall mall) { this.number = number; this.mall = mall; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }
    public Mall getMall() { return mall; }
    public void setMall(Mall mall) { this.mall = mall; }
}