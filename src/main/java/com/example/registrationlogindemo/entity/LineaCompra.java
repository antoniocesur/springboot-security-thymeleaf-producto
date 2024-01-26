package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LineaCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Producto producto;

    private double precio;

    @ManyToOne
    private Compra compra;

    private int cantidad;
}
