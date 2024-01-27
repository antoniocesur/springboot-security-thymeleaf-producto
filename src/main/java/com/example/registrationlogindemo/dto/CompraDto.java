package com.example.registrationlogindemo.dto;

import com.example.registrationlogindemo.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraDto {
    //El carrito de la compra va a ser una CompraDto
    private long id;
    private User comprador;
    private LocalDate fecha;
    private ArrayList<LineaCompraDto> lineaCompraDtos;

}
