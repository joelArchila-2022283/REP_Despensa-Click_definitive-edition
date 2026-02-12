package com.joelarchila.DespensaClick.service;


import com.joelarchila.DespensaClick.Entity.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductoService {
    List<Producto> getAllProductos();
    Producto getProductoById(Integer id);
    Producto saveProducto (Producto producto) throws RuntimeException;
    Producto updateProducto(Integer id, Producto producto);
    void deleteProducto(Integer id);
}
