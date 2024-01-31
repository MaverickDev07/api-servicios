package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppControlconsultaservicioEntity;
import org.allivia.api.alliviaapi.entities.AppEducacionEntity;
import org.allivia.api.alliviaapi.services.IControlConsultaServicioService;
import org.allivia.api.alliviaapi.services.IEducacionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ControlConsultaServicioController {
    public static final Logger logger = LogManager.getLogger(ControlConsultaServicioController.class);
    @Autowired
    private IControlConsultaServicioService iControlConsultaServicioService;

    @PostMapping("/control/consulta/servicio/add")
    public ResponseEntity<Object> Add(@RequestBody AppControlconsultaservicioEntity appControlconsultaservicioEntity) throws JsonProcessingException {
        try {
            Object result = iControlConsultaServicioService.save(appControlconsultaservicioEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/control/consulta/servicio/update")
    public ResponseEntity<Object> Update(@RequestBody AppControlconsultaservicioEntity appControlconsultaservicioEntity) throws JsonProcessingException {
        try {
            Object result = iControlConsultaServicioService.update(appControlconsultaservicioEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
