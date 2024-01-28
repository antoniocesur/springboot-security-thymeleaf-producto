package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.CompraDto;
import com.example.registrationlogindemo.dto.LineaCompraDto;
import com.example.registrationlogindemo.dto.UserMapper;
import com.example.registrationlogindemo.entity.Compra;
import com.example.registrationlogindemo.entity.LineaCompra;
import com.example.registrationlogindemo.entity.Producto;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.CompraRepository;
import com.example.registrationlogindemo.repository.LineaCompraRepository;
import com.example.registrationlogindemo.service.ProductoService;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.service.impl.UserServiceImpl;
import com.example.registrationlogindemo.storage.StorageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProductoController {
    @Autowired
    ProductoService productoService;

    @Autowired
    public StorageService storageService;

    @Autowired
    public CompraRepository compraRepository;

    @Autowired
    public LineaCompraRepository lineaCompraRepository;

    @Autowired
    public UserServiceImpl userService;

    //Voy a guardar el carrito de cada usuario en un mapa con el id de usuario
    //y una compraDto
    public Map<Long, CompraDto> carrito;

    public ProductoController(){
        carrito=new HashMap<>();
    }

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
    public String crearProductos(@ModelAttribute Producto producto, @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            String imagen = storageService.store(file, String.valueOf(producto.getId()));
            System.out.println("La imagen a guardar es : " + imagen);
            producto.setImagen(MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class, "serveFile", imagen).build().toUriString());
        }
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
    public String modificarProductos(@ModelAttribute("producto") Producto producto, @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            String imagen = storageService.store(file, String.valueOf(producto.getId()));
            System.out.println("La imagen a guardar es : " + imagen);
            producto.setImagen(MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class, "serveFile", imagen).build().toUriString());
        }
        productoService.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/detalle/{id}")
    public String detalleProducto(@PathVariable("id") long id, Model model){
        Producto producto=productoService.findById(id);
        model.addAttribute("producto", producto);
        return "detalle";
    }


}
