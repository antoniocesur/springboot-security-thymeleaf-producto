package com.example.registrationlogindemo.controller;

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

    public ArrayList<Long> carrito;

    public ProductoController(){
        carrito=new ArrayList<>();
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
    public String modificarProductos(@ModelAttribute Producto producto, @RequestParam("file") MultipartFile file){
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

    @GetMapping("/carrito/altas/{id}")
    public String altasCarrito(@PathVariable("id") long id, HttpSession sesion){
        carrito.add(id);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model){
        ArrayList<Producto> listaProductos=new ArrayList<>();
        for(long id:carrito){
            listaProductos.add(productoService.findById(id));
        }
        model.addAttribute("listaProductos", listaProductos);
        return "carrito";
    }

    @GetMapping("/carrito/eliminar/{id}")
    public String eliminarCarrito(@PathVariable("id") long id){
        carrito.remove(id);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/comprar")
    public String comprarCarrito(Model model, Authentication authentication){
        User user=userService.findByEmail(authentication.getName());

        Compra compra=new Compra();
        compra.setFecha(LocalDate.now());
        compra.setComprador(user);
        compraRepository.save(compra);

        double total=0;
        for(long id:carrito){
            Producto producto=productoService.findById(id);
            LineaCompra lineaCompra=new LineaCompra();
            lineaCompra.setProducto(producto);
            lineaCompra.setCompra(compra);
            lineaCompra.setCantidad(1);
            lineaCompra.setPrecio(producto.getPrecio());
            total += lineaCompra.getCantidad() * lineaCompra.getPrecio();

            lineaCompraRepository.save(lineaCompra);
        }
        carrito=new ArrayList<>();
        user.setCredito(user.getCredito()-total);
        userService.saveUser(UserMapper.userToDto(user));
        return "redirect:/";
    }
}
