package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private double precio;
    @Column(columnDefinition = "TEXT")
    private String imagen;
}
