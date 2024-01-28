package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.CompraDto;
import com.example.registrationlogindemo.dto.LineaCompraDto;
import com.example.registrationlogindemo.dto.UserMapper;
import com.example.registrationlogindemo.entity.Compra;
import com.example.registrationlogindemo.entity.LineaCompra;
import com.example.registrationlogindemo.repository.CompraRepository;
import com.example.registrationlogindemo.repository.LineaCompraRepository;
import com.example.registrationlogindemo.service.ProductoService;
import com.example.registrationlogindemo.service.impl.UserServiceImpl;
import com.example.registrationlogindemo.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CarritoController {
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

    public CarritoController(){
        carrito=new HashMap<>();
    }


    @GetMapping("/carrito/altas/{id}")
    public String altasCarrito(@PathVariable("id") long id, Authentication authentication){
        //TODO: Debería pasar casi todo este código al servicio y completar el mapper

        Long idUser=userService.findByEmail(authentication.getName()).getId();
        //Si el usuario activo aún no tiene un carrito, lo creo
        if(carrito.get(idUser) == null){
            //Creo la compraDto para almacenar sus compras
            CompraDto compraDto=new CompraDto();
            compraDto.setFecha(LocalDate.now());
            compraDto.setComprador(userService.findByEmail(authentication.getName()));
            compraDto.setLineaCompraDtos(new ArrayList<>());
            carrito.put(idUser, compraDto);
        }
        //Si el carrito de este usuario ya existe, simplemente añado el producto
        //Añado a la compra, el producto cuyo id me han pasado (TODO: después debería añadir otra variable con la cantidad y comprobar si ya está en la lista)
        LineaCompraDto lineaCompraDto=new LineaCompraDto();
        lineaCompraDto.setProducto(productoService.findById(id));
        lineaCompraDto.setCantidad(1);

        //Le pongo el precio actual del producto, pero sin "enlazar" para que si cambia el precio del producto, no se cambie en la venta
        lineaCompraDto.setPrecio(lineaCompraDto.getProducto().getPrecio());

        //Una vez que ya tiene todos los datos, añado la línea de comprar
        carrito.get(idUser).getLineaCompraDtos().add(lineaCompraDto);

        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model, Authentication authentication){
        Long idUser=userService.findByEmail(authentication.getName()).getId();
        ArrayList<LineaCompraDto> lista = (carrito.get(idUser) == null) ? new ArrayList<>() : carrito.get(idUser).getLineaCompraDtos();
        model.addAttribute("listaLineasCompra", lista);
        return "carrito";
    }

    @GetMapping("/carrito/eliminar/{id}")
    public String eliminarCarrito(@PathVariable("id") long id, Authentication authentication){
        Long idUser=userService.findByEmail(authentication.getName()).getId();
        for(int n=0; n<carrito.get(idUser).getLineaCompraDtos().size(); n++){
            LineaCompraDto linea=carrito.get(idUser).getLineaCompraDtos().get(n);
            if(linea.getId() == id){
                carrito.get(idUser).getLineaCompraDtos().remove(n);
            }
        };
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/comprar")
    public String comprarCarrito(Model model, Authentication authentication){

        Compra compra=new Compra();
        compra.setFecha(LocalDate.now());
        compra.setComprador(userService.findByEmail(authentication.getName()));
        compraRepository.save(compra);

        double total=0;
        for(LineaCompraDto lineaCompraDto:carrito.get(userService.findByEmail(authentication.getName())).getLineaCompraDtos()){
            LineaCompra lineaCompra=new LineaCompra();
            lineaCompra.setProducto(lineaCompraDto.getProducto());
            lineaCompra.setCompra(compra);
            lineaCompra.setCantidad(lineaCompraDto.getCantidad());
            lineaCompra.setPrecio(lineaCompraDto.getPrecio());
            total += lineaCompra.getCantidad() * lineaCompra.getPrecio();

            lineaCompraRepository.save(lineaCompra);
        }
        carrito.remove(userService.findByEmail(authentication.getName()));
        userService.findByEmail(authentication.getName()).setCredito(userService.findByEmail(authentication.getName()).getCredito()-total);
        userService.saveUser(UserMapper.userToDto(userService.findByEmail(authentication.getName())));
        return "redirect:/";
    }
}
