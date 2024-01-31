package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.PayException;
import org.allivia.api.alliviaapi.services.ISuscripcionService;
import org.allivia.api.alliviaapi.services.ISuscripcionServicioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class SuscripcionServicioController {
    public static final Logger logger = LogManager.getLogger(SuscripcionServicioController.class);
    @Autowired
    private ISuscripcionServicioService suscripcionServicioService;


    @GetMapping("/suscripcionServicios")
    public ResponseEntity<Object> getListSuscripcionServicios() throws JsonProcessingException {
        try {
            Object result = suscripcionServicioService.findSuscripcionServices();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/suscripcion/paciente/add")
    public ResponseEntity<Object> addSuscripcionPaciente(@RequestBody AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws JsonProcessingException {
        try {
            Object result = suscripcionServicioService.suscripcionPaciente(appPagoSuscripcionPaciente);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("message", "Error al suscribir el paciente");
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(data);
            }
        }catch (PayException e){
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(data);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/suscripcion/paciente/compra")
    public ResponseEntity<Object> addSuscripcionPaciente(@RequestParam Long idSuscripcion, @RequestParam Long idPaciente) throws JsonProcessingException {
        try {
            Object result = suscripcionServicioService.suscripcionPaciente(idSuscripcion, idPaciente);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/suscripcion/paciente")
    public ResponseEntity<Object> getSuscripcionPaciente(@RequestParam long idPaciente) throws JsonProcessingException {
        try {
            Object result = suscripcionServicioService.getSuscripcionPaciente(idPaciente);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/suscripcion/paciente/cancelacion")
    public ResponseEntity<Object> update(@RequestParam long idPaciente, @RequestParam String motivo) throws JsonProcessingException {
        try {
            Object result = suscripcionServicioService.updateCancelacion(idPaciente, motivo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
