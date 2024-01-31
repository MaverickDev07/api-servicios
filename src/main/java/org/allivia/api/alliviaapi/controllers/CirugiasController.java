package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.services.IAlergiaService;
import org.allivia.api.alliviaapi.services.ICirugiaService;
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
public class CirugiasController {
    public static final Logger logger = LogManager.getLogger(CirugiasController.class);
    @Autowired
    private ICirugiaService iCirugiaService;

    @GetMapping("/cirugias")
    public ResponseEntity<Object> getListCirugias() throws JsonProcessingException {
        try {
            Object result = iCirugiaService.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener cirugias", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cirugias/{value}")
    public ResponseEntity<Object> getListCirugias(@PathVariable String value) throws JsonProcessingException {
        try {
            Object result = iCirugiaService.findByName(value);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener cirugias", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
