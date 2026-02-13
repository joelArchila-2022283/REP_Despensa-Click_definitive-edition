package com.joelarchila.DespensaClick.controller;

import com.joelarchila.DespensaClick.Entity.Producto;
import com.joelarchila.DespensaClick.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")

public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    //Listar
    @GetMapping
    public List<Producto> getAllProductos(){
        return productoService.getAllProductos();
    }

    //Crear
    @PostMapping
    public ResponseEntity<Object> createProducto(@Valid @RequestBody Producto producto){
        try{
            Producto createdProducto = productoService.saveProducto(producto);
            return new ResponseEntity<>(createdProducto, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Integer id, @RequestBody Producto producto){
        Producto actualizado = productoService.updateProducto(id, producto);

        if(actualizado != null){
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Integer id){
        Producto producto = productoService.getProductoById(id);

        if(producto != null){
            productoService.deleteProducto(id);
            return new ResponseEntity<>("El producto ha sido eliminado con exito", HttpStatus.OK);

        }else{
            return new ResponseEntity<>("No se ha encontrado el producto por el ID: "+id, HttpStatus.NOT_FOUND);
        }
    }

}
