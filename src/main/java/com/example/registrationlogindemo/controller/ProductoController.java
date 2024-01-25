package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.entity.Producto;
import com.example.registrationlogindemo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ProductoController {
    @Autowired
    ProductoService productoService;

    @GetMapping("/")
    public String principal(Model model){
        model.addAttribute("listaProductos", productoService.findAll());
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

    @GetMapping("/productos/editar/{id}")
    public String modificarProductos(@PathVariable("id") long id, Model model){
        Producto producto = productoService.findById(id);
        model.addAttribute("producto", producto);
        return "formulario";
    }

    @PostMapping("/productos/modificar")
    public String modificarProductos(@ModelAttribute Producto producto){
        productoService.save(producto);
        return "redirect:/productos";
    }
}
