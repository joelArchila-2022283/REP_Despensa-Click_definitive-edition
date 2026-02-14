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

        //  Validar Nulos y Vacíos
        if (producto.getNombre_producto() == null || producto.getCategoria_producto().trim().isEmpty()) {
            return new ResponseEntity<>("Error: El nombre del producto es obligatorio.", HttpStatus.BAD_REQUEST);
        }

        // Validar que el precio compra y venta sea mayor a 0
        if (producto.getPrecio_compra() <= 0 & producto.getPrecio_venta() <= 0) {
            return new ResponseEntity<>("Error: El precio de compra y de venta debe ser mayor a cero.", HttpStatus.BAD_REQUEST);
        }

        try{
            Producto createdProducto = productoService.saveProducto(producto);
            return new ResponseEntity<>(createdProducto, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProducto(@PathVariable Integer id, @RequestBody Producto producto){

        // 1. Validar ID
        if (id <= 0) {
            return new ResponseEntity<>("Error: El ID debe ser un número positivo.", HttpStatus.BAD_REQUEST);
        }

        // 2. Validaciones de lógica de negocio
        if (producto.getPrecio_compra() <= 0 && producto.getPrecio_venta() <= 0) {
            return new ResponseEntity<>("Error: El nuevo precio debe ser mayor a cero.", HttpStatus.BAD_REQUEST);
        }

        if (producto.getNombre_producto() == null || producto.getCategoria_producto().trim().isEmpty()) {
            return new ResponseEntity<>("Error: El nombre y la categoria no puede quedar vacío.", HttpStatus.BAD_REQUEST);
        }

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

        if (id <= 0) {
            return new ResponseEntity<>("Error: ID inválido.", HttpStatus.BAD_REQUEST);
        }

        Producto producto = productoService.getProductoById(id);

        if(producto != null){
            productoService.deleteProducto(id);
            return new ResponseEntity<>("El producto ha sido eliminado con exito", HttpStatus.OK);

        }else{
            return new ResponseEntity<>("No se ha encontrado el producto por el ID: "+id, HttpStatus.NOT_FOUND);
        }
    }

}
