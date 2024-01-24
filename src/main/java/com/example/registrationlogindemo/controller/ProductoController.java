package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.entity.Producto;
import com.example.registrationlogindemo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductoController {
    @Autowired
    ProductoService productoService;

    @GetMapping("/")
    public String principal(Model model){
        return "index";
    }

    @GetMapping("/productos")
    public String listaProductos(Model model){
        //Entre comillas el nombre de la variable que usaré en Thymeleaf
        model.addAttribute("listaProductos", productoService.findAll());
        return "productos";
    }

    @GetMapping("/productos/altas")
    public String altasProductos(Model model){
        model.addAttribute("producto", new Producto());
        return "formulario";
    }

    @PostMapping("/productos/crear")
    public String crearProductos(@ModelAttribute Producto producto){
        productoService.save(producto);
        return "redirect:/productos/altas";
    }
}
