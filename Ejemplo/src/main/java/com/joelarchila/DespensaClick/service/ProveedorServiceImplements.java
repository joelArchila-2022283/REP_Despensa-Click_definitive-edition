package com.joelarchila.DespensaClick.service;


import com.joelarchila.DespensaClick.Entity.Proveedor;
import com.joelarchila.DespensaClick.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImplements implements ProveedorService{

    private final ProveedorRepository proveedorRepository;

    public ProveedorServiceImplements(ProveedorRepository proveedorRepository){
        this.proveedorRepository = proveedorRepository;
    }


    @Override
    public List<Proveedor> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor getProveedorById(Integer id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) throws RuntimeException {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor updateProveedor(Integer id, Proveedor proveedor) {
        Proveedor proveedorExistente = proveedorRepository.findById(id).orElse(null);

        if(proveedorExistente != null){
            proveedorExistente.setNombre_proveedor(proveedor.getNombre_proveedor());
            proveedorExistente.setTelefono_proveedor(proveedor.getTelefono_proveedor());
            proveedorExistente.setDireccion(proveedor.getDireccion());
            proveedorExistente.setEmail_proveedor(proveedor.getEmail_proveedor());

            return proveedorRepository.save(proveedorExistente);
        }
        return null;
    }

    @Override
    public void deleteProveedor(Integer id) {
        proveedorRepository.deleteById(id);
    }
}
