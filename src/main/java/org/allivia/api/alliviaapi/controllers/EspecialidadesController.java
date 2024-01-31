package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.services.IEspecialidadesService;
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
public class EspecialidadesController {
    public static final Logger logger = LogManager.getLogger(EspecialidadesController.class);
    @Autowired
    private IEspecialidadesService especialidadesService;

    @GetMapping("/especialidades")
    public ResponseEntity<Object> getListEspecialidades() throws JsonProcessingException {
        try {
            Object result = especialidadesService.findAll();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/especialidad/{id}")
    public ResponseEntity<Object> getEspecialidad(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = especialidadesService.findById(id);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/especialidadbyname/{nombre}")
    public ResponseEntity<Object> getEspecialidad(@PathVariable String nombre) throws JsonProcessingException {
        try {
            Object result = especialidadesService.findByNombre(nombre);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
