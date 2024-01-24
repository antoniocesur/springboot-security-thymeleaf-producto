package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private double precio;
}
