package com.joelarchila.DespensaClick.service;

import com.joelarchila.DespensaClick.Entity.Venta;
import com.joelarchila.DespensaClick.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImplements implements VentaService{

    private final VentaRepository ventaRepository;

    public VentaServiceImplements(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta getVentaById(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Venta saveVenta(Venta venta) throws RuntimeException {
        return ventaRepository.save(venta);
    }

    @Override
    public Venta updateVenta(Integer id, Venta venta) {
        Venta ventaExistente = ventaRepository.findById(id).orElse(null);

        if(ventaExistente != null){
            ventaExistente.setFecha_venta(venta.getFecha_venta());
            ventaExistente.setCantidad(venta.getCantidad());
            ventaExistente.setTotal(venta.getTotal());
            ventaExistente.setId_empleado(venta.getId_empleado());
            ventaExistente.setId_producto(venta.getId_producto());

            return ventaRepository.save(ventaExistente);
        }
        return null;
    }

    @Override
    public void deleteVenta(Integer id) {
        ventaRepository.deleteById(id);
    }
}
