package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppDoctorafiliacionesEntity;
import org.allivia.api.alliviaapi.entities.AppPagoEntity;
import org.allivia.api.alliviaapi.services.IAfiliacionesDoctorService;
import org.allivia.api.alliviaapi.services.IPagoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PagoController {
    public static final Logger logger = LogManager.getLogger(PagoController.class);
    @Autowired
    private IPagoService iPagoService;

    @PostMapping("/pago/add")
    public ResponseEntity<Object> getAdd(@RequestBody AppPagoEntity appPagoEntity) throws JsonProcessingException {
        try {
            Object result = iPagoService.save(appPagoEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/validar/pago/{transaccion}")
    public ResponseEntity<Object> getAdd(@PathVariable String transaccion) throws JsonProcessingException {
        try {
            Object result = iPagoService.update(transaccion);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
