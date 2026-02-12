package com.joelarchila.DespensaClick.repository;

import com.joelarchila.DespensaClick.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
