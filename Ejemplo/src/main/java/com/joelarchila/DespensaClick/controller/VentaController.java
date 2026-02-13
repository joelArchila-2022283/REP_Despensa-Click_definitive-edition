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
        try {
            Venta createdVenta = ventaService.saveVenta(venta);
            return new ResponseEntity<>(createdVenta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Integer id, @RequestBody Venta venta) {
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
        Venta venta = ventaService.getVentaById(id);

        if (venta != null) {
            ventaService.deleteVenta(id);
            return new ResponseEntity<>("La venta ha sido eliminada con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se ha encontrado la venta con el ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}