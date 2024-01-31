package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.services.ITipoCitaService;
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
public class VacunasController {
    public static final Logger logger = LogManager.getLogger(VacunasController.class);
    @Autowired
    private IVacunaService iVacunaService;

    @GetMapping("/vacunas")
    public ResponseEntity<Object> getListVacunas() throws JsonProcessingException {
        try {
            Object result = iVacunaService.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la vacunas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/vacunas/{value}")
    public ResponseEntity<Object> getListVacunas(@PathVariable String value) throws JsonProcessingException {
        try {
            Object result = iVacunaService.findByName(value);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la vacunas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
