package org.allivia.api.alliviaapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.allivia.api.alliviaapi.services.UsuarioEstadoViewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@RestController
public class UsuarioEstadoViewController {
    public static final Logger logger = LogManager.getLogger(UsuarioEstadoViewController.class);

    @Autowired
    private UsuarioEstadoViewServiceImpl usuarioEstadoViewServiceImpl;

    // @GetMapping("/usuarioPaciente")
    // public ResponseEntity<Object> getListPacientes(@RequestParam(defaultValue = "", required = false) String nombre, @RequestParam(defaultValue = "", required = false) String estado) throws JsonProcessingException {
    //     Object result;
    //     if(nombre.trim().isEmpty() && estado.trim().isEmpty()){
    //         result = usuarioEstadoViewServiceImpl.findAllUserView();
    //     }else if(!nombre.trim().isEmpty() && estado.trim().isEmpty()){
    //         result = usuarioEstadoViewServiceImpl.findByNombre(nombre);
    //     }else if(nombre.trim().isEmpty() && !estado.trim().isEmpty()){
    //         result = usuarioEstadoViewServiceImpl.findByEstado(estado);
    //     } else {
    //         result = usuarioEstadoViewServiceImpl.findByNombreAndEstado(nombre, estado);
    //     }
    //     return  ResponseEntity.ok(result);
    // }

    @GetMapping("/usuarioPaciente")
    public ResponseEntity<Object> getListPacientes(@RequestParam(defaultValue = "", required = true) int id, @RequestParam(defaultValue = "", required = false) String nombre, @RequestParam(value = "estado", required = false) List<String> estado) throws JsonProcessingException {
        try {
            logger.log(Level.INFO, "id: " + id);
            logger.log(Level.INFO, "nombre: " + nombre);
            logger.log(Level.INFO, "estado: " + estado);

            Object result = new Object();
            if (nombre.trim().isEmpty() && estado == null) {
                result = usuarioEstadoViewServiceImpl.findAllUserView(id);
            } else if (!nombre.trim().isEmpty() && estado == null) {
                result = usuarioEstadoViewServiceImpl.findByNombre(nombre, id);
            } else if (nombre.trim().isEmpty() && estado != null) {
                result = usuarioEstadoViewServiceImpl.findByEstado(estado, id);
            } else {
                result = usuarioEstadoViewServiceImpl.findByNombreAndEstado(nombre, estado, id);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

} 