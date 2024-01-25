package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.entity.Producto;
import com.example.registrationlogindemo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    ProductoRepository productoRepository;

    public void save(Producto producto){
        productoRepository.save(producto);
    }

    public Producto findById(long id){
        return productoRepository.findById(id);
    }

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }
}
