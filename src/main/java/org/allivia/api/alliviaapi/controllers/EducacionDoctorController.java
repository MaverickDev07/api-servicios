package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppDoctoreducacionEntity;
import org.allivia.api.alliviaapi.services.IEducacionDoctorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
public class EducacionDoctorController {
    public static final Logger logger = LogManager.getLogger(EducacionDoctorController.class);
    @Autowired
    private IEducacionDoctorService iEducacionDoctorService;

    @PostMapping("/doctorEducacion/add")
    public ResponseEntity<Object> Add(@RequestBody AppDoctoreducacionEntity appDoctoreducacionEntity) throws JsonProcessingException {
        try {
            Object result = iEducacionDoctorService.save(appDoctoreducacionEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/titulos")
    public ResponseEntity<Object> getTitulos() throws JsonProcessingException {
        try {
            Object result = iEducacionDoctorService.getTitulos();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/educaciones/doctor/{id}")
    public ResponseEntity<Object> getEducacionDoctor(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = iEducacionDoctorService.getEducacionDoctor(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/educacion/doctor/delete")
    public ResponseEntity<Object> deleteEducacionDoctor(@RequestParam long idDoctor) throws JsonProcessingException {
        try {
            iEducacionDoctorService.deleteEducacionDoctor(idDoctor);
            HashMap<Object, Object> result = new HashMap<>();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al eliminar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
