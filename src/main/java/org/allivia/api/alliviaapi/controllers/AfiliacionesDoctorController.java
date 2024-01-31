package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppDoctorafiliacionesEntity;
import org.allivia.api.alliviaapi.entities.AppDoctoreducacionEntity;
import org.allivia.api.alliviaapi.services.IAfiliacionesDoctorService;
import org.allivia.api.alliviaapi.services.IEducacionDoctorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
public class AfiliacionesDoctorController {
    public static final Logger logger = LogManager.getLogger(AfiliacionesDoctorController.class);
    @Autowired
    private IAfiliacionesDoctorService iAfiliacionesDoctorService;

    @PostMapping("/doctorAfiliacion/add")
    public ResponseEntity<Object> Add(@RequestBody AppDoctorafiliacionesEntity appDoctorafiliacionesEntity) throws JsonProcessingException {
        try {
            Object result = iAfiliacionesDoctorService.save(appDoctorafiliacionesEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/afiliaciones/doctor/{id}")
    public ResponseEntity<Object> getAfiliacionesDoctor(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = iAfiliacionesDoctorService.getAfiliacionesDoctor(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/afiliacion/doctor/delete")
    public ResponseEntity<Object> deleteAfiliacionDoctor(@RequestParam long idDoctor) throws JsonProcessingException {
        try {

            iAfiliacionesDoctorService.deleteAfiliacionDoctor(idDoctor);
            HashMap<Object, Object> result = new HashMap<>();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al eliminar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
