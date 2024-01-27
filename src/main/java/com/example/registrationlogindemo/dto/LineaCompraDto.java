package com.example.registrationlogindemo.dto;

import com.example.registrationlogindemo.entity.Compra;
import com.example.registrationlogindemo.entity.Producto;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineaCompraDto {
    private long id;

    private Producto producto;

    private double precio;

    private Compra compra;

    private int cantidad;
}
