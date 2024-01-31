package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppDoctoresespecialidadesEntity;
import org.allivia.api.alliviaapi.services.IEspecialidadDoctorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
public class EspecialidadDoctorController {
    public static final Logger logger = LogManager.getLogger(EspecialidadDoctorController.class);

    @Autowired
    private IEspecialidadDoctorService especialidadDoctorService;


    @PostMapping("/doctorEspecialidad/add")
    public ResponseEntity<Object> getAdd(@RequestBody AppDoctoresespecialidadesEntity doctoresespecialidadesEntity) throws JsonProcessingException {
        try {
            Object result = especialidadDoctorService.save(doctoresespecialidadesEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/especialidades/doctor/idEspecialidad/{id}")
    public ResponseEntity<Object> getListSintomas(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = especialidadDoctorService.findEspecialidadDoctor(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/especialidades/doctor/tipo/{tipo}")
    public ResponseEntity<Object> getListSintomasTipo(@PathVariable String tipo) throws JsonProcessingException {
        try {
            Object result = especialidadDoctorService.findEspecialidadDoctorTipo(tipo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/especialidades/doctor/nombre/{nombre}")
    public ResponseEntity<Object> getListSintomas(@PathVariable String nombre) throws JsonProcessingException {
        try {
            Object result = especialidadDoctorService.findDoctorEspecialidad(nombre);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/especialidades/doctor/nombre/{nombre}/idEspecialidad/{idEspecialidad}")
    public ResponseEntity<Object> getListSintomas(@PathVariable String nombre, @PathVariable long idEspecialidad) throws JsonProcessingException {
        try {
            Object result = especialidadDoctorService.findDoctorEspecialidad(idEspecialidad, nombre);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/informacion/doctor/id/{id}")
    public ResponseEntity<Object> getDoctorInformacion(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = especialidadDoctorService.findDoctorEspecialidad(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/especialidades/doctor/{id}")
    public ResponseEntity<Object> getEspecialidadesDoctor(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = especialidadDoctorService.getEspecialidadesDoctor(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/especialidad/doctor/delete")
    public ResponseEntity<Object> deleteEspecialidadDoctor(@RequestParam long idDoctor) throws JsonProcessingException {
        try {
            especialidadDoctorService.deleteEspecialidadDoctor(idDoctor);
            HashMap<Object, Object> result = new HashMap<>();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al eliminar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/doctor/aleatorio")
    public ResponseEntity<Object> getDoctorAleatorio() throws JsonProcessingException {
        try {
            Object result = especialidadDoctorService.findDoctorAleatorio();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/especialidades/doctor}")
    public ResponseEntity<Object> getListSintomas(@RequestParam long idEspecialidad, @RequestParam String nombre) throws JsonProcessingException {
        try {
            Object result = especialidadDoctorService.findDoctorEspecialidad(idEspecialidad, nombre);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/doctor/dias/atencion")
    public ResponseEntity<Object> getDoctorHorarioAtencion(@RequestParam long idDoctor) throws JsonProcessingException {
        try {
            Object result = especialidadDoctorService.obtenerDiasHorasAtencion(idDoctor);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
