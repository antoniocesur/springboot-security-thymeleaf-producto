package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.LineaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaCompraRepository extends JpaRepository<LineaCompra, Long> {
}
