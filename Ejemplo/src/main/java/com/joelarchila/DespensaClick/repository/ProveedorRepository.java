package com.joelarchila.DespensaClick.repository;

import com.joelarchila.DespensaClick.Entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}
