package com.joelarchila.DespensaClick.service;

import com.joelarchila.DespensaClick.Entity.Producto;
import com.joelarchila.DespensaClick.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImplements implements ProductoService{

    private final ProductoRepository productoRepository;

    public ProductoServiceImplements(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto saveProducto(Producto producto) throws RuntimeException {
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Integer id, Producto producto) {
        Producto productoExistente = productoRepository.findById(id).orElse(null);

        if(productoExistente != null){
            productoExistente.setNombre_producto(producto.getNombre_producto());
            productoExistente.setCategoria_producto(producto.getCategoria_producto());
            productoExistente.setPrecio_compra(producto.getPrecio_compra());
            productoExistente.setPrecio_venta(producto.getPrecio_venta());
            productoExistente.setId_proveedor(producto.getId_proveedor());

            return productoRepository.save(productoExistente);
        }
        return null;
    }

    @Override
    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }
}
