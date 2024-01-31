package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppHorarioatencionEntity;
import org.allivia.api.alliviaapi.entities.AppHorasEntity;
import org.allivia.api.alliviaapi.repositories.IHorasRepository;
import org.allivia.api.alliviaapi.services.IHorarioAtencionService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.EditorAwareTag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


@RestController
public class HorarioAtencionController {
    public static final Logger logger = LogManager.getLogger(HorarioAtencionController.class);
    @Autowired
    private IHorarioAtencionService iHorarioAtencionService;

    @Autowired
    private IHorasRepository iHorasRepository;

    @PostMapping("/horaAtencion/add")
    public ResponseEntity<Object> getAdd(@RequestBody AppHorarioatencionEntity horarioatencionEntity) throws JsonProcessingException {
        try {
            Object result = iHorarioAtencionService.save(horarioatencionEntity);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                AppHorasEntity appHorasEntity = iHorasRepository.findById(horarioatencionEntity.getIdHoras()).get();
                HashMap<String, String> error = new HashMap<>();
                error.put("Mensaje:", "Ya se encuentra registrada la hora: " + appHorasEntity.getDescripcion() + " en la fecha: " + horarioatencionEntity.getFecha());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/horaAtencion/horas")
    public ResponseEntity<Object> getHoras() throws JsonProcessingException {
        try {
            Object result = iHorarioAtencionService.findAllHorarios();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/horaAtencion/dias")
    public ResponseEntity<Object> getDias() throws JsonProcessingException {
        try {
            Object result = iHorarioAtencionService.findAllDias();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/horaAtencion")
    public ResponseEntity<Object> gethorasHabilitadasNohabilitadas(@RequestParam long idDoctor, @RequestParam String fecha) throws JsonProcessingException {
        try {
            Object result = iHorarioAtencionService.getHorarioAtencionDoctor(idDoctor, fecha);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/horas/habilitadas")
    public ResponseEntity<Object> gethorasHabilitadas(@RequestParam long idDoctor, @RequestParam String fecha) throws JsonProcessingException {
        try {
            Object result = iHorarioAtencionService.getHorasAtencionDoctor(idDoctor, fecha);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/horaAtencion/update")
    public ResponseEntity<Object> gethorasUpdate(@RequestParam long id, @RequestParam boolean estado) throws JsonProcessingException {
        try {
            Object result = iHorarioAtencionService.updateHoraAtencion(id, estado);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
