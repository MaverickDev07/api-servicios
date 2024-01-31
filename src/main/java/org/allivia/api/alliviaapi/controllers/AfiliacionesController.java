package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.services.IAfiliacionesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AfiliacionesController {
    public static final Logger logger = LogManager.getLogger(AfiliacionesController.class);
    @Autowired
    private IAfiliacionesService iAfiliacionesService;

    @GetMapping("/afiliaciones")
    public ResponseEntity<Object> getListAfiliaciones() throws JsonProcessingException {
        try {
            Object result = iAfiliacionesService.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
