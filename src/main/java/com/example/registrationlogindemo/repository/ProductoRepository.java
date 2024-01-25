package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    public Producto findById(long id);
}
