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

        // 1. Validar Nulos (Asegúrate de que estos nombres coincidan con tu Entity Proveedor)
        if (proveedor.getNombre_proveedor() == null || proveedor.getEmail_proveedor() == null || proveedor.getDireccion() == null) {
            return new ResponseEntity<>("Error: El nombre, direccion y correo son obligatorios.", HttpStatus.BAD_REQUEST);
        }

        // 2. Validar Correo
        String correo = proveedor.getEmail_proveedor().toLowerCase();
        if (!(correo.endsWith("@gmail.com") || correo.endsWith("@yahoo.com") || correo.endsWith("@outlook.com"))) {
            return new ResponseEntity<>("Error: El correo debe ser @gmail.com, @yahoo.com o @outlook.com.", HttpStatus.BAD_REQUEST);
        }

        try{
            Proveedor createdProveedor = proveedorService.saveProveedor(proveedor);
            return new ResponseEntity<>(createdProveedor, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProveedor(@PathVariable Integer id, @RequestBody Proveedor proveedor){

        // Validar ID
        if (id <= 0) {
            return new ResponseEntity<>("Error: El ID debe ser un número positivo.", HttpStatus.BAD_REQUEST);
        }

        // Validar nulos en los campos
        if (proveedor.getNombre_proveedor() == null || proveedor.getEmail_proveedor() == null) {
            return new ResponseEntity<>("Error: No puedes dejar campos obligatorios vacíos.", HttpStatus.BAD_REQUEST);
        }

        // Validar Correo
        String correo = proveedor.getEmail_proveedor().toLowerCase();
        if (!(correo.endsWith("@gmail.com") || correo.endsWith("@yahoo.com") || correo.endsWith("@outlook.com"))) {
            return new ResponseEntity<>("Error: Formato de correo inválido.", HttpStatus.BAD_REQUEST);
        }

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

        if (id <= 0) {
            return new ResponseEntity<>("Error: ID no válido.", HttpStatus.BAD_REQUEST);
        }

        Proveedor proveedor = proveedorService.getProveedorById(id);

        if(proveedor != null){
            proveedorService.deleteProveedor(id);
            return new ResponseEntity<>("El proveedor ha sido eliminado con exito", HttpStatus.OK);

        }else{
            return new ResponseEntity<>("No se ha encontrado el proveedor con su ID: "+id, HttpStatus.NOT_FOUND);
        }
    }
}
