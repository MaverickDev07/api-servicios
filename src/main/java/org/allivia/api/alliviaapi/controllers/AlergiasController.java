package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.services.IAlergiaService;
import org.allivia.api.alliviaapi.services.IVacunaService;
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
public class AlergiasController {
    public static final Logger logger = LogManager.getLogger(AlergiasController.class);
    @Autowired
    private IAlergiaService iAlergiaService;

    @GetMapping("/alergias")
    public ResponseEntity<Object> getListAlergias() throws JsonProcessingException {
        try {
            Object result = iAlergiaService.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener alergias", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alergias/{value}")
    public ResponseEntity<Object> getListAlergias(@PathVariable String value) throws JsonProcessingException {
        try {
            Object result = iAlergiaService.findByName(value);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener alergias", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
