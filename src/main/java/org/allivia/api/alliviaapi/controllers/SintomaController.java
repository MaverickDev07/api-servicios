package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.services.ISintomaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class SintomaController {
    public static final Logger logger = LogManager.getLogger(SintomaController.class);
    @Autowired
    private ISintomaService sintomaService;

    @GetMapping("/sintomas")
    public ResponseEntity<Object> getListSintomas() throws JsonProcessingException {
        try {
            Object result = sintomaService.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/sintoma/id/{id}")
    public ResponseEntity<Object> getIdSintomaa(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = sintomaService.findById(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
