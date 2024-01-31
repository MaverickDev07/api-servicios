package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppControlsuscripcionservicioEntity;
import org.allivia.api.alliviaapi.services.IControlSuscripcionServicioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ControlSuscripcionServicioController {
    public static final Logger logger = LogManager.getLogger(ControlSuscripcionServicioController.class);
    @Autowired
    private IControlSuscripcionServicioService iControlSuscripcionServicioService;

    @PostMapping("/control/suscripcion/servicio/add")
    public ResponseEntity<Object> getAdd(@RequestBody AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity) throws JsonProcessingException {
        try {
            Object result = iControlSuscripcionServicioService.save(appControlsuscripcionservicioEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @PutMapping("/control/suscripcion/servicio/update")
//    public ResponseEntity<Object> getUpdate(@RequestBody AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity) throws JsonProcessingException {
//        Object result=iControlSuscripcionServicioService.save(appControlsuscripcionservicioEntity);
//        return  ResponseEntity.ok(result);
//    }

    @PutMapping("/control/suscripcion/servicio/update")
    public ResponseEntity<Object> updateControlSuscripcionIdServicio(@RequestBody AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity) {
        try {
            AppControlsuscripcionservicioEntity user = iControlSuscripcionServicioService.updateControlSuscripcionServicio(appControlsuscripcionservicioEntity);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/control/suscripcion/servicio/tipo/update")
    public ResponseEntity<Object> updateControlSuscripcionTipo(@RequestParam String tipo, @RequestParam long idPaciente, @RequestParam int cantidad) {
        try {
            AppControlsuscripcionservicioEntity user = iControlSuscripcionServicioService.updateControlSuscripcionServicioTipo(idPaciente,cantidad,tipo);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/control/suscripcion/servicio")
    public ResponseEntity<Object> getControlSuscripcionServicio(@RequestParam String tipo, @RequestParam long idPaciente) throws JsonProcessingException {
        try {
            Object result = iControlSuscripcionServicioService.getControlSuscripcionServicioTipo(tipo, idPaciente);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
