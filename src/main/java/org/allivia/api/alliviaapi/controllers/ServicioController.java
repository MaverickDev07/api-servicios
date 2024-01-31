package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppParametrosEntity;
import org.allivia.api.alliviaapi.entities.AppServicioEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.services.IServicioService;
import org.allivia.api.alliviaapi.services.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ServicioController {
    public static final Logger logger = LogManager.getLogger(ServicioController.class);
    @Autowired
    private IServicioService servicioService;


    @GetMapping("/servicio/id/{id}")
    public ResponseEntity<Object> getIdServicio(@PathVariable long id) throws JsonProcessingException {
        try {
            AppServicioEntity result = servicioService.findByIdServices(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
