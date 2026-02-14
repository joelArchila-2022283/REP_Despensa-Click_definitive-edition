package com.joelarchila.DespensaClick.controller;

import com.joelarchila.DespensaClick.Entity.Venta;
import com.joelarchila.DespensaClick.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // Listar
    @GetMapping
    public List<Venta> getAllVentas() {
        return ventaService.getAllVentas();
    }

    // Crear
    @PostMapping
    public ResponseEntity<Object> createVenta(@Valid @RequestBody Venta venta) {

        // Validar Nulos
        if (venta.getId_producto() == null || venta.getId_empleado() == null || venta.getFecha_venta() == null) {
            return new ResponseEntity<>("Error: Producto, Empleado y Fecha son obligatorios.", HttpStatus.BAD_REQUEST);
        }

        // Validar Cantidad
        if (venta.getCantidad() <= 0) {
            return new ResponseEntity<>("Error: La cantidad debe ser mayor a 0.", HttpStatus.BAD_REQUEST);
        }

        // Validar Total
        if (venta.getTotal() < 0) {
            return new ResponseEntity<>("Error: El total no puede ser negativo.", HttpStatus.BAD_REQUEST);
        }

        try {
            Venta createdVenta = ventaService.saveVenta(venta);
            return new ResponseEntity<>(createdVenta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVenta(@PathVariable Integer id, @RequestBody Venta venta) {

        // Validar ID
        if (id <= 0) {
            return new ResponseEntity<>("Error: ID de venta inválido.", HttpStatus.BAD_REQUEST);
        }

        // Validar que los datos de actualización
        if (venta.getCantidad() <= 0 || venta.getTotal() < 0) {
            return new ResponseEntity<>("Error: Cantidad o Total no válidos.", HttpStatus.BAD_REQUEST);
        }

        Venta actualizada = ventaService.updateVenta(id, venta);

        if (actualizada != null) {
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenta(@PathVariable Integer id) {

        if (id <= 0) {
            return new ResponseEntity<>("Error: ID debe ser mayor a cero.", HttpStatus.BAD_REQUEST);
        }

        Venta venta = ventaService.getVentaById(id);

        if (venta != null) {
            ventaService.deleteVenta(id);
            return new ResponseEntity<>("La venta ha sido eliminada con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se ha encontrado la venta con el ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}