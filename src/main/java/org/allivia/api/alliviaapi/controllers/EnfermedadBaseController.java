package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.services.ICirugiaService;
import org.allivia.api.alliviaapi.services.IEnfermedadBaseService;
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
public class EnfermedadBaseController {
    public static final Logger logger = LogManager.getLogger(EnfermedadBaseController.class);
    @Autowired
    private IEnfermedadBaseService iEnfermedadBaseService;

    @GetMapping("/enfermedadesBase")
    public ResponseEntity<Object> getListEnfermedadBase() throws JsonProcessingException {
        try {
            Object result = iEnfermedadBaseService.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener enfermedades de base", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/enfermedadesBase/{value}")
    public ResponseEntity<Object> getListEnfermedadBase(@PathVariable String value) throws JsonProcessingException {
        try {
            Object result = iEnfermedadBaseService.findByName(value);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener enfermedades de base", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
