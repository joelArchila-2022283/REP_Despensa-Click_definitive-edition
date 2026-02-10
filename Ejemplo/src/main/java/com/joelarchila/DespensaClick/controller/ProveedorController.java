package com.joelarchila.DespensaClick.controller;


import com.joelarchila.DespensaClick.Entity.Proveedor;
import com.joelarchila.DespensaClick.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")

public class ProveedorController {
    //Constante
    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService){
        this.proveedorService = proveedorService;
    }

    //Listar
    @GetMapping
    public List<Proveedor> getAllProveedores(){return proveedorService.getAllProveedores();}

    //Crear
    @PostMapping
    public ResponseEntity<Object> createProveedor(@Valid @RequestBody Proveedor proveedor){
        try{
            Proveedor createdProveedor = proveedorService.saveProveedor(proveedor);
            return new ResponseEntity<>(createdProveedor, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Integer id, @RequestBody Proveedor proveedor){
        Proveedor actualizado = proveedorService.updateProveedor(id, proveedor);

        if(actualizado != null){
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProveedor(@PathVariable Integer id){
        Proveedor proveedor = proveedorService.getProveedorById(id);

        if(proveedor != null){
            proveedorService.deleteProveedor(id);
            return new ResponseEntity<>("El proveedor ha sido eliminado con exito", HttpStatus.OK);

        }else{
            return new ResponseEntity<>("No se ha encontrado el proveedor con su ID: "+id, HttpStatus.NOT_FOUND);
        }
    }
}
