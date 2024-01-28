package com.example.registrationlogindemo.dto;

import com.example.registrationlogindemo.entity.LineaCompra;

public class LineaCompraMapper {

    public static LineaCompraDto toLineaCompraDto(LineaCompra lineaCompra){
        return LineaCompraDto.builder()
                .id(lineaCompra.getId())
                .producto(lineaCompra.getProducto())
                .compra(lineaCompra.getCompra())
                .precio(lineaCompra.getPrecio())
                .cantidad(lineaCompra.getCantidad())
                .build();
    }

    public static LineaCompra toLineaCompra(LineaCompraDto lineaCompraDto){
        return LineaCompra.builder()
                .id(lineaCompraDto.getId())
                .producto(lineaCompraDto.getProducto())
                .compra(lineaCompraDto.getCompra())
                .precio(lineaCompraDto.getPrecio())
                .cantidad(lineaCompraDto.getCantidad())
                .build();
    }

}
