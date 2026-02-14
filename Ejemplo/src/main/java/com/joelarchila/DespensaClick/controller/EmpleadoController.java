package com.joelarchila.DespensaClick.controller;

import com.joelarchila.DespensaClick.Entity.Empleado;
import com.joelarchila.DespensaClick.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Avisa que esta clase manejará datos
@RestController

//Anotacion para ver que ruta va a llamar
@RequestMapping("/api/empleados")

public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService){
        this.empleadoService=empleadoService;;

    }

    //Anotación que cuando se abre la ruta, le avisa al Service que devuelva la lista(Empleados)
    @GetMapping
    public List<Empleado> getAllEmpleados(){return empleadoService.getAllEmpleados();}

    //Anotación cuando envian datos nuevos, el controlador los recibe
    @PostMapping
    public ResponseEntity<Object> createEmpleado(@Valid @RequestBody Empleado empleado){

        //Validacion de campos nulos
        if(empleado.getNombre_empleado() == null || empleado.getApellido_empleado() == null || empleado.getEmail_empleado() == null){
            return new ResponseEntity<>("Error: Todos los campos son obligatorios y no pueden ser nulos.", HttpStatus.BAD_REQUEST);
        }

        String correo = empleado.getEmail_empleado().toLowerCase(); // Convertimos a minúsculas para comparar fácil

        // Verificamos si NO termina con ninguno de los tres permitidos
        if (!(correo.endsWith("@gmail.com") || correo.endsWith("@yahoo.com") || correo.endsWith("@outlook.com"))) {
            return new ResponseEntity<>("Error: El correo debe ser un dominio válido (@gmail.com, @yahoo.com o @outlook.com).", HttpStatus.BAD_REQUEST);
        }

        try{
            Empleado createdEmpleado = empleadoService.saveEmpleado(empleado);
            return new ResponseEntity<>(createdEmpleado, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    //Anotacion
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmpleado(@PathVariable Integer id, @RequestBody Empleado empleado){
        //Le pasamos el id y los datos nuevos al service
        Empleado actualizado = empleadoService.updateEmpleado(id, empleado);

        // Validar que el ID sea válido (mayor a 0)
        if (id <= 0) {
            return new ResponseEntity<>("Error: El ID proporcionado no es válido.", HttpStatus.BAD_REQUEST);
        }

        // Validar que los datos nuevos no sean nulos
        if (empleado.getNombre_empleado() == null || empleado.getApellido_empleado() == null || empleado.getEmail_empleado() == null) {
            return new ResponseEntity<>("Error: No puedes dejar campos obligatorios nulos al actualizar.", HttpStatus.BAD_REQUEST);
        }

        // Validar el correo
        String correo = empleado.getEmail_empleado().toLowerCase();
        if (!(correo.endsWith("@gmail.com") || correo.endsWith("@yahoo.com") || correo.endsWith("@outlook.com"))) {
            return new ResponseEntity<>("Error: El nuevo correo debe ser un dominio permitido.", HttpStatus.BAD_REQUEST);
        }

        if(actualizado != null){
            //Si el servicio nos devolvio al empleado porque exista, respodemos con el httpstatus
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } else{
            //Si el servicio devolvio null, respondemos con httpstatus
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Anotacion para quitar algo del servidor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmpleado(@PathVariable Integer id){
        //Intenta buscar para saber si esta ahi el empleado
        Empleado empleado = empleadoService.getEmpleadoById(id);

        //Validar que el ID
        if (id <= 0) {
            return new ResponseEntity<>("Error: El ID debe ser un número positivo.", HttpStatus.BAD_REQUEST);
        }

        // Verificar si existe antes de borrar
        Empleado existente = empleadoService.getEmpleadoById(id);
        if (existente == null) {
            return new ResponseEntity<>("Error: El empleado con ID " + id + " no existe.", HttpStatus.NOT_FOUND);
        }

        if(empleado != null){
            empleadoService.deleteEmpleado(id);
            return new ResponseEntity<>("El empleado ha sido eliminado con exito", HttpStatus.OK);

        }else{
            return new ResponseEntity<>("No se ha encontrado el empleado con ID: "+id, HttpStatus.NOT_FOUND);
        }
    }

}
