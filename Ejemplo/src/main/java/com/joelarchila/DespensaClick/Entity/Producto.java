package com.joelarchila.DespensaClick.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Productos")

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_producto")
    private Integer id_producto;

    @Column(name = "nombre_producto")
    private String nombre_producto;

    @Column(name = "categoria_producto")
    private String categoria_producto;

    @Column(name = "precio_compra")
    private double precio_compra;

    @Column(name = "precio_venta")
    private double precio_venta;

    @Column(name = "id_proveedor")
    private Integer id_proveedor;

    public Producto() {

    }

    public Producto(Integer id_producto, String nombre_producto, String categoria_producto, double precio_compra, double precio_venta, Integer id_proveedor) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.categoria_producto = categoria_producto;
        this.precio_compra = precio_compra;
        this.precio_venta = precio_venta;
        this.id_proveedor = id_proveedor;
    }

    
    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getCategoria_producto() {
        return categoria_producto;
    }

    public void setCategoria_producto(String categoria_producto) {
        this.categoria_producto = categoria_producto;
    }

    public double getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(double precio_compra) {
        this.precio_compra = precio_compra;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public Integer getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(Integer id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
}
