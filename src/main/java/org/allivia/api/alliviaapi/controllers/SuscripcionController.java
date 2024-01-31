package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppServicioEntity;
import org.allivia.api.alliviaapi.entities.AppSuscripcionEntity;
import org.allivia.api.alliviaapi.services.IServicioService;
import org.allivia.api.alliviaapi.services.ISuscripcionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuscripcionController {
    public static final Logger logger = LogManager.getLogger(SuscripcionController.class);
    @Autowired
    private ISuscripcionService suscripcionService;


    @GetMapping("/suscripcion/id/{id}")
    public ResponseEntity<Object> getIdSuscripcion(@PathVariable long id) throws JsonProcessingException {
        try {
            AppSuscripcionEntity result = suscripcionService.findByIdSuscripcion(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
